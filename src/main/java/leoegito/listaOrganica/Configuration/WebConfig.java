package leoegito.listaOrganica.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods(HttpMethod.GET.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.DELETE.name(),
                                HttpMethod.OPTIONS.name())
                        .allowedHeaders(HttpHeaders.CONTENT_TYPE,
                                HttpHeaders.AUTHORIZATION,
                                HttpHeaders.ACCEPT,
                                HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                                HttpHeaders.LOCATION,
                                HttpHeaders.CONTENT_LOCATION
                                );
            }
        };
    }
}
