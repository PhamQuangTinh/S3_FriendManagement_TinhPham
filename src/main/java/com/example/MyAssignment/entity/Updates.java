package com.example.MyAssignment.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Table(name = "updates", schema = "my_assignment")
public class Updates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "update_id", nullable = false)
    private Long updateId;

    @Column(name = "status")
    private int status;

    @CreatedDate
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Person person;

    @PrePersist
    public void onCreate(){
        this.createDate = new Date();
        this.status = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Updates updates = (Updates) o;
        return updateId.equals(updates.updateId) && person.getEmail().equals(updates.person.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(updateId, person.getEmail());
    }
}
