import {Component} from '@angular/core';
import {BattleshipService} from "./battleship.service";

@Component({
  selector: 'app-battleship', // Selector der Battleship-Komponente
  templateUrl: './battleship.component.html',
  styleUrls: ['./battleship.component.css']
})
export class BattleshipComponent {
  board: any[][] = [];
  gameOver: boolean = false;
  gameWon: boolean = false;

  constructor(private battleshipService: BattleshipService) {
  }

  ngOnInit() {
    this.initBoard();
    this.startGame();
  }

  initBoard() {
    // Initialize the game board
  }

  startGame() {
    this.battleshipService.startGame().subscribe((data: any) => {
      // Initialize game state
    });
  }

  fireMissile(x: number, y: number) {
    if (!this.gameOver && !this.board[x][y].fired) {
      this.battleshipService.fireMissile(x, y).subscribe((data: any) => {
        // Handle response from backend
      });
    }
  }

  checkGameStatus() {
    // Implement logic to check if the game is won or lost
  }
}
