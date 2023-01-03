package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(FunctionApplication.class);
	}

	
//	 @Bean
//	  @Order(MicrometerTracingAutoConfiguration.SENDER_TRACING_OBSERVATION_HANDLER_ORDER)
//	  public PropagatingSenderTracingObservationHandler<?> propagatingSenderTracingObservationHandler(Tracer tracer, Propagator propagator) {
//	    return new PropagatingSenderTracingObservationHandler<>(tracer, propagator) {
//	      @Override
//	      public void customizeSenderSpan(SenderContext context, Span span) {
//	        if (context instanceof ClientRequestObservationContext ctx) {
//	          span.remoteServiceName("dave");
//	          
//	        }
//	      }
//	    };
//	  }
}
