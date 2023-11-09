package bitcamp.show_pet.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ncp")
public class NcpConfig {

  private String endPoint;
  private String regionName;
  private String accessKey;
  private String secretKey;

  public NcpConfig() {
    System.out.println("✅NcpConfig() executed");
  }

  @Override
  public String toString() {
    return "NcpConfig{" +
        "endPoint='" + endPoint + '\'' +
        ", regionName='" + regionName + '\'' +
        ", accessKey='" + accessKey + '\'' +
        ", secretKey='" + secretKey + '\'' +
        '}';
  }

  public String getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
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

}
