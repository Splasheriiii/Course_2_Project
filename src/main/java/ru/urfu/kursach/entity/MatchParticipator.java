package ru.urfu.kursach.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "match_participator")
public class MatchParticipator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "team_id", nullable = false)
    private Long teamId;
    @Column(name = "match_id", nullable = false)
    private Long matchId;
    @Column(nullable = false)
    private Integer score;
}
