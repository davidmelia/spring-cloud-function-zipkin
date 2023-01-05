package example;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component(value = "s3FluxFunction")
@AllArgsConstructor
public class S3FluxFunction implements Function<Flux<S3Event>, Flux<String>> {
	
	@Override
	public Flux<String> apply(Flux<S3Event> flux) {
		return flux.flatMap(fluxItem -> {
			log.info("S3 Event = {}", fluxItem);
			return Mono.just("OK");

		});
	}


}
