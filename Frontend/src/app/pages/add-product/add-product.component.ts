import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { Machine } from "../../model/machine";
import { MachineService } from "../../services/product/product.service";
import { NgIf } from "@angular/common";

@Component({
  selector: 'app-add-product',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.css'
})
export class RegisterMachineComponent {
  registerMachineForm: FormGroup;
  private readonly machineService = inject(MachineService);
  machineCreated = false;

  constructor(private fb: FormBuilder) {
    this.registerMachineForm = this.fb.group({
      serialNumber: ['', Validators.required],
      model: ['', Validators.required],
      manufacturer: ['', Validators.required],
      type: ['', Validators.required],
      vesselOrLocation: ['', Validators.required],
      installationDate: ['', Validators.required]
    })
  }

  onSubmit(): void {
    if (this.registerMachineForm.valid) {
      // const machine: Machine = this.registerMachineForm.value;
      const machine: Machine = {
        serialNumber: this.registerMachineForm.get('serialNumber')?.value,
        model: this.registerMachineForm.get('model')?.value,
        manufacturer: this.registerMachineForm.get('manufaturer')?.value,
        type: this.registerMachineForm.get('type')?.value,
        vesselOrLocation: this.registerMachineForm.get('vesselOrLocation')?.value,
        installationDate: this.registerMachineForm.get('installationDate')?.value
      }
      this.machineService.createMachine(machine).subscribe(machine => {
        this.machineCreated = true;
        this.registerMachineForm.reset();
      })
    } else {
      console.log('Form is not valid');
    }
  }

  // get skuCode() {
  //   return this.registerMachineForm.get('skuCode');
  // }

  // get name() {
  //   return this.registerMachineForm.get('name');
  // }

  // get description() {
  //   return this.registerMachineForm.get('description');
  // }

  // get price() {
  //   return this.registerMachineForm.get('price');
  // }
  get serialNumber() { return this.registerMachineForm.get('serialNumber'); }
  get model() { return this.registerMachineForm.get('model'); }
  get manufacturer() { return this.registerMachineForm.get('manufacturer'); }
  get type() { return this.registerMachineForm.get('type'); }
  get vesselOrLocation() { return this.registerMachineForm.get('vesselOrLocation'); }
  get installationDate() { return this.registerMachineForm.get('installationDate'); }
}
