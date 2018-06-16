package org.vielheit.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.vielheit.core.domain.UserRole

interface UserRoleRepository : JpaRepository<UserRole, Int>