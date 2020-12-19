package com.springbootweb.bangercocarrental.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path documentUploadDir = Paths.get("./documents");
        Path documentUploadDir2 = Paths.get("./category_logos");
        Path documentUploadDir3 = Paths.get("./car_images");

        String documentUploadPath = documentUploadDir.toFile().getAbsolutePath();
        String documentUploadPath2 = documentUploadDir2.toFile().getAbsolutePath();
        String documentUploadPath3 = documentUploadDir3.toFile().getAbsolutePath();

        registry.addResourceHandler("/documents/**").addResourceLocations("file:/" + documentUploadPath + "/");
        registry.addResourceHandler("/category_logos/**").addResourceLocations("file:/" + documentUploadPath2  + "/");
        registry.addResourceHandler("/car_images/**").addResourceLocations("file:/" + documentUploadPath3  + "/");
    }
}
