package hr.brocom.generic.repository;

import hr.brocom.generic.configuration.FileConfig;
import hr.brocom.generic.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class FileDao extends AbstractRepositoryImpl<File, JpaRepository<File, Long>> implements AbstractRepository<File> {

    @Autowired
    private FileConfig fileConfig;

    public FileDao() {
        super(File.class);
    }

    public File createFile(final MultipartFile multipartFile) throws IOException {
        final Path newFile = Paths.get(fileConfig.getPath() + multipartFile.getResource().getFilename());
        Files.createDirectories(newFile.getParent());
        Files.write(newFile, multipartFile.getBytes());
        return create(new File(multipartFile.getResource().getFilename(), newFile.toAbsolutePath().toString(), multipartFile.getContentType()));
    }

    public FileSystemResource findFile(final Long id) {
        return new FileSystemResource(Paths.get(findById(id).getLocation()));
    }

    public void deleteFile(final Long id) throws IOException {
        final File byId = findById(id);
        delete(id);

        Files.delete(Paths.get(byId.getLocation()));

    }
}
