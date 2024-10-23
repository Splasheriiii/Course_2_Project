package ru.urfu.kursach.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import ru.urfu.kursach.entity.Role;

import java.util.List;

@NoRepositoryBean
public interface UserJpaRepository<T, Y> extends JpaRepository<T, Y> {
    List<T> findByUserId(Long id);
}
