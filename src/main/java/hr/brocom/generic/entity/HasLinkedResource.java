package hr.brocom.generic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface HasLinkedResource {
    default List<String> getLinkedResources() {

        if (linkedResources().noneMatch(Objects::nonNull)) {
            return null;
        }

        return linkedResources()
                .flatMap(Collection::stream)
                .map(BaseNameEntity::getName).distinct().collect(Collectors.toList());
    }

    @JsonIgnore
    Stream<List<? extends BaseNameEntity>> linkedResources();
}
