package org.voting.controller;

import org.springframework.web.bind.annotation.*;
import org.voting.model.Game;
import org.voting.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private GameService gameService;

    @GetMapping("/")
    public String getMainPage(Model model) {
        List<Game> games = gameService.getAll();
        model.addAttribute("games", games);
        return "index";
    }

    @PostMapping("/vote")
    public String postVote(@RequestParam("id") String id) {
        gameService.voteById(id);
        System.out.println("Received vote for game ID: " + id);
        return "ok";
    }

    @GetMapping("/add")
    public String getAdd() {
        return "add";
    }

    @PostMapping("/add")
    public String postAdd(Game game) {
        System.out.println("Adding new game: " + game);
        gameService.addGame(game);
        return "redirect:/";
    }

    // Временный метод для проверки всех ключей в Redis
    @GetMapping("/checkRedisKeys")
    @ResponseBody
    public String checkRedisKeys() {
        gameService.checkRedisKeys();
        return "Redis keys checked";
    }
}
