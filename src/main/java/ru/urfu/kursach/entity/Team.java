package ru.urfu.kursach.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = true)
    private String logo;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Player> players;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "match_participator",
            joinColumns = { @JoinColumn(name = "team_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "match_id", referencedColumnName = "id") }
    )
    private List<Match> matches;
}
