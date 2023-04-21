package example;

import io.micrometer.observation.ObservationPredicate;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationRegistryCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class SpringSecurityMemoryLeakAutoConfiguration {

  @Bean
  ObservationRegistryCustomizer<ObservationRegistry> noSpringSecurityObservations() {
      System.out.println("***SpringSecurityMemoryLeakAutoConfiguration**");
      ObservationPredicate predicate = (name, context) -> !name.startsWith("spring.security.");
      return (registry) -> registry.observationConfig().observationPredicate(predicate);
  }
  
}
