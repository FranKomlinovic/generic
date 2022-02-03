package hr.brocom.generic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;

@ToString
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class BaseNameEntity extends BaseEntity {
    private String name;
}
