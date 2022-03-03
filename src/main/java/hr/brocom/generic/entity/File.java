package hr.brocom.generic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE file SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class File extends BaseEntity {

    @JsonIgnore
    private String name;
    @JsonIgnore
    private String type;
    @Lob
    @JsonIgnore
    private byte[] data;

}
