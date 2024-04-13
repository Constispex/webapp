import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {BattleshipComponent} from './battleship/battleship.component'; // Importiere die Battleship-Komponente

@NgModule({
  declarations: [
    BattleshipComponent // Deklariere die Battleship-Komponente
  ],
  imports: [
    BrowserModule,
    AppComponent
  ],
  providers: [],
  exports: [
    BattleshipComponent
  ],
})
export class AppModule {
}
