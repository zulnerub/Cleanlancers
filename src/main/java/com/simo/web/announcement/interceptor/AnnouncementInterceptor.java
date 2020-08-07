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

@Component
public class AnnouncementInterceptor implements HandlerInterceptor {
    private final AnnouncementServiceImpl announcementService;
    private final UserServiceImpl userService;

    public AnnouncementInterceptor(AnnouncementServiceImpl announcementService, UserServiceImpl userService) {
        this.announcementService = announcementService;
        this.userService = userService;
    }


    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

        AnnouncementRegisterDTO announcementRegisterDTO = new AnnouncementRegisterDTO();
        UserEntity creator = this.userService.findUserByUsername(modelAndView.getModel().get("creatorName").toString());

        announcementRegisterDTO.setUser(creator);
        announcementRegisterDTO.setTitle("New task created.");

        StringBuilder sb = new StringBuilder();
        sb.append("By client: ").append(modelAndView.getModel().get("creatorName"))
                .append("\t\n")
                .append("Region: ").append(modelAndView.getModel().get("taskRegion"))
                .append("\t\n")
                .append("Task name: ").append(modelAndView.getModel().get("taskName"));

        announcementRegisterDTO.setDescription(sb.toString());

        this.announcementService.createOrUpdateAnnouncement(announcementRegisterDTO);

    }
}
