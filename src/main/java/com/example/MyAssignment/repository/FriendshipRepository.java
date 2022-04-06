package com.example.MyAssignment.repository;

import com.example.MyAssignment.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query( "SELECT f1.friendId " +
            "FROM Friendship f1 " +
            "       INNER JOIN Friendship f2 ON (f1.friendId = f2.friendId) " +
            "WHERE f1.person.id = :person1 " +
            "   AND f2.person.id = :person2")
    Set<Integer> getCommonFriendId(@Param("person1") long personId1, @Param("person2") long personId2);

    @Query( "SELECT f.friendId " +
            "FROM Friendship f " +
            "       LEFT JOIN Updates u ON f.friendId = u.updateId " +
            "WHERE f.person.id = :personId " +
            "   AND f.status = 1 " +
            "   AND(u IS NULL OR (u IS NOT NULL AND u.status = 1)) "
    )
    Set<Integer> findValidFriendIdForUpdates(@Param("personId") long personId);
}
