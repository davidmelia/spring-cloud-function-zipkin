package example;


import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
  
  @Bean
  public WebFluxConfigurer corsConfigurer() {
    return new WebFluxConfigurer() {
      @Override
      public void addCorsMappings(org.springframework.web.reactive.config.CorsRegistry registry) {

        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods(HttpMethod.GET.toString(), HttpMethod.POST.toString(), HttpMethod.PUT.toString(), HttpMethod.DELETE.toString(), HttpMethod.OPTIONS.toString(),
                HttpMethod.PATCH.toString(), HttpMethod.HEAD.toString());
      }

    };
  }
  
  @PostConstruct
  public void init() {
    Hooks.enableAutomaticContextPropagation();
  }
}
