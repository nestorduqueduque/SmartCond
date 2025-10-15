package server.smartcond.Domain.Services;

import server.smartcond.Domain.Dto.request.CeladorRequestDTO;
import server.smartcond.Domain.Dto.request.NoticeRequestDTO;
import server.smartcond.Domain.Dto.request.ResidentRequestDTO;
import server.smartcond.Domain.Dto.response.AdminDashboardDTO;
import server.smartcond.Domain.Dto.response.CeladorResponseDTO;
import server.smartcond.Domain.Dto.response.NoticeResponseDTO;
import server.smartcond.Domain.Dto.response.ResidentResponseDTO;

import java.util.List;

public interface IAdminService {


    //Celador Methods
    List<CeladorResponseDTO> findAllCeladors();
    CeladorResponseDTO finById(Long id);
    CeladorResponseDTO createCelador(CeladorRequestDTO celadorRequestDTO);
    CeladorResponseDTO updateCelador(CeladorRequestDTO celadorRequestDTO, Long id);

    //Resident Method
    ResidentResponseDTO createResident(ResidentRequestDTO residentRequestDTO);
    List<ResidentResponseDTO> findAllResidents();

    //Notices
    NoticeResponseDTO createNotice(Long authorId, NoticeRequestDTO dto);
    List<NoticeResponseDTO> getAllNotice();
    NoticeResponseDTO findNoticeById(Long id);

    //Dashboard
    AdminDashboardDTO getAdminDashboard(Long id);


}
