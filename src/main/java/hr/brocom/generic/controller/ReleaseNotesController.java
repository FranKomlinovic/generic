package hr.brocom.generic.controller;

import hr.brocom.generic.configuration.ReleaseNote;
import hr.brocom.generic.configuration.ReleaseNoteConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/release-notes")
@AllArgsConstructor
public class ReleaseNotesController {

    private ReleaseNoteConfig releaseNoteConfig;

    @GetMapping
    public ResponseEntity<List<ReleaseNote>> getAllVersions() {
        return ResponseEntity.ok(releaseNoteConfig.getReleaseNotes());
    }

    @GetMapping("/current")
    public ResponseEntity<ReleaseNote> getCurrentVersion(final @Value("${project.version}") String version) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(releaseNoteConfig.getReleaseNotes().stream().filter(a -> a.getVersion().equalsIgnoreCase(version))
                .findFirst().orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @GetMapping("/{version}")
    public ResponseEntity<ReleaseNote> getAllVersions(final @PathVariable("version") String version) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(releaseNoteConfig.getReleaseNotes().stream().filter(a -> a.getVersion().equalsIgnoreCase(version))
                .findFirst().orElseThrow(ChangeSetPersister.NotFoundException::new));
    }
}
