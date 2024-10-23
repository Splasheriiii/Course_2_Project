package ru.urfu.kursach.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.urfu.kursach.component.UserSpecificComponent;
import ru.urfu.kursach.dto.TeamDto;
import ru.urfu.kursach.entity.Team;
import ru.urfu.kursach.repository.TeamRepository;

@Slf4j
@Controller
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserSpecificComponent userSpecificComponent;

    @GetMapping("/all")
    public ModelAndView all() {
        var mav = new ModelAndView("teams");
        mav.addObject("teams", userSpecificComponent.getAll(teamRepository).stream().map(TeamDto::new).toList());
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView one(@PathVariable Long id) throws Exception {
        var mav = new ModelAndView("team");
        mav.addObject("team", userSpecificComponent.check(teamRepository.findById(id)));
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView edit() {
        var mav = new ModelAndView("team-form");
        mav.addObject("team", new Team());
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editById(@PathVariable Long id) throws Exception {
        var mav = new ModelAndView("team-form");
        mav.addObject("team", userSpecificComponent.check(teamRepository.findById(id)));
        return mav;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Team team) {
        var t = userSpecificComponent.save(teamRepository, team);
        return "redirect:/team/" + t.getId();
    }

}
