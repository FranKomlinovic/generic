package hr.brocom.generic.mapper;

import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BaseEnumMapper {

    default String toRepresentation(Enum enumObject) {
        if (enumObject == null) {
            return null;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        for (String word : enumObject.toString().split("_")) {
            stringBuilder.append(StringUtils.capitalize(word.toLowerCase(Locale.ROOT))).append(' ');
        }
        return stringBuilder.toString().trim();
    }

    default List<String> toRepresentation(List<Enum> enums) {
        return enums.stream().map(this::toRepresentation).collect(Collectors.toList());
    }
}