package example;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component(value = "s3Function")
@AllArgsConstructor
public class S3Function implements Function<S3Event, String> {
	
	@Override
	public String apply(S3Event event) {
	  log.info("S3 Event = {}", event);
	  return "OK";
	}


}

