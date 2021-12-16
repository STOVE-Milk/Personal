package com.steam.gateway.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService<T> {
    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<byte[]> get(String url, String queryString, MultiValueMap<String, String> header) {
        return restTemplate.exchange(url + queryString, HttpMethod.GET, new HttpEntity<>(null, header), byte[].class);
    }

    public ResponseEntity<byte[]> delete(String url, MultiValueMap<String, String> header) {
        return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(null, header), byte[].class);
    }

    public ResponseEntity<byte[]> post(String url, MultiValueMap<String, String> header, byte[] body) {
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, header), byte[].class);
    }

    public ResponseEntity<byte[]> put(String url, MultiValueMap<String, String> header, byte[] body) {
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(body, header), byte[].class);
    }

    public ResponseEntity<byte[]> patch(String url, MultiValueMap<String, String> header, byte[] body) {
        return restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body, header), byte[].class);
    }
}