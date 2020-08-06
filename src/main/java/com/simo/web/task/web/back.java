// if ([[${commentError}]]) {
//     $("#taskId").val([[${formAddComment.taskId}]]);
//     $("#commentDescription").val([[${formAddComment.description}]])
//     $('#addCommentModal').modal('show');












//package com.simo.web.task.web;
//
//import com.simo.web.comment.service.CommentServiceImpl;
//import com.simo.web.comment.model.CommentAddDTO;
//import com.simo.web.comment.model.mapper.CommentMapper;
//import com.simo.web.comment.model.CommentServiceDTO;
//import com.simo.web.region.service.RegionService;
//import com.simo.web.region.model.RegionEntity;
//import com.simo.web.task.TaskService;
//import com.simo.web.task.model.RegisterTaskDTO;
//import com.simo.web.task.model.SearchTaskDTO;
//import com.simo.web.task.model.TaskEntity;
//import com.simo.web.task.model.TaskServiceDTO;
//import com.simo.web.user.model.UserEntity;
//import com.simo.web.user.service.UserServiceImpl;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.validation.Valid;
//import java.security.Principal;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.List;
//
//public class back {
//    package com.simo.web.task.web;
//
//import com.simo.web.comment.service.CommentServiceImpl;
//import com.simo.web.comment.model.CommentAddDTO;
//import com.simo.web.comment.model.mapper.CommentMapper;
//import com.simo.web.comment.model.CommentServiceDTO;
//import com.simo.web.region.service.RegionService;
//import com.simo.web.region.model.RegionEntity;
//import com.simo.web.task.TaskService;
//import com.simo.web.task.model.RegisterTaskDTO;
//import com.simo.web.task.model.SearchTaskDTO;
//import com.simo.web.task.model.TaskEntity;
//import com.simo.web.task.model.TaskServiceDTO;
//import com.simo.web.user.model.UserEntity;
//import com.simo.web.user.service.UserServiceImpl;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.validation.Valid;
//import java.security.Principal;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.List;
//
//    @Controller
//    @RequestMapping("/tasks")
//    public class TaskController {
//
//        private final TaskService taskService;
//        private final RegionService regionService;
//        private final CommentServiceImpl commentService;
//        private final UserServiceImpl userService;
//
//        public TaskController(TaskService taskService, RegionService regionService, CommentServiceImpl commentService, UserServiceImpl userService) {
//            this.taskService = taskService;
//            this.regionService = regionService;
//            this.commentService = commentService;
//            this.userService = userService;
//        }
//
//        @GetMapping
//        public String loadSearch(Model model) {
//
//            SearchTaskDTO searchTaskDTO;
//            if (!model.containsAttribute("searchForm")) {
//                searchTaskDTO = new SearchTaskDTO();
//                model.addAttribute("foundTasks", this.taskService.allTasks());
//            }else {
//                searchTaskDTO = (SearchTaskDTO) model.getAttribute("searchForm");
//            }
//
//            if (!model.containsAttribute("formAddComment")){
//                model.addAttribute("formAddComment", new CommentAddDTO());
//            }
//
//
//            return "task/tasks";
//
//        }
//
//
//        @PostMapping
//        public String getSearchedTasks(@Valid @ModelAttribute("searchForm") SearchTaskDTO searchTasksDTO,
//                                       BindingResult bindingResult,
//                                       RedirectAttributes redirectAttributes) {
//
//            if (bindingResult.hasErrors()) {
//                redirectAttributes.addFlashAttribute("searchForm", searchTasksDTO);
//                redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "searchFrom", bindingResult);
//
//                return "redirect:/tasks";
//            }
//
//
//            List<TaskServiceDTO> taskServiceDTOS =
//                    this.taskService.searchTasks(searchTasksDTO.getNameLike(),
//                            searchTasksDTO.getClientFirstName(),
//                            searchTasksDTO.getClientSecondName());
//
//
//            redirectAttributes.addFlashAttribute("foundTasks", taskServiceDTOS);
//            redirectAttributes.addFlashAttribute("searchForm", searchTasksDTO);
//
//
//            return "redirect:/tasks";
//        }
//
////    @PreAuthorize("hasRole('ROLE_CLIENT') || hasRole('ROLE_ADMIN')")
////    @GetMapping("/add-comment")
////    public String newComment(Model model, CommentAddDTO commentAddDTO) {
////        CommentAddDTO formAddComment;
////
////        if (model.containsAttribute("formAddComment")) {
////            formAddComment = (CommentAddDTO) model.getAttribute("formAddComment");
////        } else {
////            formAddComment = new CommentAddDTO();
////        }
////
////        model.addAttribute("formAddComment", formAddComment);
////
////        return "comment/add";
////    }
//
//        @PreAuthorize("hasRole('ROLE_CLIENT') || hasRole('ROLE_ADMIN')")
//        @PostMapping("/save-comment")
//        public String saveNewComment(@Valid @ModelAttribute("formAddComment") CommentAddDTO formAddComment,
//                                     @ModelAttribute("searchForm") SearchTaskDTO searchTaskDTO,
//                                     BindingResult bindingResult,
//                                     RedirectAttributes redirectAttributes,
//                                     Principal principal) {
//
//            if (bindingResult.hasErrors()) {
//                redirectAttributes.addFlashAttribute("formAddComment", formAddComment);
//                redirectAttributes
//                        .addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "formAddComment",
//                                bindingResult);
//                redirectAttributes.addFlashAttribute("commentError", bindingResult.hasFieldErrors("description"));
//                redirectAttributes.addFlashAttribute("searchForm", searchTaskDTO);
//
//
//                return "redirect:/tasks";
//            }
//            CommentServiceDTO commentServiceDTO = CommentMapper.INSTANCE.mapCommentAddDtoToCommentServiceDto(formAddComment);
//
//            TaskEntity task = this.taskService.findById(formAddComment.getTaskId());
//            UserEntity author = this.userService.findUserByUsername(principal.getName());
//
//            commentServiceDTO.setTask(task);
//            commentServiceDTO.setAuthor(author);
//            commentServiceDTO.setResponses(new ArrayList<>());
//            commentServiceDTO.setCreatedOn(Instant.now());
//
//            this.commentService.save(commentServiceDTO);
//
//            return "redirect:/tasks";
//        }
//
//        @PreAuthorize("hasRole('ROLE_CLIENT') || hasRole('ROLE_ADMIN')")
//        @GetMapping("/new")
//        public String newTask(Model model, RegisterTaskDTO registerTaskDTO) {
//
//            RegisterTaskDTO formData;
//            List<RegionEntity> allRegions = this.regionService.findAllRegions();
//            model.addAttribute("regs", allRegions);
//
//            if (model.containsAttribute("formData")) {
//                formData = (RegisterTaskDTO) model.getAttribute("formData");
//            } else {
//                formData = new RegisterTaskDTO();
//
//            }
//
//            model.addAttribute("formData", formData);
//
//            return "task/new";
//        }
//
//        @PreAuthorize("hasRole('ROLE_CLIENT') || hasRole('ROLE_ADMIN')")
//        @PostMapping("/save")
//        public String save(@Valid @ModelAttribute("formData") RegisterTaskDTO registerTaskDTO,
//                           Principal principal,
//                           BindingResult bindingResult,
//                           RedirectAttributes redirectAttributes) {
//
//            if (bindingResult.hasErrors()) {
//                redirectAttributes.addFlashAttribute("formData", registerTaskDTO);
//                redirectAttributes
//                        .addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "formData",
//                                bindingResult);
//
//                return "redirect:/tasks/new";
//            }
//
//            taskService.createTask(registerTaskDTO, principal.getName());
//
//            return "redirect:/tasks";
//        }
//
//        @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_CLIENT')")
//        @DeleteMapping("/delete")
//        public String delete(@ModelAttribute(name = "deleteId") Long deleteId) {
//            taskService.delete(deleteId);
//
//            return "redirect:/tasks";
//        }
//    }
//
//}
