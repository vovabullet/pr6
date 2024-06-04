package org.results.controller;

import org.results.service.GameResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ResultsRestController {
    @Autowired
    private GameResultsService gameResultsService;

    @GetMapping("/results")
    public ResponseEntity<?> getResults() {
        return ResponseEntity.ok(gameResultsService.getTopGames());
    }
}
