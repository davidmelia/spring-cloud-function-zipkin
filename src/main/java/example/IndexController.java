package example;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class IndexController {

  private WebClient webClient;
  
  public IndexController(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("https://httpbin.org/json").build();
  }

  @GetMapping("/memoryLeak")
  public Mono<Map> getStuff() {
    return webClient.get().retrieve().bodyToMono(Map.class).map(r -> {System.out.println(r); return Map.of("response",r);} );
  }


}
