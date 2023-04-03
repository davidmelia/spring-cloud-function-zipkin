package example;

import brave.baggage.BaggageField;
import brave.baggage.CorrelationScopeConfig;
import brave.baggage.CorrelationScopeCustomizer;
import java.util.List;
import org.springframework.boot.actuate.autoconfigure.tracing.BraveAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.tracing.TracingProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;


@AutoConfiguration(after = BraveAutoConfiguration.class)
@ConditionalOnClass({ BaggageField.class})
@ConditionalOnBean(TracingProperties.class)
public class CorrelationFieldMemoryLeakAutoConfiguration {

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
