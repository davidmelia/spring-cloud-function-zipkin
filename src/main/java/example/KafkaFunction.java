package example;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component(value = "function")
@AllArgsConstructor
public class KafkaFunction implements Function<Flux<Map<String, String>>, Flux<String>> {

    private final String cacheControlHeader = "cache-control-delete-key";

    private StreamBridge streamBridge;
	
	@Override
	public Flux<String> apply(Flux<Map<String, String>> flux) {
		return flux.flatMap(response -> {
		    log.info("response is {}",response);
		    String id = UUID.randomUUID().toString();
		    Message<KafkaNull> kafkaEvent = MessageBuilder
		            .withPayload(KafkaNull.INSTANCE)
					.setHeader(KafkaHeaders.MESSAGE_KEY, id).setHeader(cacheControlHeader, id).build();
			log.info("Sending message to binding = {}", kafkaEvent);
			if (streamBridge.send("test-out-0", kafkaEvent)) {
				log.info("Message sent to binding = {}", kafkaEvent);
				return Mono.just("OK").delayElement(Duration.ofSeconds(4));
			} else {
				log.error("Error occurred while sending message = {} to the binding.", kafkaEvent);
				return Mono.error(new RuntimeException("event publishing failed"));
			}
		});
	}


}
