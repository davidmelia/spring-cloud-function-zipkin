package example;

import io.micrometer.context.ContextSnapshot;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import java.time.Duration;
import java.util.Map;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component(value = "function")
public class WebClientFunction implements Function<Flux<Map<String, String>>, Flux<String>> {

	private WebClient webClient;
	
	  public WebClientFunction(Builder webClientBuilder) {
	    this.webClient = webClientBuilder.baseUrl("https://httpbin.org/json").build();
	  }

	@Override
	public Flux<String> apply(Flux<Map<String, String>> flux) {
		return flux.flatMap(response -> {
          return Mono.deferContextual(contextView -> {
            try (ContextSnapshot.Scope scope = ContextSnapshot.setThreadLocalsFrom(contextView,
                    ObservationThreadLocalAccessor.KEY)) {
		  
		    log.info("response is {}",response);
			return webClient.get().retrieve().bodyToMono(Map.class).map(r -> {log.info("response={}",r); return r;} ).thenReturn("OK").delayElement(Duration.ofSeconds(4));
            }});
		});
	}
	

}
