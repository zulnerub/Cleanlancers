package com.simo.web.user.repository;

import com.simo.web.user.model.UserEntity;
import com.simo.web.user.model.UserServiceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findOneByEmail(String email);

    @Query(value = "SELECT u FROM UserEntity u " +
            "WHERE u.email like CONCAT('%', :email, '%') " +
            "AND u.firstName like CONCAT('%', :firstName, '%') " +
            "AND u.lastName like CONCAT('%', :lastName, '%') " +
            "AND u.region.name like CONCAT('%', :workArea, '%') " +
            "ORDER BY u.cleanerTasks.size ASC ")
    Optional<List<UserEntity>> getCleanersBySearchParams(@Param("email") String email,
                                                         @Param("firstName") String firstName,
                                                         @Param("lastName") String lastName,
                                                         @Param("workArea") String workArea);

    @Query(value = "SELECT u FROM UserEntity  u " +
            "WHERE u.email like CONCAT('%', :userEmail, '%') ")
    Optional<List<UserEntity>> filterUsersByEmail(@Param("userEmail") String userEmail);

    @Query(value = "SELECT u.firstName FROM UserEntity  u " +
            "WHERE u.firstName like CONCAT('%', :firstName, '%') ")
    Optional<List<String>> filterUsersByFirstName(@Param("firstName") String firstName);

    @Query(value = "SELECT u.lastName FROM UserEntity  u " +
            "WHERE u.lastName like CONCAT('%', :lastName, '%') ")
    Optional<List<String>> filterUsersByLastname(@Param("lastName") String lastName);

    @Query(value = "SELECT u.region.name FROM UserEntity  u " +
            "WHERE u.region.name like CONCAT('%', :regionName, '%') ")
    Optional<List<String>> filterUsersByRegionName(@Param("regionName") String regionName);


}
