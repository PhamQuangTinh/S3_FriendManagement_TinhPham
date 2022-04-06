package com.example.MyAssignment.repository;

import com.example.MyAssignment.entity.Updates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

public interface UpdatesRepository extends JpaRepository<Updates, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Updates SET status = :status WHERE person.id = :targetId AND updateId = :requestorId")
    void updateStatusUpdates(@Param("requestorId")long requestorId, @Param("targetId") long targetId, @Param("status") int status);

    Optional<Updates> findByUpdateIdAndPerson_Id(long target, long requestorId);

    @Query(value = "" +
            "SELECT updateId " +
            "FROM Updates " +
            "WHERE person.id = :personId " +
            "GROUP BY updateId ")
    Set<Integer> findValidReceiver(long personId);
}
