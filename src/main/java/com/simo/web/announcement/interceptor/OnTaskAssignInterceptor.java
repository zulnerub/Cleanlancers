package com.simo.web.announcement.interceptor;

import com.simo.web.announcement.model.AnnouncementRegisterDTO;
import com.simo.web.announcement.service.AnnouncementServiceImpl;
import com.simo.web.user.model.UserEntity;
import com.simo.web.user.service.UserServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Component
public class OnTaskAssignInterceptor implements HandlerInterceptor {
    private final UserServiceImpl userService;
    private final AnnouncementServiceImpl announcementService;

    public OnTaskAssignInterceptor(UserServiceImpl userService, AnnouncementServiceImpl announcementService) {
        this.userService = userService;
        this.announcementService = announcementService;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

        if (modelAndView != null && modelAndView.getModel().size() > 0) {
            AnnouncementRegisterDTO announcementForCleaner = new AnnouncementRegisterDTO();
            UserEntity cleaner = this.userService.findUserByUsername(modelAndView.getModel().get("cleanerUsername").toString());

            announcementForCleaner.setUser(cleaner);

            announcementForCleaner.setTitle("You have been assigned to a new job.");

            String descriptionForCleaner = "By admin username: " + modelAndView.getModel().get("adminUsername") +
                    "\t\n" +
                    "Region: " + modelAndView.getModel().get("regionName") +
                    "\t\n" +
                    "Task name: " + modelAndView.getModel().get("taskName") +
                    "\t\n" +
                    "Client : " + modelAndView.getModel().get("clientUsername");

            announcementForCleaner.setDescription(descriptionForCleaner);

            this.announcementService.createOrUpdateAnnouncement(announcementForCleaner);


            AnnouncementRegisterDTO announcementForClient = new AnnouncementRegisterDTO();
            UserEntity client = this.userService.findUserByUsername(modelAndView.getModel().get("clientUsername").toString());

            announcementForClient.setUser(client);

            announcementForClient.setTitle("We have started your task.");

            String descriptionForClient =
                    "Region: " + modelAndView.getModel().get("regionName") +
                    "\t\n" +
                    "Task name: " + modelAndView.getModel().get("taskName") +
                    "\t\n" +
                    "Cleaner : " + modelAndView.getModel().get("cleanerUsername");

            announcementForClient.setDescription(descriptionForClient);

            this.announcementService.createOrUpdateAnnouncement(announcementForClient);



        }
    }
}
