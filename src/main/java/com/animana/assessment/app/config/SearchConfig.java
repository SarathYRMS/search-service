package com.animana.assessment.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "search.upstream")
@Data
public class SearchConfig {
    private Integer maxResults;
    private String booksUrl;
    private String albumsUrl;
}

