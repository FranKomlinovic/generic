package hr.brocom.generic.ping;

import hr.brocom.generic.mapper.AbstractCrudMapper;
import hr.brocom.generic.mapper.GeneralMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = GeneralMapperConfig.class)
public interface PingMapper extends AbstractCrudMapper<Ping, PingDto> {
}
