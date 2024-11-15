package com.remotefalcon.external.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.remotefalcon.external.api.aop.RequiresAccess;
import com.remotefalcon.external.api.response.ShowResponse;
import com.remotefalcon.external.api.service.ExternalApiService;
import com.remotefalcon.library.documents.Show;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ExternalApiController {
  private final ExternalApiService externalApiService;

  @GetMapping(value = "/showDetails")
  @RequiresAccess
  public ResponseEntity<ShowResponse> showDetails() throws IOException {
    return this.externalApiService.showDetails();
  }
}
