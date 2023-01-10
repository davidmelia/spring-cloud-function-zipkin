package example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.TestPropertySource;

@SpringBootApplication
@SpringBootTest
@TestPropertySource(properties = {
    "de.flapdoodle.mongodb.embedded.version=4.2.23"
    ,"spring.data.mongodb.uri=mongodb://localhost/test"
})
//@TestPropertySource(properties = {
//    "spring.mongodb.embedded.version=4.2.19",
//    "spring.data.mongodb.uri=mongodb://localhost/test"
//})
public class EmbeddedMongoTest {

  @Autowired
  public MyRepository myRepository;

  @Test
  void testName(@Autowired final ReactiveMongoTemplate mongoTemplate) throws Exception {    
    Map<String, String> input = new HashMap<>();
    input.put("Some", "Name");

    myRepository.save(input).block();
    
    Map<String,String> result = myRepository.findAll().blockFirst();
    
    assertThat(result.get("Some")).isEqualTo("Name");

  }
  
}
