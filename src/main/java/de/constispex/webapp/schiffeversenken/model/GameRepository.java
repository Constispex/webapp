package de.constispex.webapp.schiffeversenken.model;

import de.constispex.webapp.schiffeversenken.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}