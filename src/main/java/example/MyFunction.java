package example;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import java.time.Duration;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component(value = "function")
@AllArgsConstructor
public class MyFunction implements Function<Flux<S3Event>, Flux<String>> {
	
	@Override
	public Flux<String> apply(Flux<S3Event> flux) {
		return flux.flatMap(fluxItem -> {
			log.info("Sending message to binding = {}", fluxItem);
			return Mono.just("OK").delayElement(Duration.ofSeconds(4));

		});
	}


}
