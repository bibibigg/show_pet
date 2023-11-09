package bitcamp.show_pet.sms.service;

import bitcamp.show_pet.config.NcpSmsConfig;
import bitcamp.show_pet.sms.Dto.MessageDto;
import bitcamp.show_pet.sms.Dto.SmsRequestDto;
import bitcamp.show_pet.sms.Dto.SmsResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsService {

  private final NcpSmsConfig ncpSmsConfig;

  public SmsService(NcpSmsConfig ncpSmsConfig) {
    this.ncpSmsConfig = ncpSmsConfig;
  }

  public SmsResponseDto sendSms(MessageDto messageDto)
      throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    Long time = System.currentTimeMillis();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("x-ncp-apigw-timestamp", time.toString());
    headers.set("x-ncp-iam-access-key", ncpSmsConfig.getAccessKey());
    headers.set("x-ncp-apigw-signature-v2", ncpSmsConfig.makeSignature(time));

    List<MessageDto> messages = new ArrayList<>();
    messages.add(messageDto);

    SmsRequestDto request = SmsRequestDto.builder()
        .type("SMS")
        .contentType("COMM")
        .countryCode("82")
        .from(ncpSmsConfig.getSenderPhone())
        .content(messageDto.getContent())
        .messages(messages)
        .build();

    ObjectMapper objectMapper = new ObjectMapper();
    String body = objectMapper.writeValueAsString(request);
    HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    SmsResponseDto response = restTemplate.postForObject(new URI(
        "https://sens.apigw.ntruss.com/sms/v2/services/" + ncpSmsConfig.getServiceId()
            + "/messages"), httpBody, SmsResponseDto.class);

    return response;
  }


  private Map<String, String> verificationCodes = new HashMap<>();

  // Generate a random verification code of the specified length
  private String generateRandomCode(int length) {
    Random random = new Random();
    StringBuilder code = new StringBuilder();

    for (int i = 0; i < length; i++) {
      code.append(random.nextInt(10)); // Generate random digits (0-9)
    }

    return code.toString();
  }

  // 인증번호 생성 및 저장
  public String generateAndSaveVerificationCode(String phoneNumber) {
    // 6자리의 랜덤 인증번호 생성
    String verificationCode = generateRandomCode(6);

    // 전화번호와 연결하여 인증번호를 저장합니다 (데이터베이스 또는 메모리 저장소를 사용할 수 있습니다).
    // 이 예제에서는 HashMap을 사용합니다.
    verificationCodes.put(phoneNumber, verificationCode);

    return verificationCode;
  }

  public SmsResponseDto sendSmsAndGenerateVerificationCode(MessageDto messageDto, String phoneNumber)
          throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    // SMS 보내는 로직 (이미 구현된 코드)

    // 인증번호 생성
    String verificationCode = generateAndSaveVerificationCode(phoneNumber);

    // 인증번호를 SMS 내용에 추가
    messageDto.setContent("Your verification code is: " + verificationCode);

    // SMS를 다시 보냄 (이 부분은 인증번호가 포함된 내용으로 보냄)
    SmsResponseDto response = sendSms(messageDto);

    return response;
  }
}
