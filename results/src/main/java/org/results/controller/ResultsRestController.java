package org.results.controller;

import org.results.dto.GameResultDto;
import org.results.service.GameResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class ResultsRestController {
    @Autowired
    private GameResultsService gameResultsService;

    @GetMapping("/results")
    public ResponseEntity<?> getResults() {
        List<GameResultDto> results = gameResultsService.getTopGames();
        System.out.println("Controller Results: " + results);
        return ResponseEntity.ok(results);
    }
}
