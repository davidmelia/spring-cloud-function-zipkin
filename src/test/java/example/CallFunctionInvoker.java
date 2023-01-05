package example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.function.adapter.aws.FunctionInvoker;

@Slf4j
public class CallFunctionInvoker {

  @Test
  public void runS3FluxFunction() throws IOException {
    var bucketEntity = new S3EventNotification.S3BucketEntity("dev-bucket", null, null);
    var object = new S3EventNotification.S3ObjectEntity("s3Key", null, null, "SOME_VERSION", null);
    var s3 = new S3EventNotification.S3Entity(null, bucketEntity, object, null);
    var eventNotificationRecord = new S3EventNotification.S3EventNotificationRecord(null, null, null, DateTime.now().toString(), null, null, null, s3, null);
    var s3Event = new S3Event(Arrays.asList(eventNotificationRecord));
    runFunction("s3FluxFunction", s3Event);
  }
  
  @Test
  public void runS3Function() throws IOException {
    var bucketEntity = new S3EventNotification.S3BucketEntity("dev-bucket", null, null);
    var object = new S3EventNotification.S3ObjectEntity("s3Key", null, null, "SOME_VERSION", null);
    var s3 = new S3EventNotification.S3Entity(null, bucketEntity, object, null);
    var eventNotificationRecord = new S3EventNotification.S3EventNotificationRecord(null, null, null, DateTime.now().toString(), null, null, null, s3, null);
    var s3Event = new S3Event(Arrays.asList(eventNotificationRecord));
    runFunction("s3Function", s3Event);
  }
  
  @Test
  public void runSQSFluxFunction() throws IOException {
    var sqsMessage = new SQSEvent.SQSMessage();
    sqsMessage.setBody("body");   
    var sqsEvent = new SQSEvent();
    sqsEvent.setRecords(List.of(sqsMessage));
    runFunction("sqsFluxFunction", sqsEvent);
  }
  
  private void runFunction(String functionName, Object payload) throws IOException {
    FunctionInvoker handler = new FunctionInvoker(functionName);
    ObjectMapper om = new ObjectMapper();
    om.registerModule(new JodaModule());
    ByteArrayInputStream is = new ByteArrayInputStream(om.writeValueAsBytes(payload));
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    handler.handleRequest(is, outputStream, null);
    log.info("the result is {}", outputStream.toString());
    assertEquals("\"OK\"", outputStream.toString());   
  }
  
}
