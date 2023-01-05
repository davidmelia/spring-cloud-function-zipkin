package example;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.TestPropertySource;

@SpringBootApplication
@SpringBootTest
@TestPropertySource(properties = {
    "spring.cloud.stream.bindings.test-out-0.destination=test"
    ,"spring.cloud.stream.kafka.bindings.test-out-0.producer.configuration.key.serializer=org.apache.kafka.common.serialization.StringSerializer"
    ,"spring.cloud.stream.kafka.bindings.test-out-0.producer.configuration.value.serializer=org.springframework.kafka.support.serializer.JsonSerializer"
      
})
public class KafkaNullTest {
  
  @Autowired
  private StreamBridge streamBridge;

  @Test
  void testName() throws Exception {
    String id = UUID.randomUUID().toString();
    Message<KafkaNull> kafkaEvent = MessageBuilder
            .withPayload(KafkaNull.INSTANCE)
            .setHeader(KafkaHeaders.KEY, id).build();
    streamBridge.send("test-out-0", kafkaEvent);
  }
  
}
