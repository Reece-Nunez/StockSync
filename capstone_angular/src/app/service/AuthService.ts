import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081/api/users';

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    const loginUrl = `${this.apiUrl}/auth/login`;
    return this.http.post(loginUrl, { username, password });
  }

  deleteUser(username: string, password: string): Observable<any> {
    const deleteUserUrl = `${this.apiUrl}/delete`;
    return this.http.delete(deleteUserUrl, { body: { username, password } });
  }
}
