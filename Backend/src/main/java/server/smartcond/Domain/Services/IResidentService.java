package server.smartcond.Domain.Services;

import server.smartcond.Domain.Dto.response.ResidentDashboardResponseDTO;

public interface IResidentService {

    ResidentDashboardResponseDTO getResidentDashboard(Integer number);
}
