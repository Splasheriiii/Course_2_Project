package ru.urfu.kursach.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.kursach.entity.Player;
import ru.urfu.kursach.entity.Role;

public interface PlayerRepository extends UserJpaRepository<Player, Long> {
}
