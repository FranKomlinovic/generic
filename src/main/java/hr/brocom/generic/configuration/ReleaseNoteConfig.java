package hr.brocom.generic.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@ConfigurationProperties
@PropertySource(value = "classpath:release-notes.yml", factory = YamlPropertySourceFactory.class)
@Data
public class ReleaseNoteConfig {
    private List<ReleaseNote> releaseNotes;
}
