package com.springingdream.adviser.repository;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.springingdream.adviser.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class HistoryService {
    private final RestTemplate template;

    private String url = "http://my.dekinci.com:10005";

    @Autowired
    public HistoryService(RestTemplate template, @Qualifier("eurekaClient") EurekaClient eurekaClient) {
        Application application = eurekaClient.getApplication("marketplace-history");
        InstanceInfo instanceInfo = application.getInstances().get(0);
        url = instanceInfo.getHomePageUrl();
        this.template = template;
    }

    public List<Rating> ratings(Long uid) {
        try {
            ResponseEntity<List<Rating>> response = template.exchange(
                    url + "/user/" + uid + "/ratings",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Rating>>(){});
            return response.getBody();
        } catch (RestClientException e) {
            return Collections.emptyList();
        }
    }

    @Bean
    public static RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
