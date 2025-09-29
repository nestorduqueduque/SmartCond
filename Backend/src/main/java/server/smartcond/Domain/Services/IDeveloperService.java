package server.smartcond.Domain.Services;

import server.smartcond.Domain.Dto.request.AdminRequestDTO;
import server.smartcond.Domain.Dto.request.CeladorRequestDTO;
import server.smartcond.Domain.Dto.response.AdminResponseDTO;
import server.smartcond.Domain.Dto.response.CeladorResponseDTO;

import java.util.List;

public interface IDeveloperService {
    AdminResponseDTO createAdmin(AdminRequestDTO adminRequestDTO);

    List<AdminResponseDTO> findAllAdmins();

}
