package example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.TestPropertySource;

@SpringBootApplication
@SpringBootTest
@TestPropertySource(properties = {
    "de.flapdoodle.mongodb.embedded.version=4.2.23",
    "spring.data.mongodb.uri=mongodb://localhost/test"
})
public class EmbeddedMongoTest {
  

  @Test
  void testName(@Autowired final ReactiveMongoTemplate mongoTemplate) throws Exception {    
    mongoTemplate.getMongoDatabase().map(db -> db.createCollection("deleteMe")).block();
    assertThat(mongoTemplate.getMongoDatabase().map(db-> db.getCollection("deleteMe")).block()).isNotNull();

  }
  
}
