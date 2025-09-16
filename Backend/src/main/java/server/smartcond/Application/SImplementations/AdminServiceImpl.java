package server.smartcond.Application.SImplementations;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.smartcond.Domain.Dto.request.CeladorRequestDTO;
import server.smartcond.Domain.Dto.request.ResidentRequestDTO;
import server.smartcond.Domain.Dto.response.CeladorResponseDTO;
import server.smartcond.Domain.Dto.response.ResidentResponseDTO;
import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.Services.IAdminService;
import server.smartcond.Domain.Utils.RoleEnum;
import server.smartcond.Domain.dao.interfaces.ICeladorDao;
import server.smartcond.Domain.dao.interfaces.IResidentDao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements IAdminService {


    @Autowired
    private ICeladorDao celadorDao;
    @Autowired
    private IResidentDao residentDao;

    //celador
    @Override
    public List<CeladorResponseDTO> findAllCeladors() {
            ModelMapper modelMapper = new ModelMapper();
            return this.celadorDao.findAllCeladors()
                    .stream()
                    .map(entity -> modelMapper.map(entity, CeladorResponseDTO.class))
                    .collect(Collectors.toList());
    }

    @Override
    public CeladorResponseDTO finById(Long id) {
        this.celadorDao.findById(id);
        Optional<UserEntity> userEntity = this.celadorDao.findById(id);

        if(userEntity.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            UserEntity currentUser = userEntity.get();
            return  modelMapper.map(currentUser, CeladorResponseDTO.class);
        } else {
            return new CeladorResponseDTO();
        }
    }

    @Override
    public CeladorResponseDTO createCelador(CeladorRequestDTO celadorRequestDTO) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            UserEntity userEntity = modelMapper.map(celadorRequestDTO, UserEntity.class);
            userEntity.setEnabled(true);
            userEntity.setAccountNoExpired(true);
            userEntity.setAccountNoLocked(true);
            userEntity.setCredentialNoExpired(true);
            userEntity.setRole(RoleEnum.CELADOR);
            celadorDao.saveUserCelador(userEntity);
            CeladorResponseDTO responseDTO = modelMapper.map(userEntity, CeladorResponseDTO.class);
            return responseDTO;

        } catch (Exception e) {
            throw new UnsupportedOperationException("Error al guardar Usuario");
        }
    }
    @Override
    public CeladorResponseDTO updateCelador(CeladorRequestDTO celadorRequestDTO, Long id) {
        return null;
    }


    //Resident
    @Override
    public ResidentResponseDTO createResident(ResidentRequestDTO residentRequestDTO) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            UserEntity userEntity = modelMapper.map(residentRequestDTO, UserEntity.class);
            userEntity.setEnabled(true);
            userEntity.setAccountNoExpired(true);
            userEntity.setAccountNoLocked(true);
            userEntity.setCredentialNoExpired(true);
            userEntity.setRole(RoleEnum.RESIDENT);
            residentDao.saveUserResident(userEntity);
            ResidentResponseDTO responseDTO = modelMapper.map(userEntity, ResidentResponseDTO.class);
            return responseDTO;

        } catch (Exception e) {
            throw new UnsupportedOperationException("Error al guardar Usuario");
        }
    }

    @Override
    public List<ResidentResponseDTO> findAllResidents() {
        ModelMapper modelMapper = new ModelMapper();
        return this.residentDao.findAllResidents()
                .stream()
                .map(entity -> modelMapper.map(entity, ResidentResponseDTO.class))
                .collect(Collectors.toList());
    }


}
