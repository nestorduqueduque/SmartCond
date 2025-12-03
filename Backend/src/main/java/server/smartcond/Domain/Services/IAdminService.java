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

    boolean checkDocumentExists(String document);
    boolean checkEmailExists(String email);


    //Celador Methods
    List<CeladorResponseDTO> findAllCeladors();
    CeladorResponseDTO finCeladorById(Long id);
    CeladorResponseDTO createCelador(CeladorRequestDTO celadorRequestDTO);
    CeladorResponseDTO updateCelador(CeladorRequestDTO celadorRequestDTO, Long id);

    void deleteCelador(Long id);

    //Residents Methods
    ResidentResponseDTO createResident(ResidentRequestDTO residentRequestDTO);
    List<ResidentResponseDTO> findAllResidents();

    ResidentResponseDTO updateResident(ResidentRequestDTO residentRequestDTO, Long id);
    void deleteResident(Long id);
    ResidentResponseDTO findResidentById(Long id);

    //Notices
    NoticeResponseDTO createNotice(Long authorId, NoticeRequestDTO dto);
    List<NoticeResponseDTO> getAllNotice();
    NoticeResponseDTO findNoticeById(Long id);
    NoticeResponseDTO createNoticeByUsername(String username, NoticeRequestDTO dto);
    void  deleteNotice(Long id);
    NoticeResponseDTO updateNotice(Long id, NoticeRequestDTO dto);


    //Dashboard
    AdminDashboardDTO getAdminDashboard(Long id);

    AdminDashboardDTO getAdminDashboardUsername(String username);


}
