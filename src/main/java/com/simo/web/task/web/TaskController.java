package com.simo.web.task.web;

import com.simo.web.comment.model.*;
import com.simo.web.region.service.RegionServiceImpl;
import com.simo.web.region.model.RegionEntity;
import com.simo.web.response.model.ResponseAddDTO;
import com.simo.web.task.service.TaskServiceImpl;
import com.simo.web.task.model.TaskRegisterDTO;
import com.simo.web.task.model.TaskSearchDTO;
import com.simo.web.task.model.TaskServiceDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskServiceImpl taskService;
    private final RegionServiceImpl regionService;

    public TaskController(TaskServiceImpl taskService,
                          RegionServiceImpl regionService) {

        this.taskService = taskService;
        this.regionService = regionService;
    }

    @GetMapping
    public String loadSearch(Model model) {
        TaskSearchDTO searchTaskDTO;
        CommentAddDTO commentAddDTO;
        ResponseAddDTO responseAddDTO;


        if (model.containsAttribute("searchForm")) {
            searchTaskDTO = (TaskSearchDTO) model.getAttribute("searchForm");
            model.addAttribute("foundTasks", this.taskService.searchTasks(searchTaskDTO.getNameLike(),
                    searchTaskDTO.getClientFirstName(),
                    searchTaskDTO.getClientSecondName()));
        }else{
            searchTaskDTO = new TaskSearchDTO();
            model.addAttribute("foundTasks", this.taskService.allTasks());
        }

        model.addAttribute("searchForm", searchTaskDTO);


        if (model.containsAttribute("formAddComment")){
            commentAddDTO = (CommentAddDTO) model.getAttribute("formAddComment");
        }else{
            commentAddDTO = new CommentAddDTO();
            model.addAttribute("commentError", false);
        }

        if (!model.containsAttribute("errorCommentAttributes")){
            CommentAddDTO errorCommentAttributes = new CommentAddDTO();
            model.addAttribute("errorCommentAttributes", errorCommentAttributes);
        }else {
            model.addAttribute("commentError", true);
        }

        model.addAttribute("formAddComment", commentAddDTO);

        if (model.containsAttribute("formAddCommentReply")){
            responseAddDTO = (ResponseAddDTO) model.getAttribute("formAddCommentReply");
        }else{
            responseAddDTO = new ResponseAddDTO();
            model.addAttribute("responseError", false);
        }

        if (!model.containsAttribute("errorReplyAttributes")){
            ResponseAddDTO errorReplyAttributes = new ResponseAddDTO();
            model.addAttribute("errorReplyAttributes", errorReplyAttributes);
        }else {
            model.addAttribute("responseError", true);
        }

        model.addAttribute("formAddCommentReply", responseAddDTO);


        return "task/tasks";

    }


    @PostMapping
    public String getSearchedTasks(@Valid @ModelAttribute("searchForm") TaskSearchDTO searchTasksDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("searchForm", searchTasksDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "searchFrom", bindingResult);

            return "redirect:/tasks";
        }


        List<TaskServiceDTO> taskServiceDTOS =
                this.taskService.searchTasks(searchTasksDTO.getNameLike(),
                        searchTasksDTO.getClientFirstName(),
                        searchTasksDTO.getClientSecondName());




        redirectAttributes.addFlashAttribute("foundTasks", taskServiceDTOS);
        redirectAttributes.addFlashAttribute("searchForm", searchTasksDTO);


        return "redirect:/tasks";
    }

    @PreAuthorize("hasRole('ROLE_CLIENT') || hasRole('ROLE_ADMIN')")
    @GetMapping("/new")
    public String newTask(Model model) {

        TaskRegisterDTO formData;
        List<RegionEntity> allRegions = this.regionService.findAllRegions();
        model.addAttribute("regs", allRegions);

        if (model.containsAttribute("formData")) {
            formData = (TaskRegisterDTO) model.getAttribute("formData");
        } else {
            formData = new TaskRegisterDTO();

        }

        model.addAttribute("formData", formData);

        return "task/new";
    }

    @PreAuthorize("hasRole('ROLE_CLIENT') || hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("formData") TaskRegisterDTO registerTaskDTO,
                       Principal principal,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("formData", registerTaskDTO);
            redirectAttributes
                    .addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "formData",
                            bindingResult);

            return "redirect:/tasks/new";
        }

        taskService.createTask(registerTaskDTO, principal.getName());

        return "redirect:/tasks";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public String delete(@ModelAttribute("deleteTaskId") Long deleteId) {
        taskService.delete(deleteId);

        return "redirect:/tasks";
    }
}
