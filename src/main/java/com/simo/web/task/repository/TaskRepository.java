package com.simo.web.task.repository;

import com.simo.web.task.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<List<TaskEntity>> findAllByClient_Email(String email);

    Optional<List<TaskEntity>> findAllByName(String email);

    @Query(value = "SELECT t FROM TaskEntity t " +
            "WHERE t.name like CONCAT('%', :name, '%') " +
            "AND t.client.firstName like CONCAT('%', :clientFirstName, '%') " +
            "AND t.client.lastName like CONCAT('%', :clientLastName, '%') ")
    Optional<List<TaskEntity>> getTasksBySearchParameters(@Param("name") String name,
                                                          @Param("clientFirstName") String clientFirstName,
                                                          @Param("clientLastName") String clientLastName);

    @Query(value = "SELECT t.name FROM TaskEntity t " +
            "WHERE t.name like CONCAT('%', :taskName, '%')")
    Optional<List<String>> filterByTaskName(@Param("taskName") String taskName);

    @Query(value = "SELECT DISTINCT t.client.firstName FROM TaskEntity t " +
            "WHERE t.client.firstName like CONCAT('%', :clientFirstName, '%')")
    Optional<List<String>> filterByClientFirstName(@Param("clientFirstName") String clientFirstName);

    @Query(value = "SELECT DISTINCT t.client.lastName FROM TaskEntity t " +
            "WHERE t.client.lastName like CONCAT('%', :clientLastName, '%')")
    Optional<List<String>> filterByClientLastName(@Param("clientLastName") String clientLastName);


}
