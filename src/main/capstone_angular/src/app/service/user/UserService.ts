import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Location, LocationStrategy} from "@angular/common";

@Injectable({
  providedIn: 'root'
})

export class UserService {
  //private userUrl: string = 'http://localhost:8080/api/users';
  private userUrl;

  constructor(private http: HttpClient, private location: Location) {
    this.userUrl = this.location.prepareExternalUrl('/api/users');
  }

  createUser(userData: any): Observable<any> {
    return this.http.post(`${this.userUrl}/register`, userData);
  }

  // New method to fetch all users
  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.userUrl}`);
  }

  // New method to update a user's role
  updateUserRole(userId: number, role: string): Observable<any> {
    return this.http.put(`${this.userUrl}/${userId}/role`, { role });
  }
  
}
