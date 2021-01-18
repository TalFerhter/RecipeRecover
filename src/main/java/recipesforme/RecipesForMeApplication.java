package recipesforme;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import recipesforme.bl.services.StorageProperties;
import recipesforme.bl.services.StorageService;

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties(StorageProperties.class)
public class RecipesForMeApplication {

	public static void main(String[] args) {

		SpringApplication.run(RecipesForMeApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
