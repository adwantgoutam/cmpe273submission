package hello;

import java.net.UnknownHostException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.mongodb.Mongo;

@ComponentScan
@EnableAutoConfiguration
public class Application {
	
	public @Bean Mongo mongo() throws UnknownHostException{
	       return new Mongo("localhost");
	   }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
