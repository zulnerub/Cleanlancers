package com.simo.web.task.web;

import com.simo.web.comment.CommentServiceImpl;
import com.simo.web.comment.ResponseServiceImpl;
import com.simo.web.comment.model.*;
import com.simo.web.region.RegionService;
import com.simo.web.region.model.RegionEntity;
import com.simo.web.task.TaskService;
import com.simo.web.task.model.RegisterTaskDTO;
import com.simo.web.task.model.SearchTaskDTO;
import com.simo.web.task.model.TaskEntity;
import com.simo.web.task.model.TaskServiceDTO;
import com.simo.web.user.model.UserEntity;
import com.simo.web.user.service.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final RegionService regionService;
    private final CommentServiceImpl commentService;
    private final UserServiceImpl userService;
    private final ResponseServiceImpl responseService;

    public TaskController(TaskService taskService,
                          RegionService regionService,
                          CommentServiceImpl commentService,
                          UserServiceImpl userService,
                          ResponseServiceImpl responseService) {

        this.taskService = taskService;
        this.regionService = regionService;
        this.commentService = commentService;
        this.userService = userService;
        this.responseService = responseService;
    }

    @GetMapping
    public String loadSearch(Model model) {
        SearchTaskDTO searchTaskDTO;
        CommentAddDTO commentAddDTO;
        ResponseAddDTO responseAddDTO;


        if (model.containsAttribute("searchForm")) {
            searchTaskDTO = (SearchTaskDTO) model.getAttribute("searchForm");
        }else{
            searchTaskDTO = new SearchTaskDTO();
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
    public String getSearchedTasks(@Valid @ModelAttribute("searchForm") SearchTaskDTO searchTasksDTO,
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
    @PostMapping("/save-comment")
    public String saveNewComment(@Valid @ModelAttribute("formAddComment") CommentAddDTO formAddComment,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {


        if (bindingResult.hasErrors()) {
            CommentAddDTO errorCommentAttributes = new CommentAddDTO();
            errorCommentAttributes.setDescription(formAddComment.getDescription());
            errorCommentAttributes.setTaskId(formAddComment.getTaskId());

            redirectAttributes.addFlashAttribute("errorCommentAttributes", errorCommentAttributes);

            redirectAttributes.addFlashAttribute("formAddComment", formAddComment);
            redirectAttributes
                    .addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "formAddComment",
                            bindingResult);

            redirectAttributes.addFlashAttribute("commentError", true);
//            redirectAttributes.addFlashAttribute("errorTaskId", formAddComment.getTaskId());

            return "redirect:/tasks";
        }
        redirectAttributes.addFlashAttribute("commentError", false);

        CommentServiceDTO commentServiceDTO = CommentMapper.INSTANCE.mapCommentAddDtoToCommentServiceDto(formAddComment);

        TaskEntity task = this.taskService.findById(formAddComment.getTaskId());
        UserEntity author = this.userService.findUserByUsername(principal.getName());

        commentServiceDTO.setTask(task);
        commentServiceDTO.setAuthor(author);
        commentServiceDTO.setResponses(new ArrayList<>());
        commentServiceDTO.setCreatedOn(Instant.now());

        this.commentService.save(commentServiceDTO);

        return "redirect:/tasks";
    }


    @PreAuthorize("hasRole('ROLE_CLIENT') || hasRole('ROLE_ADMIN')")
    @PostMapping("/save-reply")
    public String saveNewReply(@Valid @ModelAttribute("formAddCommentReply") ResponseAddDTO formAddCommentReply,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {


        if (bindingResult.hasErrors()) {
            ResponseAddDTO errorReplyAttributes = new ResponseAddDTO();
            errorReplyAttributes.setDescription(formAddCommentReply.getDescription());
            errorReplyAttributes.setCommentId(formAddCommentReply.getCommentId());

            redirectAttributes.addFlashAttribute("errorReplyAttributes", errorReplyAttributes);

            redirectAttributes.addFlashAttribute("formAddCommentReply", formAddCommentReply);
            redirectAttributes
                    .addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "formAddCommentReply",
                            bindingResult);

            redirectAttributes.addFlashAttribute("responseError", true);

            return "redirect:/tasks";
        }
        redirectAttributes.addFlashAttribute("responseError", false);

        ResponseServiceDTO responseServiceDTO =
                ResponseMapper.INSTANCE.mapResponseAddDtoToResponseServiceDto(formAddCommentReply);

        UserEntity author = this.userService.findUserByUsername(principal.getName());
        CommentEntity comment = this.commentService.findById(formAddCommentReply.getCommentId());

        responseServiceDTO.setAuthor(author);
        responseServiceDTO.setComment(comment);
        responseServiceDTO.setCreatedOn(Instant.now());


        this.responseService.save(responseServiceDTO);

        return "redirect:/tasks";
    }

    @PreAuthorize("hasRole('ROLE_CLIENT') || hasRole('ROLE_ADMIN')")
    @GetMapping("/new")
    public String newTask(Model model) {

        RegisterTaskDTO formData;
        List<RegionEntity> allRegions = this.regionService.findAllRegions();
        model.addAttribute("regs", allRegions);

        if (model.containsAttribute("formData")) {
            formData = (RegisterTaskDTO) model.getAttribute("formData");
        } else {
            formData = new RegisterTaskDTO();

        }

        model.addAttribute("formData", formData);

        return "task/new";
    }

    @PreAuthorize("hasRole('ROLE_CLIENT') || hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("formData") RegisterTaskDTO registerTaskDTO,
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

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_CLIENT')")
    @DeleteMapping("/delete")
    public String delete(@ModelAttribute("deleteId") Long deleteId) {
        taskService.delete(deleteId);

        return "redirect:/tasks";
    }
}
