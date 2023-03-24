package example;


import brave.baggage.BaggageField;
import brave.baggage.CorrelationScopeConfig;
import brave.baggage.CorrelationScopeCustomizer;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.tracing.TracingProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
  
  @PostConstruct
  public void init() {
    Hooks.enableAutomaticContextPropagation();
  }

  @Bean
  @Order(1)
  CorrelationScopeCustomizer myCorrelationFieldsCorrelationScopeCustomizer(TracingProperties tracingProperties) {
    return (builder) -> {
      List<String> correlationFields = tracingProperties.getBaggage().getCorrelation().getFields();
      for (String field : correlationFields) {
        builder.add(CorrelationScopeConfig.SingleCorrelationField.newBuilder(BaggageField.create(field))
                .build());
      }
    };
  }
}
