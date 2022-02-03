package hr.brocom.generic.service;

import hr.brocom.generic.entity.File;
import hr.brocom.generic.repository.FileDao;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class FileService {
    private FileDao fileRepository;

    public File save(final MultipartFile multipartFile) throws IOException {
        return fileRepository.createFile(multipartFile);
    }

    public Resource find(final Long resourceId) {
        return fileRepository.findFile(resourceId);
    }

    public void delete(final Long id) throws IOException {
        fileRepository.deleteFile(id);
    }
}
