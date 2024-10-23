package ru.urfu.kursach.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.urfu.kursach.component.UserSpecificComponent;
import ru.urfu.kursach.dto.MatchDto;
import ru.urfu.kursach.dto.ParticipatorDto;
import ru.urfu.kursach.entity.Match;
import ru.urfu.kursach.entity.MatchParticipator;
import ru.urfu.kursach.entity.UserSpecific;
import ru.urfu.kursach.repository.MatchParticipatorRepository;
import ru.urfu.kursach.repository.MatchRepository;
import ru.urfu.kursach.repository.TeamRepository;

@Slf4j
@Controller
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchParticipatorRepository matchParticipatorRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserSpecificComponent userSpecificComponent;

    @GetMapping("/all")
    public ModelAndView matches() {
        var mav = new ModelAndView("matches");
        mav.addObject("matches", userSpecificComponent.getAll(matchRepository).stream().map(MatchDto::new).toList());
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView match(@PathVariable Long id) throws Exception {
        var mav = new ModelAndView("match");
        var match = userSpecificComponent.check(matchRepository.findById(id));
        mav.addObject("match", new MatchDto(match));
        return mav;
    }

    @GetMapping("/create")
    public String create() {
        userSpecificComponent.save(matchRepository, new Match());
        return "redirect:/match/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        matchRepository.deleteById(id);
        return "redirect:/match/all";
    }

    @GetMapping("/part/create/{matchId}")
    public ModelAndView createParticipator(@PathVariable Long matchId) throws Exception {
        var mav = new ModelAndView("participator-form");
        mav.addObject("match", userSpecificComponent.check(matchRepository.findById(matchId)));
        mav.addObject("teams", userSpecificComponent.getAll(teamRepository));
        mav.addObject("participator", new ParticipatorDto(matchId));
        return mav;
    }

    @GetMapping("/part/edit/{matchId}/{partId}")
    public ModelAndView editParticipator(@PathVariable Long matchId, @PathVariable Long partId) throws Exception {
        var mav = new ModelAndView("participator-form");
        mav.addObject("match", userSpecificComponent.check(matchRepository.findById(matchId)));
        mav.addObject("teams", userSpecificComponent.getAll(teamRepository));
        mav.addObject("participator", new ParticipatorDto(matchParticipatorRepository.findById(partId).get()));
        return mav;
    }

    @PostMapping("/part/save")
    public String saveParticipator(@ModelAttribute ParticipatorDto dto) throws Exception {
        var participator = MatchParticipator.builder()
                .id(dto.getId())
                .match(userSpecificComponent.check(matchRepository.findById(dto.getMatchId())))
                .team(userSpecificComponent.check(teamRepository.findById(dto.getTeamId())))
                .score(dto.getScore())
                .build();
        matchParticipatorRepository.save(participator);
        return "redirect:/match/" + participator.getMatch().getId();
    }

}
