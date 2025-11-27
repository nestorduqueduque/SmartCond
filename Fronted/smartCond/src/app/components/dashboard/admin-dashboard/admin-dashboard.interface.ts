export interface Notice {
  id: number;
  title: string;
  content: string;
  authorName: string;
  createdAt: string;
}

export interface AdminDashboardDTO {
  adminName: string;
  latestNotices: Notice[];
}

export interface NoticeRequestDTO {
  title: string;
  content: string;
}

export interface CeladorRequestDTO {
  name: string;
  lastName: string;
  document: number;
  email: string;
  password: string;
  phoneNumber: number;
  direction: string;
}

export interface ResidentRequestDTO{
  name: string;
  lastName: string;
  document: number;
  email: string;
  password: string;
  phoneNumber: number;
  apartment: number
}

export interface VehicleRequestDTO {
  plate: string;
  type: 'CAR' | 'MOTORCYCLE' ,
  brand: string;
  model: string;
  apartment: number;
}

export interface VehicleTypeOption {
  label: string;
  value: VehicleRequestDTO['type'];
}

