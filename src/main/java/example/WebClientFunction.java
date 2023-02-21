package example;

import io.micrometer.observation.ObservationRegistry;
import java.util.Map;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.observability.micrometer.Micrometer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

@Slf4j
@Component(value = "function")
public class WebClientFunction implements Function<Flux<Map<String, String>>, Flux<String>> {

  private WebClient webClient;

  @Autowired
  private ObservationRegistry observationRegistry;

  public WebClientFunction(Builder webClientBuilder) {
    Hooks.enableAutomaticContextPropagation();
    this.webClient = webClientBuilder.baseUrl("https://httpbin.org/json").build();
  }

  @Override
  public Flux<String> apply(Flux<Map<String, String>> flux) {
    return flux.flatMap(request -> {
      log.info("1) this does not have a trace id");
      return Mono.just(request).doOnNext(r -> log.info("2) this does not have a trace id", request)).then(webClient.get().retrieve().bodyToMono(Map.class).map(r -> {
        log.info("3) this does have a trace id");
        return r;
      }).thenReturn("OK"));

    }).tap(Micrometer.observation(observationRegistry));
  }


}
