package com.spitter.service.rest.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.spitter.orm.domain.Spitter;
import com.spitter.orm.domain.Spittle;
import com.spitter.service.rest.SpitterRestClient;

/**
 * @author Tyler Yin
 */
@Component
public class SpitterRestClientImpl implements SpitterRestClient {

    @Override
    public Spitter saveSpitter(Spitter spitter) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/Spitter/rest/saveSpitter";
        ResponseEntity<Spitter> response = restTemplate.postForEntity(url, spitter, Spitter.class);
        return response.getBody();
    }

    @Override
    public Spitter findByUserName(String userName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/Spitter/rest/{userName}";
        ResponseEntity<Spitter> response = restTemplate.getForEntity(url, Spitter.class, userName);
        return response.getBody();
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<Spittle> spittles() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/Spitter/rest/spittles";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        return (List<Spittle>) response.getBody();
    }
}
