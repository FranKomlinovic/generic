package hr.brocom.generic.controller;

import hr.brocom.generic.entity.File;
import hr.brocom.generic.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "http://localhost:4200")
public class FileController {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractCrudController.class);

    @Autowired
    private FileService fileService;

    @PostMapping
    public ResponseEntity<File> upload(@RequestParam final MultipartFile file) throws IOException {
        LOGGER.info("Uploading file: {}...", file.getResource().getFilename());
        final long time = System.currentTimeMillis();
        final File created = fileService.save(file);
        LOGGER.debug("FileService.save() finished in {} ms", System.currentTimeMillis() - time);
        LOGGER.info("FileService.create() returned {}", created);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable final Long id) throws IOException {
        LOGGER.info("Deleting File: with ID: {}...", id);
        final long time = System.currentTimeMillis();
        fileService.delete(id);
        LOGGER.debug("FileService.delete() finished in {} ms", System.currentTimeMillis() - time);
        LOGGER.info("File deleted successfully");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable final Long id) throws IOException {
        LOGGER.info("Getting file with ID: {}...", id);
        final long time = System.currentTimeMillis();
        final File result = fileService.find(id);
        LOGGER.debug("FileService.findById() finished in {} ms", System.currentTimeMillis() - time);
        LOGGER.info("FileService.findById() returned {}", result.getName());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(result.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + result.getName() + "\"")
                .body(new ByteArrayResource(result.getData()));
    }

}
