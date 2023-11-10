package com.glam.bff.client.polycam;

import com.glam.bff.dto.polycam.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PolycamClient {

  private static final String URL = "https://poly.cam/api/generate-texture";
  private static final String DEVICE_ID = "_fuCB1gLvjC70bzu9UJWqF";
  private static final String USER_ID = "4fCplEejPcReuGdceUui4kONBaF2";

  public static ResponseDTO fetchRemoteData(String prompt) {

    RestTemplate restTemplate = new RestTemplate();

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
            .queryParam("prompt", prompt)
            .queryParam("device_id", DEVICE_ID)
            .queryParam("user_id", USER_ID);

    ResponseEntity<ResponseDTO> response = restTemplate.getForEntity(builder.toUriString(), ResponseDTO.class);

    return response.getBody();
  }
}
