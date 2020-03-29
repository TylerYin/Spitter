package com.spitter.config;

import java.util.regex.Pattern;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import com.spitter.config.RootConfig.WebPackage;

/**
 * @author Tyler Yin
 */
@Configuration
@Import(DataConfig.class)
@ComponentScan(basePackages = {"com.spitter"}, excludeFilters = {
        @Filter(type = FilterType.CUSTOM, value = WebPackage.class)})

public class RootConfig {
    public static class WebPackage extends RegexPatternTypeFilter {
        public WebPackage() {
            super(Pattern.compile("spittr\\.web"));
        }
    }
}