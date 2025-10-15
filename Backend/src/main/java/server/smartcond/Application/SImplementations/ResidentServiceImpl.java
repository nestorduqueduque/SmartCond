package server.smartcond.Application.SImplementations;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.smartcond.Domain.Dto.response.*;
import server.smartcond.Domain.Entities.*;
import server.smartcond.Domain.Services.IResidentService;
import server.smartcond.Domain.dao.interfaces.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResidentServiceImpl implements IResidentService {

    @Autowired
    private IPackageDao packageDao;
    @Autowired
    private INoticeDao noticeDao;
    @Autowired
    private IApartmentDao apartmentDao;
    @Autowired
    private IVehicleDao vehicleDao;
    @Autowired
    private IVisitorDao visitorDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IResidentDao residentDao;







    //Methods
    private VehicleResponseDTO toVehicleResponse(VehicleEntity vehicleEntity) {
        VehicleResponseDTO dto = new VehicleResponseDTO();
        dto.setId(vehicleEntity.getId());
        dto.setPlate(vehicleEntity.getPlate());
        dto.setType(vehicleEntity.getType());
        dto.setBrand(vehicleEntity.getBrand());
        dto.setModel(vehicleEntity.getModel());
        dto.setApartment(vehicleEntity.getApartment().getNumber());
        return dto;
    }

    private VisitorResponseDTO toVisitorResponse(VisitorEntity entity) {
        VisitorResponseDTO dto = new VisitorResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDocument(entity.getDocument());
        dto.setReason(entity.getReason());
        dto.setEntryTime(entity.getEntryTime());
        dto.setApartment(entity.getApartment().getNumber());
        return dto;
    }

    private PackageResponseDTO toPackageResponse(PackageEntity entity) {
        PackageResponseDTO dto = new PackageResponseDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setReceivedAt(entity.getReceivedAt());
        dto.setDeliveredAt(entity.getDeliveredAt());
        dto.setStatus(entity.getStatus());
        dto.setApartment(entity.getApartment().getNumber());
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

    @Override
    public ResidentDashboardDTO getResidentDashboard(Long residentId) {
       ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(PackageEntity.class, PackageResponseDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getApartment().getId(), PackageResponseDTO::setApartment);
        });

        modelMapper.typeMap(VisitorEntity.class, VisitorResponseDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getApartment().getId(), VisitorResponseDTO::setApartment);
        });
       UserEntity author = residentDao.findById(residentId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
       Long apartmentId = author.getApartment().getId();
       List<NoticeEntity> lastNotices = noticeDao.findLatestNotices();
       List<NoticeResponseDTO> noticeResponseDTOS = lastNotices.stream()
               .map(notice -> modelMapper.map(notice, NoticeResponseDTO.class)).collect(Collectors.toList());
       List<VisitorEntity> lastVisitors = visitorDao.findLatestVisitorsByApartment(apartmentId, 4);
       List<VisitorResponseDTO> visitorResponseDTOS = lastVisitors.stream()
               .map(visitor -> modelMapper.map(visitor, VisitorResponseDTO.class)).collect(Collectors.toList());
       List<PackageEntity> lastPackages = packageDao.findLatestPackagesByApartment(apartmentId, 4);
       List<PackageResponseDTO>  packageResponseDTOS = lastPackages.stream()
               .map(packages -> modelMapper.map(packages, PackageResponseDTO.class )).collect(Collectors.toList());
       ApartmentInfoResponseDTO apartmentDTO = modelMapper.map(author.getApartment(), ApartmentInfoResponseDTO.class);

       ResidentDashboardDTO dashboardDTO = new ResidentDashboardDTO();
       dashboardDTO.setResidentName(author.getName());
       dashboardDTO.setApartment(apartmentDTO);
       dashboardDTO.setLatestPackages(packageResponseDTOS);
       dashboardDTO.setLatestVisitors(visitorResponseDTOS);
       dashboardDTO.setLatestNotices(noticeResponseDTOS);
       return dashboardDTO;

    }

}
