import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Location, LocationStrategy} from "@angular/common";

@Injectable({
  providedIn: 'root'
})

export class UserService {
  private userUrl: string;

  constructor(private http: HttpClient, private location: Location) {
    this.userUrl = this.location.prepareExternalUrl('/api/users');
  }

  createUser(userData: any): Observable<any> {
    return this.http.post(`${this.userUrl}/register`, userData);
  }
}
