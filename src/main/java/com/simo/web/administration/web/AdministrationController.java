package com.simo.web.administration.web;

import com.simo.web.task.model.TaskEntity;
import com.simo.web.task.model.mapper.TaskMapper;
import com.simo.web.task.service.TaskServiceImpl;
import com.simo.web.user.model.AssignCleanerForm;
import com.simo.web.user.model.UserEntity;
import com.simo.web.user.model.UserSearchDTO;
import com.simo.web.user.model.UserServiceDTO;
import com.simo.web.user.service.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/administration")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdministrationController {

    private final UserServiceImpl userService;
    private final TaskServiceImpl taskService;

    public AdministrationController(UserServiceImpl userService, TaskServiceImpl taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @PostMapping("/unassign-cleaner")
    public String unassignCleaner(@ModelAttribute("unassignTaskId") Long unassignTaskId) {
        TaskEntity task = this.taskService.findById(unassignTaskId);
        task.setCleaner(null);
        this.taskService.updatedTask(task);

        return "redirect:/administration";
    }

    @PostMapping("/assign-cleaner")
    public ModelAndView assignCleanerToTask(@Valid @ModelAttribute("assignCleanerForm") AssignCleanerForm assignCleanerForm,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes,
                                            ModelAndView modelAndView,
                                            Principal principal) {

        if (!this.userService.existUser(assignCleanerForm.getAssignedUsername())) {
            redirectAttributes.addFlashAttribute("userNotFound", "true");
            redirectAttributes.addFlashAttribute("assignCleanerForm", assignCleanerForm);

            modelAndView.setViewName("redirect:/administration");
            return modelAndView;
        } else {
            redirectAttributes.addFlashAttribute("userNotFound", "false");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("assignCleanerForm", assignCleanerForm);
            redirectAttributes
                    .addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "assignCleanerForm",
                            bindingResult);

            modelAndView.setViewName("redirect:/administration");
            return modelAndView;
        }

        UserEntity cleaner = this.userService.findUserByUsername(assignCleanerForm.getAssignedUsername());
        TaskEntity task = this.taskService.findById(assignCleanerForm.getTaskId());

        cleaner.getCleanerTasks().add(task);
        task.setCleaner(cleaner);

        this.userService.updatedCleanerWithNewTasks(cleaner);
        this.taskService.updatedTask(task);

        modelAndView.addObject("adminUsername", principal.getName());
        modelAndView.addObject("cleanerUsername", cleaner.getEmail());
        modelAndView.addObject("clientUsername", task.getClient().getEmail());
        modelAndView.addObject("taskName", task.getName());
        modelAndView.addObject("regionName", task.getRegion().getName());

        modelAndView.setViewName("redirect:/administration");


        return modelAndView;
    }

    @GetMapping(value = {"/cleaners-by-email/{taskRegion}/{cleanerEmail}"})
    public @ResponseBody
    List<String> filteredCleanersByEmail(
            @PathVariable(name = "taskRegion", required = false) String taskRegion,
            @PathVariable(name = "cleanerEmail", required = false) String cleanerEmail) {

        List<String> result = new ArrayList<>();
        if (cleanerEmail != null) {
            result = this.userService.filterCleanersByEmailLikeAndTaskRegion(cleanerEmail, taskRegion);
        }
        return result == null ? new ArrayList<>() : result;
    }

    @GetMapping(value = {"/user-by-email/{userEmail}", "/user-by-email/"})
    public @ResponseBody
    List<String> filteredUsersByEmail(
            @PathVariable(name = "userEmail", required = false) String userEmail) {

        List<String> result = new ArrayList<>();
        if (userEmail != null) {
            result = this.userService.filterByUserEmail(userEmail);
        }
        return result == null ? new ArrayList<>() : result;
    }

    @GetMapping(value = {"/user-by-first-name/{firstName}", "/user-by-first-name/"})
    public @ResponseBody
    List<String> filteredUsersByFirstName(
            @PathVariable(name = "firstName", required = false) String firstName) {

        List<String> result = new ArrayList<>();
        if (firstName != null) {
            result = this.userService.filterByUserFirstName(firstName);
        }
        return result == null ? new ArrayList<>() : result;
    }

    @GetMapping(value = {"/user-by-last-name/{lastName}", "/user-by-last-name/"})
    public @ResponseBody
    List<String> filteredUsersByLastName(
            @PathVariable(name = "lastName", required = false) String lastName) {

        List<String> result = new ArrayList<>();
        if (lastName != null) {
            result = this.userService.filterByUserLastName(lastName);
        }
        return result == null ? new ArrayList<>() : result;
    }

    @GetMapping(value = {"/user-by-region-name/{regionName}", "/user-by-region-name/"})
    public @ResponseBody
    List<String> filteredUsersByRegionName(
            @PathVariable(name = "regionName", required = false) String regionName) {

        List<String> result = new ArrayList<>();
        if (regionName != null) {
            result = this.userService.filterByUserRegionName(regionName);
        }
        return result == null ? new ArrayList<>() : result;
    }

    @GetMapping
    public String getFreeCleaners(Model model, RedirectAttributes redirectAttributes) {
        UserSearchDTO userSearchDTO;

        if (model.containsAttribute("searchForm")) {
            userSearchDTO = (UserSearchDTO) model.getAttribute("searchForm");
            model.addAttribute("foundCleaners", this.userService.searchCleaners(
                    userSearchDTO.getEmail(),
                    userSearchDTO.getFirstName(),
                    userSearchDTO.getLastName(),
                    userSearchDTO.getWorkArea()));
        } else {
            userSearchDTO = new UserSearchDTO();
            model.addAttribute("foundCleaners", this.userService.findAllCleaners());
        }


        if (model.containsAttribute("userNotFound") && model.getAttribute("userNotFound").equals("true")) {
            redirectAttributes.addFlashAttribute("assignCleanerForm", model.getAttribute("assignCleanerForm"));
        } else {
            AssignCleanerForm assignCleanerForm = new AssignCleanerForm();
            model.addAttribute("assignCleanerForm", assignCleanerForm);
        }

        model.addAttribute("allTasks", this.taskService.findAllOrderByCleanerAssigned());
        model.addAttribute("searchForm", userSearchDTO);

        return "administration/task-delegation";

    }


    @PostMapping
    public String getSearchedTasks(@Valid @ModelAttribute("foundCleaners") UserSearchDTO userSearchDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("foundCleaners", userSearchDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "foundCleaners", bindingResult);

            return "redirect:/administration";
        }


        List<UserServiceDTO> userServiceDTOS =
                this.userService.searchCleaners(
                        userSearchDTO.getEmail(),
                        userSearchDTO.getFirstName(),
                        userSearchDTO.getLastName(),
                        userSearchDTO.getWorkArea());


        redirectAttributes.addFlashAttribute("foundCleaners", userServiceDTOS);
        redirectAttributes.addFlashAttribute("searchForm", userSearchDTO);


        return "redirect:/administration";
    }
}
