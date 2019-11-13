package com.thesisderik.appthesis;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.thesisderik.appthesis.converter.StringToPlainGroupConverter;
import com.thesisderik.appthesis.converter.StringToPlainTaskConverter;
import com.thesisderik.appthesis.converter.StringToColorConverter;
import com.thesisderik.appthesis.converter.StringToPlainFeatureConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToPlainGroupConverter());
        registry.addConverter(new StringToPlainFeatureConverter());
        registry.addConverter(new StringToPlainTaskConverter());
        registry.addConverter(new StringToColorConverter());
    }
}
