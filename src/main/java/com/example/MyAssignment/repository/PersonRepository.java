package com.example.MyAssignment.repository;

import com.example.MyAssignment.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmail(String email);

    @Query(value = "" +
            "SELECT p.email " +
            "FROM Person p " +
            "INNER JOIN Friendship f ON p.id = f.friendId " +
            "WHERE f.person.id = :person_id " +
            "   AND f.person.status = 1 " +
            "GROUP BY p.email"
    )
    Set<String> findPersonFriendsById(@Param("person_id") long idPerson);
}
