package server.smartcond.Domain.Dto.response;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentDashboardResponseDTO {

    private List<PackageResponseDTO> lastPackages;
    private List<NoticeResponseDTO> lastNotices;
    private List<VehicleResponseDTO> vehicles;
    private List<VisitorResponseDTO> visitors;
}
