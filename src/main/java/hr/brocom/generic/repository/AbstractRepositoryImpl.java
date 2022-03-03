package hr.brocom.generic.repository;

import hr.brocom.generic.AbstractSearchQueryCriteriaConsumer;
import hr.brocom.generic.SearchCriteria;
import hr.brocom.generic.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractRepositoryImpl<ENTITY extends BaseEntity, DAO extends JpaRepository<ENTITY, Long>> implements AbstractRepository<ENTITY> {

    @Autowired
    protected DAO dao;

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class type;

    public AbstractRepositoryImpl(final Class type) {
        this.type = type;
    }

    @Override
    public List<ENTITY> search(final List<SearchCriteria> params) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<ENTITY> query = builder.createQuery(type);
        final Root r = query.from(type);

        Predicate predicate = builder.conjunction();

        final AbstractSearchQueryCriteriaConsumer searchConsumer =
                new AbstractSearchQueryCriteriaConsumer(predicate, builder, r, type);
        params.forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<ENTITY> findAll(final Sort sort) {
        return dao.findAll(sort);
    }

    @Override
    public ENTITY findById(final Long id) {
        return dao.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ENTITY create(final ENTITY dto) {
        return dao.saveAndFlush(dto);
    }

    @Override
    public ENTITY update(final ENTITY dto) {
        findById(dto.getId());
        return dao.saveAndFlush(dto);
    }

    @Override
    public void delete(final Long id) {
        dao.deleteById(id);
    }

    @Override
    public void deleteList(final List<Long> id) {
        id.forEach(uuid -> dao.deleteById(uuid));
    }

}
