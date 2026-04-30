package com.hewhorizon.hrms.auth.repositories;

import com.hewhorizon.hrms.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAuth0UserId(String auth0UserId);

    @Query("SELECT r.name FROM User u JOIN u.roles r WHERE u.auth0UserId = :auth0Id")
    List<String> findRolesByAuth0Id(String auth0Id);

    @Query("SELECT u.tenantId FROM User u WHERE u.auth0UserId = :auth0Id")
    Long findTenantIdByAuth0Id(String auth0Id);
}
