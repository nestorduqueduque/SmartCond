package server.smartcond.Domain.Services;

import server.smartcond.Domain.Dto.request.CeladorRequestDTO;
import server.smartcond.Domain.Dto.response.CeladorResponseDTO;

import java.util.List;

public interface IAdminService {

    List<CeladorResponseDTO> findAll();
    CeladorResponseDTO finById(Long id);
    CeladorResponseDTO createCelador(CeladorRequestDTO celadorRequestDTO);
    CeladorResponseDTO updateCelador(CeladorRequestDTO celadorRequestDTO, Long id);


}
