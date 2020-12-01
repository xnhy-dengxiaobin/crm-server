package io.renren.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String url = getClass().getClassLoader().getResource("").getPath()+ "static/";
        registry.addResourceHandler("/images/**")
                .addResourceLocations(url);
        super.addResourceHandlers(registry);
    }
}
