// Estructuras base
export interface ApartmentTower {
  id: number;
  number: number;
}

export interface Apartment {
  id: number;
  number: number;
  tower: ApartmentTower;
}

export interface ResidentResponseDTO {
  id: number;
  name: string;
  lastName: string;
  document: number;
  email: string;
  phoneNumber: number;
  apartment: number;
  status?: string;
}

// Interfaces de Contenido
export interface ResidentPackage {
  id: number;
  description: string;
  receivedAt: string;
  deliveredAt: string | null;
  status: 'RECEIVED' | 'DELIVERED' | string;
  apartment: number;
}

export interface ResidentVisitor {
  id: number;
  name: string;
  document: number;
  reason: string;
  entryTime: string;
  apartment: number;
}

export interface ResidentNotice {
  id: number;
  title: string;
  content: string;
  authorName: string;
  createdAt: string;
}

// Interfaz Principal del Dashboard
export interface ResidentDashboardDTO {
  residentName: string;
  apartment: Apartment;
  latestPackages: ResidentPackage[];
  latestVisitors: ResidentVisitor[];
  latestNotices: ResidentNotice[];
}
