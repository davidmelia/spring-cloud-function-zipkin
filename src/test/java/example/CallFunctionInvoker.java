package example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.cloud.function.adapter.aws.FunctionInvoker;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CallFunctionInvoker {

	@Test
	public void runFunctionInvoker() throws IOException {
		FunctionInvoker handler = new FunctionInvoker("function");
		ObjectMapper om = new ObjectMapper();
		ByteArrayInputStream is = new ByteArrayInputStream(om.writeValueAsBytes(Map.of()));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		handler.handleRequest(is, outputStream, null);
		log.info("the result is {}", outputStream.toString());
		assertEquals("\"OK\"", outputStream.toString());
	}

}
