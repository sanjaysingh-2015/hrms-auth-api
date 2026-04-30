package com.hewhorizon.hrms.auth.repositories;

import com.hewhorizon.hrms.auth.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
