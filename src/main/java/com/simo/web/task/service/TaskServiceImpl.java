package com.simo.web.task.service;

import com.simo.web.region.service.RegionServiceImpl;
import com.simo.web.region.model.RegionEntity;
import com.simo.web.task.model.TaskRegisterDTO;
import com.simo.web.task.model.TaskEntity;
import com.simo.web.task.model.mapper.TaskMapper;
import com.simo.web.task.model.TaskServiceDTO;
import com.simo.web.task.repository.TaskRepository;
import com.simo.web.user.model.UserEntity;
import com.simo.web.user.service.UserServiceImpl;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserServiceImpl userService;
    private final RegionServiceImpl regionService;


    public TaskServiceImpl(TaskRepository taskRepository, UserServiceImpl userService, RegionServiceImpl regionService ) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.regionService = regionService;
    }

    @Override
    public TaskEntity findById(Long id){
        return this.taskRepository.findById(id).orElse(null);
    }

    @Override
    public List<TaskEntity> allTasks() {
        return this.taskRepository.findAll();
    }

    @Override
    public List<TaskEntity> findAllOrderByCleanerAssigned() {
        return this.taskRepository.findAllOrderByCleanerAssigned().orElse(null);
    }

    @Override
    public List<TaskServiceDTO> searchTasks(String taskName, String clientFirstName, String ClientLastName) {

        List<TaskEntity> taskEntities = this.taskRepository
                .getTasksBySearchParameters(taskName, clientFirstName, ClientLastName).orElse(null);

        List<TaskServiceDTO> taskServiceDTOS = new ArrayList<>();
        if (taskEntities != null) {
            taskServiceDTOS = taskEntities
                    .stream()
                    .map(TaskMapper.INSTANCE::mapTaskEntityToTaskServiceDto)
                    .collect(Collectors.toList());
        }

        return taskServiceDTOS;
    }


    @Override
    public void createTask(TaskRegisterDTO registerTaskDTO, String username) {

        TaskEntity taskEntity = new TaskEntity();

        RegionEntity regionEntity = this.regionService.findRegionByName(registerTaskDTO.getRegion());
        UserEntity clientCurrent = this.userService.findUserByUsername(username);

        taskEntity.setName(registerTaskDTO.getName());
        taskEntity.setClient(clientCurrent);
        taskEntity.setComments(new ArrayList<>());
        taskEntity.setRegion(regionEntity);

        this.taskRepository.save(taskEntity);

    }

    @Override
    public void delete(Long taskId) {

        taskRepository.deleteById(taskId);
    }

    @Override
    public List<String> filterByTaskName(String taskName) {
        return this.taskRepository.filterByTaskName(taskName).orElse(null);
    }

    @Override
    public List<String> filterByClientFirstName(String firstName) {
        return this.taskRepository.filterByClientFirstName(firstName).orElse(null);
    }

    @Override
    public List<String> filterByClientLastName(String lastName) {
        return this.taskRepository.filterByClientLastName(lastName).orElse(null);
    }

    @Override
    public void updatedTask(TaskEntity task) {
        this.taskRepository.save(task);
    }
}
