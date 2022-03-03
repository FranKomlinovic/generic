package hr.brocom.generic.mapper;

import hr.brocom.generic.entity.BaseDto;
import hr.brocom.generic.entity.BaseEntity;

import java.util.List;

public interface AbstractCrudMapper<ENTITY extends BaseEntity, DTO extends BaseDto> {
    DTO toDto(ENTITY entity);

    List<DTO> toDto(List<ENTITY> entity);

}