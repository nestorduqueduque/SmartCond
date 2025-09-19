package server.smartcond.Application.SImplementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.smartcond.Domain.Dto.request.VehicleRequestDTO;

import server.smartcond.Domain.Dto.response.CeladorResponseDTO;
import server.smartcond.Domain.Dto.response.VehicleResponseDTO;
import server.smartcond.Domain.Entities.ApartmentEntity;

import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.Entities.VehicleEntity;
import server.smartcond.Domain.Services.ICeladorService;

import server.smartcond.Domain.dao.interfaces.IApartmentDao;
import server.smartcond.Domain.dao.interfaces.IVehicleDao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CeladorServiceImpl implements ICeladorService {
    @Autowired
    IVehicleDao vehicleDao;
    @Autowired
    IApartmentDao apartmentDao;

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
}