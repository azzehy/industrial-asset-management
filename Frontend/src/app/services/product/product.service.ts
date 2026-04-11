import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Machine} from "../../model/machine";

@Injectable({
  providedIn: 'root'
})
export class MachineService {

  constructor(private httpClient: HttpClient) {
  }

  getMachines(): Observable<Array<Machine>> {
    return this.httpClient.get<Array<Machine>>('http://localhost:9000/api/machine');
  }

  createMachine(machine: Machine): Observable<Machine> {
    return this.httpClient.post<Machine>('http://localhost:9000/api/machine', machine);
  }
}
