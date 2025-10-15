package server.smartcond.Domain.Dto.response;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.smartcond.Domain.Entities.ApartmentEntity;
import server.smartcond.Domain.Entities.NoticeEntity;
import server.smartcond.Domain.Entities.PackageEntity;
import server.smartcond.Domain.Entities.VisitorEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentDashboardDTO {

    private String residentName;
    private ApartmentInfoResponseDTO apartment;
    private List<PackageResponseDTO> latestPackages;
    private List<VisitorResponseDTO> latestVisitors;
    private List<NoticeResponseDTO> latestNotices;
}
