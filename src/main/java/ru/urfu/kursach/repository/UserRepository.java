package ru.urfu.kursach.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.kursach.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
