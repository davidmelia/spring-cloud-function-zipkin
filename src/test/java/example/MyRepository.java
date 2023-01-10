package example;

import java.util.Map;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MyRepository extends ReactiveMongoRepository<Map<String,String>, String> {

}
