package tonton.server.config;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tonton.server.config.cloudinary.CloudinaryProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CloudinaryProperties.class)
public class CloudinaryConfig {

    private final CloudinaryProperties cloudinaryProperties;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", Objects.toString(cloudinaryProperties.getCloudName(), ""));
        config.put("api_key", Objects.toString(cloudinaryProperties.getApiKey(), ""));
        config.put("api_secret", Objects.toString(cloudinaryProperties.getApiSecret(), ""));
        config.put("secure", "true");

        return new Cloudinary(config);
    }
}
