package com.andr3a.giacomini.sbproject.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("/index");
        registry.addViewController("/").setViewName("/index");
        registry.addViewController("/login").setViewName("/login");
        registry.addViewController("/admin").setViewName("/admin");
    }

    @Bean
    AuditorAware<String> auditorProvider(){
        return new AuditorAwareImpl();
    }

    @Bean
    public ModelMapper modelMapper() { return new ModelMapper(); }

    public class AuditorAwareImpl implements AuditorAware<String>{

        @Value("${spring.datasource.username}")
        private String springDatasourceUsername;
        @Override
        public Optional<String> getCurrentAuditor() {

            if(!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return Optional.of(user.getUsername());
            } else {
//                return Optional.empty();
                return Optional.of(springDatasourceUsername);
            }
        }
    }
}
