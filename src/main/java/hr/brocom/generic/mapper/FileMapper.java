package hr.brocom.generic.mapper;

import hr.brocom.generic.entity.File;
import org.mapstruct.Mapper;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FileMapper {

    default String toRepresentation(File file) {
        if (file == null) {
            return null;
        }

        if ((file.getData() == null || file.getType() == null) && file.getId() != null) {
            return file.getId().toString();
        }

        return "data:" + file.getType() +
                ";base64," +
                Base64.getEncoder().encodeToString(file.getData());
    }

    default List<String> toRepresentation(List<File> enums) {
        return enums.stream().map(this::toRepresentation).collect(Collectors.toList());
    }
}