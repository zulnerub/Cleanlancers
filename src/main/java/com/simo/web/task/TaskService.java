package com.simo.web.task;

import com.simo.web.comment.CommentServiceImpl;
import com.simo.web.region.RegionService;
import com.simo.web.region.model.RegionEntity;
import com.simo.web.task.model.RegisterTaskDTO;
import com.simo.web.task.model.TaskEntity;
import com.simo.web.task.model.TaskMapper;
import com.simo.web.task.model.TaskServiceDTO;
import com.simo.web.task.repository.TaskRepository;
import com.simo.web.user.model.UserEntity;
import com.simo.web.user.service.UserServiceImpl;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserServiceImpl userService;
    private final RegionService regionService;
    private final CommentServiceImpl commentService;

    public TaskService(TaskRepository taskRepository, UserServiceImpl userService, RegionService regionService, CommentServiceImpl commentService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.regionService = regionService;
        this.commentService = commentService;
    }

    public TaskEntity findById(Long id){
        return this.taskRepository.findById(id).orElse(null);
    }

    public List<TaskEntity> allTasks() {
        return this.taskRepository.findAll();
    }

    public List<TaskServiceDTO> searchTasks(String taskName, String clientName, String cleanerName) {

        List<TaskEntity> taskEntities = this.taskRepository
                .getTasksBySearchParameters(taskName, clientName, cleanerName).orElse(null);

        List<TaskServiceDTO> taskServiceDTOS = new ArrayList<>();
        if (taskEntities != null) {
            taskServiceDTOS = taskEntities
                    .stream()
                    .map(TaskMapper.INSTANCE::mapTaskEntityToTaskServiceDto)
                    .collect(Collectors.toList());
        }

        return taskServiceDTOS;
    }


    public void createTask(RegisterTaskDTO registerTaskDTO, String username) {

        TaskEntity taskEntity = new TaskEntity();

        RegionEntity regionEntity = this.regionService.findRegionByName(registerTaskDTO.getRegion());
        UserEntity clientCurrent = this.userService.findUserByUsername(username);

        taskEntity.setName(registerTaskDTO.getName());
        taskEntity.setClient(clientCurrent);
        taskEntity.setComments(new ArrayList<>());
        taskEntity.setRegion(regionEntity);

        this.taskRepository.save(taskEntity);

    }

    public void delete(Long taskId) {
//        Optional<TaskEntity> taskToDelete = this.taskRepository.findById(taskId);
//        if (taskToDelete.isPresent()) {
//            if (!taskToDelete.get().getComments().isEmpty()) {
//                taskToDelete.get().getComments()
//                        .forEach(c -> this.commentService.deleteById(c.getId()));
//            }
//        }

        taskRepository.deleteById(taskId);
    }
}
