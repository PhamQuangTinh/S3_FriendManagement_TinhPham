package com.example.MyAssignment.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "friendship", schema = "my_assignment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "friend_id", nullable = false)
    private Long friendId;

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
        this.status = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return friendId.equals(that.friendId) && person.getEmail().equals(that.person.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(friendId, person.getEmail());
    }
}
