package hr.brocom.generic.controller;

import hr.brocom.generic.entity.CodeBookEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
public class AbstractCodeBookController<ENTITY extends CodeBookEntity, REPOSITORY extends JpaRepository<ENTITY, Long>> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractCodeBookController.class);

    @Autowired
    protected REPOSITORY repository;

    protected final String className;

    protected AbstractCodeBookController(final Class<ENTITY> type) {
        className = type.getSimpleName();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ENTITY> findById(@PathVariable("id") final Long id) {
        LOGGER.info("Getting {} with ID: {}...", className, id);
        final long time = System.currentTimeMillis();
        final ENTITY result = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("{}.findById() finished in {} ms", getRepositoryName(), System.currentTimeMillis() - time);
        LOGGER.info("{}.findById() returned {}", getRepositoryName(), result);
        return ResponseEntity.ok(result);
    }

    @GetMapping()
    public ResponseEntity<List<ENTITY>> findAll() {
        LOGGER.info("Getting all {}s...", className);
        final long time = System.currentTimeMillis();
        final List<ENTITY> result = repository.findAll(Sort.unsorted());
        LOGGER.debug("{}.findAll() finished in {} ms", getRepositoryName(), System.currentTimeMillis() - time);
        LOGGER.info("{}.findAll() returned {} results", getRepositoryName(), result.size());
        return ResponseEntity.ok(result);
    }

    protected String getRepositoryName() {
        return repository.getClass().getSimpleName();
    }
}
