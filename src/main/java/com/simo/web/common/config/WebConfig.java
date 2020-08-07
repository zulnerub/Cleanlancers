package com.simo.web.common.config;


import com.simo.web.announcement.interceptor.AnnouncementInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {
    private final AnnouncementInterceptor announcementInterceptor;

    public WebConfig(AnnouncementInterceptor announcementInterceptor) {
        this.announcementInterceptor = announcementInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(announcementInterceptor)
                .addPathPatterns("/tasks/save");
    }
}
