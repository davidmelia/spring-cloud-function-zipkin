package example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.function.adapter.aws.FunctionInvoker;

@Slf4j
public class CallFunctionInvoker {

  
  
  @Test
  public void runFunctionInvoker() throws IOException {
    FunctionInvoker handler = new FunctionInvoker("function");
    ObjectMapper om = new ObjectMapper();
    om.registerModule(new JodaModule());
    ByteArrayInputStream is = new ByteArrayInputStream(om.writeValueAsBytes(create("mys3key")));
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    handler.handleRequest(is, outputStream, null);
    log.info("the result is {}", outputStream.toString());
    assertEquals("\"OK\"", outputStream.toString());
  }

  public static S3Event create(String s3Key) {

    var bucketEntity = new S3EventNotification.S3BucketEntity("dev-bucket", null, null);
    var object = new S3EventNotification.S3ObjectEntity(s3Key, null, null, "SOME_VERSION", null);
    var s3 = new S3EventNotification.S3Entity(null, bucketEntity, object, null);
    var eventNotificationRecord = new S3EventNotification.S3EventNotificationRecord(null, null, null, DateTime.now().toString(), null, null, null, s3, null);
    var s3Event = new S3Event(Arrays.asList(eventNotificationRecord));

    return s3Event;
  }

}
