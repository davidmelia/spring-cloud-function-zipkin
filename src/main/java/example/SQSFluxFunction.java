package example;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component(value = "sqsFluxFunction")
@AllArgsConstructor
public class SQSFluxFunction implements Function<Flux<SQSEvent>, Flux<String>> {
	
	@Override
	public Flux<String> apply(Flux<SQSEvent> flux) {
		return flux.flatMap(fluxItem -> {
			log.info("SQS Event = {}", fluxItem);
			return Mono.just("OK");

		});
	}


}
