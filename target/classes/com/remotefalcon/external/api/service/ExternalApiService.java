package com.remotefalcon.external.api.service;

import com.remotefalcon.external.api.request.RequestVoteRequest;
import com.remotefalcon.external.api.response.RequestVoteResponse;
import com.remotefalcon.external.api.response.ShowResponse;
import com.remotefalcon.library.documents.Show;
import com.remotefalcon.external.api.repository.ShowRepository;
import com.remotefalcon.external.api.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalApiService {
    private final ShowRepository showRepository;
    private final AuthUtil authUtil;
    private final DozerBeanMapper mapper;

    @Value("${viewer.api.url}")
    String viewerApiUrl;

    public ResponseEntity<ShowResponse> showDetails() {
        String showToken = this.authUtil.showToken;
        if(showToken == null) {
            return ResponseEntity.status(401).build();
        }
        Optional<Show> show = this.showRepository.findByShowToken(showToken);
        if(show.isPresent()) {
            ShowResponse showResponse = mapper.map(show.get(), ShowResponse.class);
            return ResponseEntity.status(200).body(showResponse);
        }
        return ResponseEntity.status(400).build();
    }

    public ResponseEntity<RequestVoteResponse> addSequenceToQueue(RequestVoteRequest requestVoteRequest) {
        String showToken = this.authUtil.showToken;
        if(showToken == null) {
            return ResponseEntity.status(401).build();
        }
        Optional<Show> show = this.showRepository.findByShowToken(showToken);
        if(show.isPresent()) {
            RestTemplate restTemplate = new RestTemplate();
            requestVoteRequest.setShowSubdomain(show.get().getShowSubdomain());
            try {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(this.authUtil.getTokenFromRequest(request));
                HttpEntity<RequestVoteRequest> requestEntity = new HttpEntity<>(requestVoteRequest, headers);
                return restTemplate.postForEntity(viewerApiUrl + "/addSequenceToQueue", requestEntity, RequestVoteResponse.class);
            }catch (HttpClientErrorException e) {
                return ResponseEntity.status(400).body(e.getResponseBodyAs(RequestVoteResponse.class));
            }
        }
        return ResponseEntity.status(400).build();
    }

    public ResponseEntity<RequestVoteResponse> voteForSequence(RequestVoteRequest requestVoteRequest) {
        String showToken = this.authUtil.showToken;
        if(showToken == null) {
            return ResponseEntity.status(401).build();
        }
        Optional<Show> show = this.showRepository.findByShowToken(showToken);
        if(show.isPresent()) {
            RestTemplate restTemplate = new RestTemplate();
            requestVoteRequest.setShowSubdomain(show.get().getShowSubdomain());
            try {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(this.authUtil.getTokenFromRequest(request));
                HttpEntity<RequestVoteRequest> requestEntity = new HttpEntity<>(requestVoteRequest, headers);
                return restTemplate.postForEntity(viewerApiUrl + "/voteForSequence", requestEntity, RequestVoteResponse.class);
            }catch (HttpClientErrorException e) {
                return ResponseEntity.status(400).body(e.getResponseBodyAs(RequestVoteResponse.class));
            }
        }
        return ResponseEntity.status(400).build();
    }
}
