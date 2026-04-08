export interface Machine {
  id?: string;
  serialNumber: string;
  model: string;
  manufacturer: string;
  type: string;
  vesselOrLocation: string;
  installationDate: string;
  operationalStatus?: string;
}
