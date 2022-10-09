package kr.pe.karsei.servletstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ServletComponentScan
@SpringBootApplication
public class ServletStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServletStudyApplication.class, args);
    }

    //@Bean
    //InternalResourceViewResolver internalResourceViewResolver() {
    //    return new InternalResourceViewResolver("/WEB-INF/views/", "/jsp");
    //}
}
