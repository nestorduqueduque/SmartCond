package server.smartcond.Application.SImplementations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.smartcond.Domain.Dto.response.*;
import server.smartcond.Domain.Entities.NoticeEntity;
import server.smartcond.Domain.Entities.PackageEntity;
import server.smartcond.Domain.Entities.VehicleEntity;
import server.smartcond.Domain.Entities.VisitorEntity;
import server.smartcond.Domain.Services.IResidentService;
import server.smartcond.Domain.dao.interfaces.*;

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


    @Override
    public ResidentDashboardResponseDTO getResidentDashboard(Integer number) {
        ResidentDashboardResponseDTO dto = new ResidentDashboardResponseDTO();
        dto.setLastPackages(packageDao.findByApartmentNumber(number).stream().map(this::toPackageResponse).toList());
        dto.setLastNotices(noticeDao.findAll().stream().map(this::toNoticeResponse).toList());
        dto.setVehicles(vehicleDao.findByApartmentNumber(number).stream().map(this::toVehicleResponse).toList());
        dto.setVisitors(visitorDao.findByApartmentNumber(number).stream().map(this::toVisitorResponse).toList());
        return dto;
    }



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
}
