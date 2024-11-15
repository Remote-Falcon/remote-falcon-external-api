package com.remotefalcon.external.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remotefalcon.external.api.response.ShowResponse;
import com.remotefalcon.library.documents.Show;
import com.remotefalcon.external.api.repository.ShowRepository;
import com.remotefalcon.external.api.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExternalApiService {
    private final ShowRepository showRepository;
    private final AuthUtil authUtil;
    private final DozerBeanMapper mapper;

    public ResponseEntity<ShowResponse> showDetails() throws IOException {
        String showToken = this.authUtil.showToken;
        if(showToken == null) {
            return ResponseEntity.status(401).build();
        }
        Optional<Show> show = this.showRepository.findByShowToken(showToken);
        if(show.isPresent()) {
            if(CollectionUtils.isEmpty(show.get().getRequests())) {
                show.get().setRequests(new ArrayList<>());
            }
            if(CollectionUtils.isEmpty(show.get().getVotes())) {
                show.get().setVotes(new ArrayList<>());
            }
            ShowResponse showResponse = mapper.map(show.get(), ShowResponse.class);
            return ResponseEntity.status(200).body(showResponse);
        }
        return ResponseEntity.status(400).build();
    }
}
