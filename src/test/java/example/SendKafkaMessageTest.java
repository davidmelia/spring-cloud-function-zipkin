package example;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.Assert;

@SpringBootApplication
@SpringBootTest
@TestPropertySource(properties = {
    "spring.cloud.stream.bindings.test-out-0.destination=test"
    ,"spring.cloud.stream.kafka.bindings.test-out-0.producer.configuration.key.serializer=org.apache.kafka.common.serialization.StringSerializer"
    ,"spring.cloud.stream.kafka.bindings.test-out-0.producer.configuration.value.serializer=org.springframework.kafka.support.serializer.JsonSerializer"
    ,"spring.cloud.stream.kafka.binder.configuration.ssl.protocol=TLSv1.2"
    ,"spring.cloud.stream.kafka.binder.enable-observation=true"
      
})
public class SendKafkaMessageTest {
  
  @Autowired
  private StreamBridge streamBridge;

  @Test
  void testName() throws Exception {
    Object kafkaEvent = MessageBuilder
        .withPayload(Map.of("Key", String.format("Value @ %s", ZonedDateTime.now(ZoneOffset.UTC))))
        .setHeaderIfAbsent(KafkaHeaders.KEY, UUID.randomUUID().toString())
        .build();
    boolean result = streamBridge.send("test-out-0", kafkaEvent);
    Assert.isTrue(result);
  }
  
}
