package de.tonyskulk.gtvisual;

import java.util.concurrent.CountDownLatch;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@SpringBootApplication
public class GtVisualApplication {

  @Autowired
  private MappingMongoConverter mongoConverter;

  @Bean
  public CountDownLatch closeLatch() {
    return new CountDownLatch(1);
  }

  // Converts . into a mongo friendly char
  @PostConstruct
  public void setUpMongoEscapeCharacterConversion() {
    mongoConverter.setMapKeyDotReplacement(",");
  }

  public static void main(String[] args) throws InterruptedException {
    ConfigurableApplicationContext ctx = SpringApplication.run(GtVisualApplication.class, args);

    final CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        closeLatch.countDown();
      }
    });
    closeLatch.await();
  }
}
