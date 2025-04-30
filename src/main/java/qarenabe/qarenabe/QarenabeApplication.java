package qarenabe.qarenabe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QarenabeApplication {

	public static void main(String[] args) {
		SpringApplication.run(QarenabeApplication.class, args);
	}

}
