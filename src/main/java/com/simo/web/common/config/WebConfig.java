package com.simo.web.common.config;


import com.simo.web.announcement.interceptor.OnTaskAssignInterceptor;
import com.simo.web.announcement.interceptor.OnTaskCreateAddAnnouncementInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {
    private final OnTaskCreateAddAnnouncementInterceptor onTaskCreateAddAnnouncementInterceptor;
    private final OnTaskAssignInterceptor onTaskAssignInterceptor;

    public WebConfig(OnTaskCreateAddAnnouncementInterceptor onTaskCreateAddAnnouncementInterceptor, OnTaskAssignInterceptor onTaskAssignInterceptor) {
        this.onTaskCreateAddAnnouncementInterceptor = onTaskCreateAddAnnouncementInterceptor;
        this.onTaskAssignInterceptor = onTaskAssignInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(onTaskCreateAddAnnouncementInterceptor)
                .addPathPatterns("/tasks/save");
        registry.addInterceptor(onTaskAssignInterceptor)
                .addPathPatterns("/administration/assign-cleaner");
    }
}
