package ru.urfu.kursach.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.kursach.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
