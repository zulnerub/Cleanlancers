package com.simo.web.administration.web;

import com.simo.web.region.model.RegionEntity;
import com.simo.web.region.service.RegionServiceImpl;
import com.simo.web.task.model.TaskEntity;
import com.simo.web.task.service.TaskServiceImpl;
import com.simo.web.user.model.*;
import com.simo.web.user.model.mapper.UserMapper;
import com.simo.web.user.model.UserUpdateDTO;
import com.simo.web.user.service.RoleServiceImpl;
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
    private final RegionServiceImpl regionService;
    private final RoleServiceImpl roleService;

    public AdministrationController(UserServiceImpl userService, TaskServiceImpl taskService, RegionServiceImpl regionService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.taskService = taskService;
        this.regionService = regionService;
        this.roleService = roleService;
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

        this.userService.updateUser(UserMapper.INSTANCE.mapUserEntityToUserServiceModel(cleaner));
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

    @GetMapping("/user-management")
    public String userManagement(Model model) {

        UserSearchDTO userSearchDTO;
        if (model.containsAttribute("searchForm")) {
            userSearchDTO = (UserSearchDTO) model.getAttribute("searchForm");
            model.addAttribute("foundUsers", this.userService.findAllUsersBySearchParams(
                    userSearchDTO.getEmail() == null ? "" : userSearchDTO.getEmail(),
                    userSearchDTO.getFirstName(),
                    userSearchDTO.getLastName()));
        } else {
            userSearchDTO = new UserSearchDTO();
            model.addAttribute("foundUsers", this.userService.findAllUsers());
        }

        model.addAttribute("searchForm", userSearchDTO);

        return "administration/user-management";

    }

    @PostMapping("/user-management")
    public String getSearchedUsers(@Valid @ModelAttribute("searchForm") UserSearchDTO userSearchDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("searchForm", userSearchDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "searchForm", bindingResult);

            return "redirect:/administration/user-management";
        }


        List<UserServiceDTO> userServiceDTOS =
                this.userService.findAllUsersBySearchParams(
                        userSearchDTO.getEmail() == null ? "" : userSearchDTO.getEmail(),
                        userSearchDTO.getFirstName(),
                        userSearchDTO.getLastName());


        redirectAttributes.addFlashAttribute("foundUsers", userServiceDTOS);
        redirectAttributes.addFlashAttribute("searchForm", userSearchDTO);


        return "redirect:/administration/user-management";
    }

    @GetMapping("/user-details/{id}")
    public String getUserDetails(@PathVariable(name = "id") String userIdString,
                                 Model model, RedirectAttributes redirectAttributes
                                ){
        Long userId = Long.parseLong(userIdString);
        if (model.containsAttribute("userEntity")){
            UserUpdateDTO user = (UserUpdateDTO) model.getAttribute("userEntity");
            if (user.getId() != null && user.getId().equals(userId)){
                model.addAttribute("userEntity", user);

                return "administration/user-details";
            }
        }
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();

        UserServiceDTO user = this.userService.findById(userId);

        userUpdateDTO.setId(user.getId());
        userUpdateDTO.setEmail(user.getEmail());
        userUpdateDTO.setFirstName(user.getFirstName());
        userUpdateDTO.setLastName(user.getLastName());
        userUpdateDTO.setRegion(user.getRegion().getName());
        userUpdateDTO.setRoles(user.getRoles().get(0).getName());

        List<RegionEntity> allRegions = this.regionService.findAllRegions();
        model.addAttribute("userEntity", userUpdateDTO);

        model.addAttribute("regions", allRegions);

        redirectAttributes.addFlashAttribute("userEntity", userUpdateDTO);
        redirectAttributes.addFlashAttribute("regions", allRegions);

        return "administration/user-details";
    }

    @PostMapping("/update-user-details")
    public String updateUserDetails(@ModelAttribute("userEntity") UserUpdateDTO model,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes
                                 ){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userEntity", model);
            redirectAttributes
                    .addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "userEntity",
                            bindingResult);

            return "administration/user-details";
        }

        UserServiceDTO user =  this.userService.findById(model.getId());
        user.setEmail(model.getEmail());
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.setRegion(this.regionService.findRegionByName(model.getRegion()));
        user.setRoles(List.of(this.roleService.findRoleByName(model.getRoles())));

        this.userService.updateUser(user);

        return "redirect:/administration/user-details/" + model.getId();
    }
}
