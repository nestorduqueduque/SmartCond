export interface Notice {
  id: number;
  title: string;
  content: string;
  authorName: string;
  createdAt: string;
}

export interface CeladorDashboardDTO {
  celadorName: string;
  latestNotices: Notice[];
}

export interface CeladorResponseDTO {
  id: number;
  name: string;
  lastName: string;
  document: number;
  email: string;
  phoneNumber: number;
  direction: string;
  status?: string;
}

export interface PackageRequestDTO {
  description: string;
  apartment: number;
}

export interface PackageResponseDTO {
  id: number;
  description: string;
  receivedAt: string;
  deliveredAt?: string;
  status: 'RECEIVED' | 'DELIVERED';
  apartment: number;
}

export interface VisitorRequestDTO {
  name: string;
  document: number;
  reason: string;
  apartment: number;
}

export interface VisitorResponseDTO {
  id: number;
  name: string;
  document: number;
  reason: string;
  entryDate: string;
  apartment: number;
}

export interface VehicleResponseDTO {
  id: number;
  plate: string;
  type: string;
  brand: string;
  model: string;
  apartment: number;
  registeredAt: string,
}





