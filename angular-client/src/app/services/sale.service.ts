import { HttpClient } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Sale } from '../model/sale';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SaleService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http:HttpClient) {
  }

  getAllSales() : Observable<any>{
    return this.http.get<Sale[]>(`${this.apiServerUrl}/api/sales`, { observe: 'response' });
  }
}
