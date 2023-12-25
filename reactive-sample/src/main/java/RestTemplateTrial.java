import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;



import java.time.Duration;

public class RestTemplateTrial {

  RestTemplate restTemplate =
      new RestTemplateBuilder().setConnectTimeout(Duration.ofDays(1000L)).build();


  @JsonInclude
  class MyObj{}
}
