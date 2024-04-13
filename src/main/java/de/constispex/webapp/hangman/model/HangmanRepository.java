package de.constispex.webapp.hangman.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HangmanRepository extends JpaRepository<Hangman, Long> {

}
