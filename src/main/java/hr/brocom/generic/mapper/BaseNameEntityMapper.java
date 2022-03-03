package hr.brocom.generic.mapper;

import hr.brocom.generic.entity.BaseNameEntity;
import hr.brocom.generic.entity.CodeBookEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BaseNameEntityMapper {

    default String fromCodeBook(CodeBookEntity entity) {
        if (entity == null) {
            return null;
        }
        return entity.getName();
    }

    default String fromBaseName(BaseNameEntity entity) {
        if (entity == null) {
            return null;
        }
        return entity.getName();
    }

    default List<String> fromCodeBook(List<? extends CodeBookEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(this::fromCodeBook).collect(Collectors.toList());

    }

    default List<String> fromBaseName(List<? extends BaseNameEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(this::fromBaseName).collect(Collectors.toList());

    }

}