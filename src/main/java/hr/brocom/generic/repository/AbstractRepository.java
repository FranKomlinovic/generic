package hr.brocom.generic.repository;

import hr.brocom.generic.SearchCriteria;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AbstractRepository<ENTITY> {

    List<ENTITY> findAll(Sort sort);

    ENTITY findById(Long id);

    ENTITY create(ENTITY dto);

    ENTITY update(ENTITY dto);

    void delete(Long id);

    void deleteList(List<Long> id);

    List<ENTITY> search(List<SearchCriteria> params);

}
