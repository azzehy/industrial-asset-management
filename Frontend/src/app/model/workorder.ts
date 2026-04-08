export interface WorkOrder {
  id?: number;
  workOrderNumber?: string;
  serialNumber: string;
  type: string;
  scheduledDate: string;
  technicianDetails : TechnicianDetails ;
}

export interface TechnicianDetails  {
  email: string;
  firstName: string;
  lastName: string;
}