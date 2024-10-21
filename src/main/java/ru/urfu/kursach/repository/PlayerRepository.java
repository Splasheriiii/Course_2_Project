package ru.urfu.kursach.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.kursach.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
