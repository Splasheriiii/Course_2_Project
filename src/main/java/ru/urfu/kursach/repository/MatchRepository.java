package ru.urfu.kursach.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.kursach.entity.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
