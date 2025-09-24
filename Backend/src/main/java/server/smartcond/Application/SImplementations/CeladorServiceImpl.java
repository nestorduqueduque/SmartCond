package server.smartcond.Application.SImplementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.smartcond.Domain.Dto.request.PackageRequestDTO;
import server.smartcond.Domain.Dto.request.VehicleRequestDTO;

import server.smartcond.Domain.Dto.request.VisitorRequestDTO;
import server.smartcond.Domain.Dto.response.CeladorResponseDTO;
import server.smartcond.Domain.Dto.response.PackageResponseDTO;
import server.smartcond.Domain.Dto.response.VehicleResponseDTO;
import server.smartcond.Domain.Dto.response.VisitorResponseDTO;
import server.smartcond.Domain.Entities.*;

import server.smartcond.Domain.Services.ICeladorService;

import server.smartcond.Domain.Utils.PackageStatus;
import server.smartcond.Domain.dao.interfaces.IApartmentDao;
import server.smartcond.Domain.dao.interfaces.IPackageDao;
import server.smartcond.Domain.dao.interfaces.IVehicleDao;
import server.smartcond.Domain.dao.interfaces.IVisitorDao;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CeladorServiceImpl implements ICeladorService {
    @Autowired
    IVehicleDao vehicleDao;
    @Autowired
    IApartmentDao apartmentDao;
    @Autowired
    IVisitorDao visitorDao;
    @Autowired
    IPackageDao packageDao;

    @Override
    public VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDto) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            VehicleEntity vehicleEntity = modelMapper.map(vehicleRequestDto, VehicleEntity.class);
            ApartmentEntity apartmentEntity = apartmentDao.findByNumber(vehicleRequestDto.getApartment()).
                    orElseThrow(() -> new RuntimeException("Apartamento No Encontrado " + vehicleRequestDto.getApartment()));
            vehicleEntity.setApartment(apartmentEntity);
            vehicleDao.saveVehicleEntity(vehicleEntity);
            VehicleResponseDTO responseDto = toVehicleResponse(vehicleEntity);
            return responseDto;

        } catch (Exception e) {
            throw new UnsupportedOperationException("Error al guardar Vehiculo");
        }
    }

    @Override
    public List<VehicleResponseDTO> findAllVehicles() {
        return this.vehicleDao.findAll()
                .stream()
                .map(this::toVehicleResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleResponseDTO> findVehiclesByApartmentNumber(Integer number) {
        return this.vehicleDao.findByApartmentNumber(number)
                .stream()
                .map(this::toVehicleResponse)
                .collect(Collectors.toList());
    }


    //Visitor
    @Override
    public VisitorResponseDTO createVisitor(VisitorRequestDTO visitorRequestDTO) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            VisitorEntity visitorEntity = modelMapper.map(visitorRequestDTO, VisitorEntity.class);
            ApartmentEntity apartment = apartmentDao.findByNumber(visitorRequestDTO.getApartment())
                    .orElseThrow(() -> new RuntimeException("Apartamento no encontrado: " + visitorRequestDTO.getApartment()));

            visitorEntity.setApartment(apartment);
            visitorEntity.setEntryTime(LocalDateTime.now(ZoneId.of("America/Bogota")));
            visitorDao.saveVisitor(visitorEntity);
            VisitorResponseDTO responseDTO = toVisitorResponse(visitorEntity);
            return responseDTO;
        }
        catch (Exception e) {
            throw new UnsupportedOperationException("Error al guardar Visitante");
        }
    }

    @Override
    public List<VisitorResponseDTO> findVisitorByApartment(Integer number) {
        return visitorDao.findByApartmentNumber(number)
                .stream()
                .map(this::toVisitorResponse)
                .collect(Collectors.toList());
    }


    //Packages

    @Override
    public List<PackageResponseDTO> findPackageNotDelivered() {
        return packageDao.findByStatusNoDelivered()
                .stream()
                .map(this::toPackageResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PackageResponseDTO> findByApartment(Integer number) {
        return packageDao.findByApartmentNumber(number)
                .stream()
                .map(this::toPackageResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PackageResponseDTO createPackage(PackageRequestDTO packageRequestDTO) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            PackageEntity packageEntity = modelMapper.map(packageRequestDTO, PackageEntity.class);
            ApartmentEntity apartment = apartmentDao.findByNumber(packageRequestDTO.getApartment())
                    .orElseThrow(() -> new RuntimeException("Apartamento no encontrado: " + packageRequestDTO.getApartment()));

            packageEntity.setApartment(apartment);
            packageEntity.setReceivedAt(LocalDateTime.now(ZoneId.of("America/Bogota")));
            packageEntity.setStatus(PackageStatus.RECEIVED);
            packageDao.savePackage(packageEntity);
            PackageResponseDTO responseDTO = toPackageResponse(packageEntity);
            return responseDTO;
        }
        catch (Exception e) {
            throw new UnsupportedOperationException("Error al guardar Paquete");
        }
    }

    @Override
    public PackageResponseDTO deliveredPackage(Long id) {
        PackageEntity packageEntity = packageDao.findById(id).orElseThrow(() -> new RuntimeException("Paquete no encontrado con id " + id));
        packageEntity.setStatus(PackageStatus.DELIVERED);
        packageEntity.setDeliveredAt(LocalDateTime.now(ZoneId.of("America/Bogota")));
        PackageEntity updated = packageDao.update(packageEntity);
        return toPackageResponse(updated);
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


}