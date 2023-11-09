package bitcamp.show_pet.config;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// application-ncp.properties로 부터 불러온다.
@Component
@ConfigurationProperties(prefix = "naver-cloud-sms")
public class NcpSmsConfig {

  private String accessKey;
  private String secretKey;
  private String serviceId;
  private String senderPhone;

  public NcpSmsConfig() {
    System.out.println("✅SmsConfig() executed");
  }

  // 암호화
  public String makeSignature(Long time)
      throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
    String space = " ";
    String newLine = "\n";
    String method = "POST";
    String url = "/sms/v2/services/" + this.serviceId + "/messages";
    String timestamp = time.toString();
    String accessKey = this.accessKey;
    String secretKey = this.secretKey;

    String message = new StringBuilder()
        .append(method)
        .append(space)
        .append(url)
        .append(newLine)
        .append(timestamp)
        .append(newLine)
        .append(accessKey)
        .toString();

    SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(signingKey);

    byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
    String encodeBase64String = Base64.encodeBase64String(rawHmac);

    return encodeBase64String;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public String getSenderPhone() {
    return senderPhone;
  }

  public void setSenderPhone(String senderPhone) {
    this.senderPhone = senderPhone;
  }
}
