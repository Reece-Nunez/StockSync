import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081/api/users';

  constructor(private http: HttpClient, private router: Router) { }

  login(username: string, password: string): Observable<any> {
    const loginUrl = `${this.apiUrl}/auth/login`;
    return this.http.post(loginUrl, { username, password });
  }

  deleteUser(username: string, password: string): Observable<any> {
    const deleteUserUrl = `${this.apiUrl}/delete`;
    return this.http.delete(deleteUserUrl, {
      body: { username, password } });
  }

  onLogout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}
