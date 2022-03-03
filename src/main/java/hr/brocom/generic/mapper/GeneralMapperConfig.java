package hr.brocom.generic.mapper;

import org.mapstruct.MapperConfig;

@MapperConfig(componentModel = "spring", uses = {BaseNameEntityMapper.class, BaseEnumMapper.class, FileMapper.class})
public interface GeneralMapperConfig {
}
