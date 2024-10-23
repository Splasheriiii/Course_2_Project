package ru.urfu.kursach.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.urfu.kursach.entity.MatchParticipator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipatorDto {
    public Long id;
    public Long matchId;
    public Long teamId;
    public int score;

    public ParticipatorDto(Long matchId) {
        this.id = null;
        this.matchId = matchId;
    }

    public ParticipatorDto(MatchParticipator participator) {
        this.id = participator.getId();
        this.matchId = participator.getMatch().getId();
        this.teamId = participator.getTeam().getId();
        this.score = participator.getScore();
    }
}
