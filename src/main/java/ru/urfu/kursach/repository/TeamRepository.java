package ru.urfu.kursach.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.kursach.entity.Team;

public interface TeamRepository extends UserJpaRepository<Team, Long> {
}
