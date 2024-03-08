package de.constispex.webapp.hangman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HangmanViewController {
    @GetMapping("/hangman")
    public String hangman(Model model) {
        model.addAttribute("title", "hangman");
        model.addAttribute("startGameText", "Start Game");
        model.addAttribute("guessLetterText", "Guess Letter");

        return "hangman";
    }

}
