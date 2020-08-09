package com.simo.web.comment.web;

import com.simo.web.comment.model.CommentAddDTO;
import com.simo.web.comment.model.mapper.CommentMapper;
import com.simo.web.comment.model.CommentServiceDTO;
import com.simo.web.comment.service.CommentServiceImpl;
import com.simo.web.task.model.TaskSearchDTO;
import com.simo.web.task.service.TaskServiceImpl;
import com.simo.web.task.model.TaskEntity;
import com.simo.web.user.model.UserEntity;
import com.simo.web.user.service.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentServiceImpl commentService;
    private final TaskServiceImpl taskService;
    private final UserServiceImpl userService;

    public CommentController(CommentServiceImpl commentService, TaskServiceImpl taskService, UserServiceImpl userService) {
        this.commentService = commentService;
        this.taskService = taskService;
        this.userService = userService;
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

        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setNameLike(task.getName());
        taskSearchDTO.setClientFirstName("");
        taskSearchDTO.setClientLastName("");
        redirectAttributes.addFlashAttribute("searchForm", taskSearchDTO);

        formAddComment.setDescription("");
        redirectAttributes.addFlashAttribute("formAddComment", formAddComment);

        return "redirect:/tasks";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public String delete(@ModelAttribute("deleteCommentId") Long deleteId) {
        commentService.delete(deleteId);

        return "redirect:/tasks";
    }
}
