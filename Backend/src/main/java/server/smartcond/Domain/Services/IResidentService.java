package server.smartcond.Domain.Services;

import server.smartcond.Domain.Dto.response.ResidentDashboardDTO;

public interface IResidentService {

    ResidentDashboardDTO getResidentDashboard(Long residentId);
}
