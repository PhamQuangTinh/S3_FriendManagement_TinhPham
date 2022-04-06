package com.example.MyAssignment.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "person", schema = "my_assignment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "email", length = 50, unique = true, nullable = false)
    private String email;


    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "status")
    private int status;

    @CreatedDate
    private Date createDate;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Friendship> friends;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Updates> updates;


    @PrePersist
    public void onCreate(){
        this.status = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return email.equals(person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

