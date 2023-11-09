package bitcamp.show_pet.sms.controller;

import bitcamp.show_pet.member.model.vo.Member;
import bitcamp.show_pet.member.service.MemberService;
import bitcamp.show_pet.sms.Dto.MessageDto;
import bitcamp.show_pet.sms.Dto.SmsResponseDto;
import bitcamp.show_pet.sms.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

@Controller
@RequiredArgsConstructor
public class SmsController {

  private final SmsService smsService;
  private final MemberService memberService;

  @GetMapping("/send")
  public String getSmsPage() {
    return "/sms/sendSms";
  }


//  @PostMapping("/sms/send")
//  public String sendSms(MessageDto messageDto, Model model)
//      throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
//    SmsResponseDto response = smsService.sendSms(messageDto);
//    model.addAttribute("response", response);
//    return "/sms/result";
//  }

  @PostMapping("/sms/send")
  public String sendSmsAndGenerateVerificationCode(MessageDto messageDto, Model model)
          throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    // 사용자의 전화번호를 얻어온다 (예: messageDto.getPhoneNumber())
    String phoneNumber = messageDto.getTo();

    // SMS 보내고 인증번호 생성하는 서비스 메서드 호출
    SmsResponseDto response = smsService.sendSmsAndGenerateVerificationCode(messageDto, phoneNumber);
    System.out.println(response);

    model.addAttribute("response", response);
    return "/sms/result";
  }

  @PostMapping("/sms/send-and-generate-verification-code")
  public ResponseEntity<Map<String, String>> sendSmsAndGenerateVerificationCode(@RequestBody MessageDto messageDto)
          throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    // 전화번호를 클라이언트에서 받아옵니다.
    String phoneNumber = messageDto.getTo(); // 클라이언트에서 전화번호를 전달해야 합니다.

    // 인증번호 생성
    String verificationCode = smsService.generateAndSaveVerificationCode(phoneNumber);

    // 인증번호를 SMS 내용에 추가
    messageDto.setContent("Your verification code is: " + verificationCode);

    // SMS를 다시 보냄 (이 부분은 인증번호가 포함된 내용으로 보냄)
    SmsResponseDto response = smsService.sendSms(messageDto);

    // 클라이언트로 인증 코드를 응답합니다.
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("verificationCode", verificationCode);

    return ResponseEntity.ok(responseBody);
  }

}
