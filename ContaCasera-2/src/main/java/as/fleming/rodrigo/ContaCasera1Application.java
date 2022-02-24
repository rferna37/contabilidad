package as.fleming.rodrigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching  // Activa la caché de métodos.
public class ContaCasera1Application {

	public static void main(String[] args) {
		SpringApplication.run(ContaCasera1Application.class, args);
	}

}
