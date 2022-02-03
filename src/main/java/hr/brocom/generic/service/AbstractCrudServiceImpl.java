package hr.brocom.generic.service;

import hr.brocom.generic.SearchCriteria;
import hr.brocom.generic.entity.BaseEntity;
import hr.brocom.generic.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Abstract service for CRUD methods.
 *
 * @param <ENTITY> ENTITY object type
 * @author fran.komlinovic
 */
public abstract class AbstractCrudServiceImpl<ENTITY extends BaseEntity> implements AbstractCrudService<ENTITY> {


    @Autowired
    protected AbstractRepository<ENTITY> repository;

    @Override
    public List<ENTITY> findAll(final Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public ENTITY findById(final Long id) {
        return repository.findById(id);
    }

    @Override
    public ENTITY create(final ENTITY dto) {
        return repository.create(dto);
    }

    @Override
    public ENTITY update(final ENTITY dto) {
        return repository.update(dto);
    }

    @Override
    public void delete(final Long id) {
        repository.delete(id);
    }

    @Override
    public void deleteList(final List<Long> id) {
        repository.deleteList(id);
    }

    @Override
    public List<ENTITY> findAllBySearchCriteria(final List<SearchCriteria> params) {
        return repository.search(params);
    }
}
