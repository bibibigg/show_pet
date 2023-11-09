package bitcamp.show_pet.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kakao")
public class KakaoConfig {

  private String restApi;

  public KakaoConfig() {
    System.out.println("KakaoConfig() executed");
  }

  public String getRestApi() {
    return restApi;
  }

  public void setRestApi(String restApi) {
    this.restApi = restApi;
  }
}
