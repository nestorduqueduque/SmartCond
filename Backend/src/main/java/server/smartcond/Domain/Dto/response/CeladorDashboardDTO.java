package server.smartcond.Domain.Dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CeladorDashboardDTO {
    private String celadorName;
    private List<NoticeResponseDTO> latestNotices;
}
