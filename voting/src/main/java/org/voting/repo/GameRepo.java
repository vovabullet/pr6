package org.voting.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.voting.model.Game;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepo extends JpaRepository<Game, String> {
}
