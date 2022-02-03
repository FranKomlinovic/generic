package hr.brocom.generic.repository;

import hr.brocom.generic.entity.CodeBookEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AbstractCodeBookRepository<ENTITY extends CodeBookEntity> {

    List<ENTITY> findAll(Sort sort);

    ENTITY findById(Long id);

}
