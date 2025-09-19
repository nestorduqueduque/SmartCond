package server.smartcond.Domain.Services;

import server.smartcond.Domain.Dto.request.VehicleRequestDTO;
import server.smartcond.Domain.Dto.response.VehicleResponseDTO;

import java.util.List;

public interface ICeladorService {

    //Vehicles
    VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDto);
    List<VehicleResponseDTO> findAllVehicles();

    List<VehicleResponseDTO> findVehiclesByApartmentNumber(Integer number);
}
