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
