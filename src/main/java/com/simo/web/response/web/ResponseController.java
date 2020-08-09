package com.simo.web.response.web;

import com.simo.web.comment.model.CommentEntity;
import com.simo.web.comment.service.CommentServiceImpl;
import com.simo.web.response.model.ResponseAddDTO;
import com.simo.web.response.model.mapper.ResponseMapper;
import com.simo.web.response.model.ResponseServiceDTO;
import com.simo.web.response.service.ResponseServiceImpl;
import com.simo.web.task.model.TaskSearchDTO;
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

@Controller
@RequestMapping("/responses")
public class ResponseController {

    private final ResponseServiceImpl responseService;
    private final CommentServiceImpl commentService;
    private final UserServiceImpl userService;

    public ResponseController(ResponseServiceImpl responseService, CommentServiceImpl commentService, UserServiceImpl userService) {
        this.responseService = responseService;
        this.commentService = commentService;
        this.userService = userService;
    }


    @PreAuthorize("hasRole('ROLE_CLIENT') || hasRole('ROLE_ADMIN')")
    @PostMapping("/save-reply")
    public String saveNewReply(@Valid @ModelAttribute("formAddCommentReply") ResponseAddDTO formAddCommentReply,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Principal principal) {


        if (bindingResult.hasErrors()) {
            ResponseAddDTO errorReplyAttributes = new ResponseAddDTO();
            errorReplyAttributes.setResponseDescription(formAddCommentReply.getResponseDescription());
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

        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setNameLike(comment.getTask().getName());
        taskSearchDTO.setClientFirstName("");
        taskSearchDTO.setClientLastName("");
        redirectAttributes.addFlashAttribute("searchForm", taskSearchDTO);

        formAddCommentReply.setResponseDescription("");
        redirectAttributes.addFlashAttribute("formAddCommentReply", formAddCommentReply);

        return "redirect:/tasks";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public String delete(@ModelAttribute("deleteReplyId") Long deleteId) {
        responseService.delete(deleteId);

        return "redirect:/tasks";
    }
}
