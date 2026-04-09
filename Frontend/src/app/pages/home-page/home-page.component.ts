import { Component, inject, OnInit } from '@angular/core';
import { OidcSecurityService } from "angular-auth-oidc-client";
import { Machine } from "../../model/machine";
import { MachineService } from "../../services/product/product.service";
import { AsyncPipe, JsonPipe } from "@angular/common";
import { Router } from "@angular/router";
import { WorkOrder } from "../../model/workorder";
import { FormsModule } from "@angular/forms";
import { WorkOrderService } from "../../services/order/order.service";

@Component({
  selector: 'app-homepage',
  templateUrl: './home-page.component.html',
  standalone: true,
  imports: [
    AsyncPipe,
    JsonPipe,
    FormsModule
  ],
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit {
  private readonly oidcSecurityService = inject(OidcSecurityService);
  private readonly machineService = inject(MachineService);
  private readonly workOrderService = inject(WorkOrderService);
  private readonly router = inject(Router);
  isAuthenticated = false;
  machines: Array<Machine> = [];
  workOrderSuccess = false;
  workOrderFailed = false;

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({ isAuthenticated }) => {
        this.isAuthenticated = isAuthenticated;
        this.machineService.getMachines()
          .pipe()
          .subscribe(machine => {
            this.machines = machine;
          })
      }
    )
  }

  goToRegisterMachinePage() {
    this.router.navigateByUrl('/add-product');
  }

  scheduleWorkOrder(machine: Machine) {

    this.oidcSecurityService.userData$.subscribe(result => {
      const technicianDetails = {
        email: result.userData.email,
        firstName: result.userData.firstName,
        lastName: result.userData.lastName
      };


      const workOrder: WorkOrder = {
        serialNumber: machine.serialNumber,
        type: "INSPECTION",
        scheduledDate: new Date().toISOString(),
        technicianDetails: technicianDetails
      }

      this.workOrderService.createWorkOrder(workOrder).subscribe(() => {
        this.workOrderSuccess = true;
        this.workOrderFailed = false;
      }, error => {
        this.workOrderFailed = true;
      })

    })
  }
}
