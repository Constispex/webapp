import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BattleshipService {
  private baseUrl: string = 'http://localhost:8080/api/battleship';

  constructor(private http: HttpClient) {
  }

  startGame(): Observable<any> {
    return this.http.get(`${this.baseUrl}/start`);
  }

  fireMissile(x: number, y: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/fire`, {x, y});
  }
}
