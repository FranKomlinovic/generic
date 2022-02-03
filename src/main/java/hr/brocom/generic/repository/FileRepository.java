package hr.brocom.generic.repository;

import hr.brocom.generic.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
