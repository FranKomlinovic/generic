package hr.brocom.generic.service;

import hr.brocom.generic.entity.File;
import hr.brocom.generic.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class FileService {

    private FileRepository fileRepository;

    public File save(final MultipartFile file) throws IOException {
        final String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        final File dbFile = new File(fileName, file.getContentType(), file.getBytes());
        return fileRepository.save(dbFile);
    }

    public File find(final Long id) {
        return fileRepository.getOne(id);
    }

    public void delete(final Long id) {
        fileRepository.delete(fileRepository.getOne(id));
    }
}