package ai.gradientlabs.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application demonstrating Gradient Labs auto-configuration.
 * <p>
 * The GradientLabsClient bean is automatically configured from application.yml
 * and available for dependency injection throughout the application.
 * <p>
 * To run this example:
 * 1. Set GLABS_API_KEY environment variable
 * 2. Optionally set GLABS_WEBHOOK_KEY for webhook verification
 * 3. Run: mvn spring-boot:run
 */
@SpringBootApplication
public class GradientLabsSpringBootExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(GradientLabsSpringBootExampleApplication.class, args);
    }
}
