import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { WorkOrder } from "../../model/workorder";

@Injectable({
  providedIn: 'root'
})
export class WorkOrderService {

  constructor(private httpClient: HttpClient) {
  }

  createWorkOrder(workOrder: WorkOrder): Observable<string> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'text' as 'json'
    };
    return this.httpClient.post<string>('http://localhost:9000/api/workorder', workOrder, httpOptions);
  }
}
