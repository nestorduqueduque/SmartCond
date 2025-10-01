package server.smartcond.Application.SImplementations;


import jakarta.persistence.EntityExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.smartcond.Domain.Dto.request.AdminRequestDTO;
import server.smartcond.Domain.Dto.response.AdminResponseDTO;
import server.smartcond.Domain.Dto.response.CeladorResponseDTO;
import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.Services.IAdminService;
import server.smartcond.Domain.Services.IDeveloperService;
import server.smartcond.Domain.Utils.RoleEnum;
import server.smartcond.Domain.dao.interfaces.IAdminDao;
import server.smartcond.Domain.dao.interfaces.IUserDao;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeveloperServiceImpl implements IDeveloperService {

    @Autowired
    private IAdminDao adminDao;
    @Autowired
    private IUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public AdminResponseDTO createAdmin(AdminRequestDTO adminRequestDTO) {
        try {
            var emailExists = userDao.findByEmail(adminRequestDTO.getEmail());
            if (emailExists.isPresent()) {
                throw new EntityExistsException("El email ya está en uso");
            }
            ModelMapper modelMapper = new ModelMapper();
            UserEntity userEntity = modelMapper.map(adminRequestDTO, UserEntity.class);
            String encodedPassword = passwordEncoder.encode(adminRequestDTO.getPassword());
            userEntity.setPassword(encodedPassword);
            userEntity.setEnabled(true);
            userEntity.setAccountNoExpired(true);
            userEntity.setAccountNoLocked(true);
            userEntity.setCredentialNoExpired(true);
            userEntity.setRole(RoleEnum.ADMIN);
            adminDao.saveUserAdmin(userEntity);
            AdminResponseDTO responseDTO = modelMapper.map(userEntity, AdminResponseDTO.class);
            return responseDTO;

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar Usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AdminResponseDTO> findAllAdmins() {
        ModelMapper modelMapper = new ModelMapper();
        return this.adminDao.findAllAdmins()
                .stream()
                .map(entity -> modelMapper.map(entity, AdminResponseDTO.class))
                .collect(Collectors.toList());
    }
}
