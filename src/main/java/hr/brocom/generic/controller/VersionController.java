package hr.brocom.generic.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
@AllArgsConstructor
public class VersionController {

    private Properties properties;

    @GetMapping
    public ResponseEntity<Properties> getVersion() {
        return ResponseEntity.ok(properties);
    }
}

@Data
@Component
class Properties {
    @Value( "${application.version}" )
    private String version;
}