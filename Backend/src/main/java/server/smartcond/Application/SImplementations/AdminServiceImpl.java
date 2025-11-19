package server.smartcond.Application.SImplementations;


import jakarta.persistence.EntityExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import server.smartcond.Domain.Dto.request.CeladorRequestDTO;
import server.smartcond.Domain.Dto.request.NoticeRequestDTO;
import server.smartcond.Domain.Dto.request.ResidentRequestDTO;
import server.smartcond.Domain.Dto.response.AdminDashboardDTO;
import server.smartcond.Domain.Dto.response.CeladorResponseDTO;
import server.smartcond.Domain.Dto.response.NoticeResponseDTO;
import server.smartcond.Domain.Dto.response.ResidentResponseDTO;
import server.smartcond.Domain.Entities.ApartmentEntity;
import server.smartcond.Domain.Entities.NoticeEntity;
import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.Services.IAdminService;
import server.smartcond.Domain.Utils.RoleEnum;
import server.smartcond.Domain.dao.interfaces.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements IAdminService {


    @Autowired
    private ICeladorDao celadorDao;
    @Autowired
    private IResidentDao residentDao;
    @Autowired
    private IApartmentDao apartmentDao;
    @Autowired
    private INoticeDao noticeDao;
    @Autowired
    private IAdminDao adminDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;


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
                     var emailExists = userDao.findByEmail(celadorRequestDTO.getEmail());
            if (emailExists.isPresent()) {
                throw new EntityExistsException("El email ya está en uso");
            }

            var documentExists = userDao.findByDocument(celadorRequestDTO.getDocument());
            if (documentExists.isPresent()) {
                throw new EntityExistsException("El documento ya está en uso");
            }
            ModelMapper modelMapper = new ModelMapper();
            UserEntity userEntity = modelMapper.map(celadorRequestDTO, UserEntity.class);

            String encodedPassword = passwordEncoder.encode(celadorRequestDTO.getPassword());
            userEntity.setPassword(encodedPassword);

            userEntity.setEnabled(true);
            userEntity.setAccountNoExpired(true);
            userEntity.setAccountNoLocked(true);
            userEntity.setCredentialNoExpired(true);
            userEntity.setRole(RoleEnum.CELADOR);

            celadorDao.saveUserCelador(userEntity);

            return modelMapper.map(userEntity, CeladorResponseDTO.class);

        } catch (EntityExistsException e) {
             throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());

        } catch (Exception e) {
            System.err.println("Error interno: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar Usuario");
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
                       var emailExists = userDao.findByEmail(residentRequestDTO.getEmail());
            if (emailExists.isPresent()) {
                throw new EntityExistsException("El email ya está en uso");
            }

            var documentExists = userDao.findByDocument(residentRequestDTO.getDocument());
            if (documentExists.isPresent()) {
                throw new EntityExistsException("El documento ya está en uso");
            }
            ApartmentEntity apartmentEntity = apartmentDao.findByNumber(residentRequestDTO.getApartment())
                    .orElseThrow(() -> new RuntimeException("Apartamento No Encontrado " + residentRequestDTO.getApartment()));

            ModelMapper modelMapper = new ModelMapper();
            UserEntity userEntity = modelMapper.map(residentRequestDTO, UserEntity.class);

            String encodedPassword = passwordEncoder.encode(residentRequestDTO.getPassword());
            userEntity.setPassword(encodedPassword);

            userEntity.setApartment(apartmentEntity);
            userEntity.setEnabled(true);
            userEntity.setAccountNoExpired(true);
            userEntity.setAccountNoLocked(true);
            userEntity.setCredentialNoExpired(true);
            userEntity.setRole(RoleEnum.RESIDENT);

            residentDao.saveUserResident(userEntity);

            ResidentResponseDTO responseDTO = toResidentResponse(userEntity);
            return responseDTO;

        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());

        } catch (RuntimeException e) {
            if (e.getMessage().contains("Apartamento No Encontrado")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado: " + e.getMessage());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar Usuario");
        }
    }

    @Override
    public List<ResidentResponseDTO> findAllResidents() {
        return this.residentDao.findAllResidents()
                .stream()
                .map(this::toResidentResponse)
                .collect(Collectors.toList());
    }

    //Notices
        @Override
        public NoticeResponseDTO createNotice(Long authorID,NoticeRequestDTO dto) {
            UserEntity author =  adminDao.findAdminById(authorID)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            NoticeEntity entity = new NoticeEntity();
            entity.setTitle(dto.getTitle());
            entity.setContent(dto.getContent());
            entity.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Bogota")));
            entity.setAuthor(author);
            noticeDao.save(entity);
            return toNoticeResponse(entity);
        }

    @Override
    public NoticeResponseDTO createNoticeByUsername(String username, NoticeRequestDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity author =  userDao.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        author.setName(author.getName());
        NoticeEntity entity = new NoticeEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Bogota")));
        entity.setAuthor(author);
        noticeDao.save(entity);
        return toNoticeResponse(entity);
    }

        @Override
        public List<NoticeResponseDTO> getAllNotice() {
            return this.noticeDao.findAll()
                    .stream()
                    .map(this::toNoticeResponse)
                    .collect(Collectors.toList());
        }

        @Override
        public NoticeResponseDTO findNoticeById(Long id) {
            return null;
        }




    //Dashboard
    @Override
    public AdminDashboardDTO getAdminDashboard(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity author =  adminDao.findAdminById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<NoticeEntity> lastNotices = noticeDao.findLatestNotices();
        List<NoticeResponseDTO> noticeResponseDTOS = lastNotices.stream()
                .map(notice -> modelMapper.map(notice, NoticeResponseDTO.class)).collect(Collectors.toList());

        AdminDashboardDTO dashboardDTO = new AdminDashboardDTO();
        dashboardDTO.setAdminName(author.getName());
        dashboardDTO.setLatestNotices(noticeResponseDTOS);
        return dashboardDTO;
    }

    @Override
    public AdminDashboardDTO getAdminDashboardUsername(String username) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity author = userDao.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<NoticeEntity> lastNotices = noticeDao.findLatestNotices();
        List<NoticeResponseDTO> noticeResponseDTOS = lastNotices.stream()
                .map(notice -> modelMapper.map(notice, NoticeResponseDTO.class)).collect(Collectors.toList());
        AdminDashboardDTO dashboardDTO = new AdminDashboardDTO();
        dashboardDTO.setAdminName(author.getName());
        dashboardDTO.setLatestNotices(noticeResponseDTOS);
        return dashboardDTO;
    }

    private ResidentResponseDTO toResidentResponse(UserEntity userEntity) {
        ResidentResponseDTO dto = new ResidentResponseDTO();
        dto.setId(userEntity.getId());
        dto.setName(userEntity.getName());
        dto.setLastName(userEntity.getLastName());
        dto.setDocument(userEntity.getDocument());
        dto.setEmail(userEntity.getEmail());
        dto.setPhoneNumber(userEntity.getPhoneNumber());
        dto.setRole(userEntity.getRole().name());
        dto.setEnabled(userEntity.isEnabled());
        dto.setApartment(userEntity.getApartment().getNumber());
        return dto;
    }

    private NoticeResponseDTO toNoticeResponse(NoticeEntity entity) {
        return new NoticeResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(), entity.getAuthor().getName() + " " + entity.getAuthor().getLastName(),
                entity.getCreatedAt()
        );
    }




}
