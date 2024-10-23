package ru.urfu.kursach.dto;

import lombok.Getter;
import ru.urfu.kursach.entity.MatchParticipator;
import ru.urfu.kursach.entity.Team;

@Getter
public class TeamDto {
    private final Long id;
    private final String name;
    private final int matches;
    private final int wins;
    private final int losses;
    private final String winrate;
    private final int score;

    public TeamDto(Team team) {
        id = team.getId();
        name = team.getName();
        var participation = team.getMatchParticipation();
        var matches = participation.stream().map(p -> {
            var list = p.getMatch().getMatchParticipation();
            if (list.size() > 1) {
                var min = list.stream().mapToInt(MatchParticipator::getScore).min().getAsInt();
                var max = list.stream().mapToInt(MatchParticipator::getScore).max().getAsInt();
                MatchScore res;
                if (min == max) {
                    res = MatchScore.DRAW;
                } else {
                    res = p.getScore() == max ? MatchScore.WIN : MatchScore.LOSE;
                }
                res.score = p.getScore();
                return res;
            } else {
                return null;
            }
        }).toList();
        this.matches = matches.size();
        this.wins = (int)matches.stream().filter(ms -> ms != null && ms.enumFlag == 1).count();
        this.losses = (int)matches.stream().filter(ms -> ms != null && ms.enumFlag == -1).count();
        this.winrate = Math.round((float) this.wins / this.matches * 100) + "%";
        this.score = matches.stream().mapToInt(ms -> ms == null ? 0 : ms.getScore()).sum();
    }

    @Getter
    private enum MatchScore {
        WIN(1),
        DRAW(0),
        LOSE(-1);
        private Integer score;
        private final int enumFlag;

        MatchScore(int enumFlag) {
            this.enumFlag = enumFlag;
        }
    }
}
