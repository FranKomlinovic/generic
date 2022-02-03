package hr.brocom.generic.ping;

import hr.brocom.generic.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Getter
@Setter
@ToString
@Entity
public class Ping extends BaseEntity {
    private String message;
}
