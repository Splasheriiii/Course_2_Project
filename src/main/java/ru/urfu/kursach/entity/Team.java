package ru.urfu.kursach.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
public class Team extends UserSpecific {
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = true, length = 1_000_000)
    private String logo;

    @OneToMany(mappedBy = "team")
    private List<Player> players;
    @OneToMany(mappedBy = "team")
    private List<MatchParticipator> matchParticipation;

}
