package ru.urfu.kursach.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.urfu.kursach.component.UserSpecificComponent;
import ru.urfu.kursach.dto.PlayerDto;
import ru.urfu.kursach.entity.Player;
import ru.urfu.kursach.repository.PlayerRepository;
import ru.urfu.kursach.repository.TeamRepository;

@Slf4j
@Controller
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserSpecificComponent userSpecificComponent;

    @GetMapping("/create/{teamId}")
    public ModelAndView edit(@PathVariable Long teamId) {
        var mav = new ModelAndView("player-form");
        mav.addObject("teams", userSpecificComponent.getAll(teamRepository));
        mav.addObject("player", new PlayerDto(teamId));
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editById(@PathVariable Long id) throws Exception {
        var mav = new ModelAndView("player-form");
        mav.addObject("teams", userSpecificComponent.getAll(teamRepository));
        mav.addObject("player", new PlayerDto(userSpecificComponent.check(playerRepository.findById(id))));
        return mav;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute PlayerDto player) throws Exception {
        var p = userSpecificComponent.save(playerRepository, Player.builder()
                .id(player.getId())
                .team(userSpecificComponent.check(teamRepository.findById(player.getTeamId())))
                .name(player.getName())
                .nickname(player.getNickname())
                .build());
        return "redirect:/team/" + p.getTeam().getId();
    }
}
