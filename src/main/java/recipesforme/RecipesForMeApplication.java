package recipesforme;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import recipesforme.bl.services.StorageProperties;
import recipesforme.bl.services.StorageService;

@SpringBootApplication
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

//	@Bean
//	public DataSourceInitializer dataSourceInitializer(@Qualifier("dataSource") final DataSource dataSource) {
//		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
//		resourceDatabasePopulator.addScript(new ClassPathResource("/data.sql"));
//		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
//		dataSourceInitializer.setDataSource(dataSource);
//		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
//		return dataSourceInitializer;
//	}

}
