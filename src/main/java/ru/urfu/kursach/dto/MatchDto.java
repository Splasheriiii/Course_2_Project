package ru.urfu.kursach.dto;

import lombok.Getter;
import lombok.Setter;
import ru.urfu.kursach.entity.Match;
import ru.urfu.kursach.entity.MatchParticipator;
import ru.urfu.kursach.entity.Team;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MatchDto {
    private final Match match;
    private final MatchParticipator left;
    private final MatchParticipator right;
    public MatchDto(Match match) {
        this.match = match;
        var matchParticipators = match.getMatchParticipation();
        left = !matchParticipators.isEmpty() ? matchParticipators.get(0) : new MatchParticipator();
        right = matchParticipators.size() > 1 ? matchParticipators.get(1) : new MatchParticipator();
    }
    public List<MatchParticipator> getParticipators() {
        return List.of(left, right);
    }
    public boolean isLeftUnvalidated() {
        return left.getTeam() == null;
    }
    public boolean isRightUnvalidated() {
        return right.getTeam() == null;
    }
    public String getLeftName() {
        return GetTeamName(left);
    }
    public String getRightName() {
        return GetTeamName(right);
    }
    private static String GetTeamName(MatchParticipator participator) {
        return participator.getTeam() == null ? "Не выбрано" : participator.getTeam().getName();
    }
}
