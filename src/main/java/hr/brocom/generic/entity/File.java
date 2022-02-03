package hr.brocom.generic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE file SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class File extends BaseEntity {

    private String name;

    private String location;

    private String contentType;

}
