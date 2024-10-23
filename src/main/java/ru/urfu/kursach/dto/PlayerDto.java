package ru.urfu.kursach.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.urfu.kursach.entity.Player;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    public Long id;
    public Long teamId;
    public String name;
    public String nickname;

    public PlayerDto(Long teamId) {
        this.id = null;
        this.teamId = teamId;
    }
    public PlayerDto(Player player) {
        this.id = player.getId();
        this.teamId = player.getTeam().getId();
        this.name = player.getName();
        this.nickname = player.getNickname();
    }
}
