package com.example.RookieShop.repository;

import com.example.RookieShop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    @Query("SELECT c FROM Role c WHERE c.role_name=?1")
    Role getByRoleName(String roleName);
}
