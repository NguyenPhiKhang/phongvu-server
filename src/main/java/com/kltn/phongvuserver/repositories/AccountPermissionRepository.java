package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.AccountPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountPermissionRepository extends JpaRepository<AccountPermission, Integer> {
}
