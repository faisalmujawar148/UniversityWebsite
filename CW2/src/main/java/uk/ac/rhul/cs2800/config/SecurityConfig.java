package uk.ac.rhul.cs2800.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuration class for setting up security settings, including CORS and CSRF handling.
 */
@Configuration
public class SecurityConfig {

  /**
   * Configures the security filter chain for the application. Disables CSRF protection and sets up
   * CORS handling.
   *
   * @param http The {@link HttpSecurity} object used to configure HTTP security.
   * @return The configured {@link SecurityFilterChain} object.
   * @throws Exception If an error occurs during security configuration.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // Disable CSRF protection and enable CORS with default settings.
    http.csrf((csrf) -> csrf.disable()).cors(withDefaults());

    // Return the configured SecurityFilterChain.
    return http.build();
  }

  /**
   * Configures CORS (Cross-Origin Resource Sharing) settings for the application. Allows all
   * resource sharing with credentials disabled.
   *
   * @return The {@link CorsConfigurationSource} that holds the CORS configuration.
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(Arrays.asList("*"));
    config.setAllowedHeaders(Arrays.asList("*"));
    config.setAllowedMethods(Arrays.asList("*"));

    // Disable credentials in CORS requests.
    config.setAllowCredentials(false);
    config.applyPermitDefaultValues();
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return source;
  }
}
