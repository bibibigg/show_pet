package bitcamp.show_pet;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;


@EnableTransactionManagement
@SpringBootApplication
public class App implements WebMvcConfigurer {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(App.class, args);
  }

//    @GetMapping("/")
//    public String hello() {
//        return "index";
//    }

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    UrlPathHelper pathHelper = new UrlPathHelper();
    pathHelper.setRemoveSemicolonContent(false);
    configurer.setUrlPathHelper(pathHelper);
  }
}
