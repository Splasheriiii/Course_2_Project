package ru.urfu.kursach.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.kursach.entity.MatchParticipator;

public interface MatchParticipatorRepository extends JpaRepository<MatchParticipator, Long> {
}
