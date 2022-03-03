package hr.brocom.generic.ping;

import hr.brocom.generic.entity.BaseDto;
import lombok.Data;

@Data
public class PingDto extends BaseDto {
    private String message;
}
