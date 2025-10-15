package server.smartcond.Domain.Dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardDTO {
    private String adminName;
    private List<NoticeResponseDTO> latestNotices;
}
