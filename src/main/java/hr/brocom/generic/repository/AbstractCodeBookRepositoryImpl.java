package hr.brocom.generic.repository;

import hr.brocom.generic.entity.CodeBookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public abstract class AbstractCodeBookRepositoryImpl<ENTITY extends CodeBookEntity> implements AbstractCodeBookRepository<ENTITY> {

    @Autowired
    protected JpaRepository<ENTITY, Long> dao;

    @Override
    public List<ENTITY> findAll(final Sort sort) {
        return dao.findAll(sort);
    }

    @Override
    public ENTITY findById(final Long id) {
        return dao.findById(id).orElseThrow(EntityNotFoundException::new);
    }


}
