package hr.brocom.generic.controller;

import hr.brocom.generic.SearchCriteria;
import hr.brocom.generic.entity.BaseEntity;
import hr.brocom.generic.service.AbstractCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
public class AbstractCrudController<ENTITY extends BaseEntity, SERVICE extends AbstractCrudService<ENTITY>> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractCrudController.class);

    @Autowired
    protected SERVICE service;

    protected final String className;

    protected AbstractCrudController(final Class<ENTITY> type) {
        className = type.getSimpleName();
    }

    @PostMapping(path = "/list")
    public ResponseEntity<List<ENTITY>> findAll(@RequestBody final List<SearchCriteria> params) {
        LOGGER.info("Getting all {}s...", className);
        final long time = System.currentTimeMillis();
        final List<ENTITY> result = service.findAllBySearchCriteria(params);
        LOGGER.debug("{}.findAllBySearchCriteria() finished in {} ms", getServiceName(), System.currentTimeMillis() - time);
        LOGGER.info("{}.findAllBySearchCriteria() returned {} results", getServiceName(), result.size());
        return ResponseEntity.ok(result);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<ENTITY> findById(@PathVariable("id") final Long id) {
        LOGGER.info("Getting {} with ID: {}...", className, id);
        final long time = System.currentTimeMillis();
        final ENTITY result = service.findById(id);
        LOGGER.debug("{}.findById() finished in {} ms", getServiceName(), System.currentTimeMillis() - time);
        LOGGER.info("{}.findById() returned {}", getServiceName(), result);
        return ResponseEntity.ok(result);
    }

    @GetMapping()
    public ResponseEntity<List<ENTITY>> findAll() {
        LOGGER.info("Getting all {}s...", className);
        final long time = System.currentTimeMillis();
        final List<ENTITY> result = service.findAll(Sort.unsorted());
        LOGGER.debug("{}.findAll() finished in {} ms", getServiceName(), System.currentTimeMillis() - time);
        LOGGER.info("{}.findAll() returned {} results", getServiceName(), result.size());
        return ResponseEntity.ok(result);
    }

    @PostMapping()
    public ResponseEntity<ENTITY> create(@RequestBody @Valid final ENTITY entity) {
        LOGGER.info("Creating {}...", entity);
        final long time = System.currentTimeMillis();
        final ENTITY created = service.create(entity);
        LOGGER.debug("{}.create() finished in {} ms", getServiceName(), System.currentTimeMillis() - time);
        LOGGER.info("{}.create() returned {}", getServiceName(), created);
        return ResponseEntity.ok(created);
    }


    @PutMapping()
    public ResponseEntity<ENTITY> update(@RequestBody final ENTITY entity) {
        LOGGER.info("Updating {} with ID: {}...", className, entity.getId());
        final long time = System.currentTimeMillis();
        final ENTITY update = service.update(entity);
        LOGGER.debug("{}.update() finished in {} ms", getServiceName(), System.currentTimeMillis() - time);
        LOGGER.info("{}.update() returned {}", getServiceName(), update);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable final Long id) {
        LOGGER.info("Deleting {}: with ID: {}...", className, id);
        final long time = System.currentTimeMillis();
        service.delete(id);
        LOGGER.debug("{}.delete() finished in {} ms", getServiceName(), System.currentTimeMillis() - time);
        LOGGER.info("{} deleted successfully", className);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/list/{idList}")
    public ResponseEntity deleteList(@PathVariable final List<Long> idList) {
        LOGGER.info("Deleting {}: with IDs: {}...", className, idList);
        final long time = System.currentTimeMillis();
        service.deleteList(idList);
        LOGGER.debug("{}.deleteList() finished in {} ms", getServiceName(), System.currentTimeMillis() - time);
        LOGGER.info("{} list deleted successfully", className);
        return ResponseEntity.ok().build();
    }


    protected String getServiceName() {
        return service.getClass().getSimpleName();
    }
}
