package server.smartcond.Domain.Services;

import server.smartcond.Domain.Dto.request.VehicleRequestDTO;
import server.smartcond.Domain.Dto.request.VisitorRequestDTO;
import server.smartcond.Domain.Dto.response.VehicleResponseDTO;
import server.smartcond.Domain.Dto.response.VisitorResponseDTO;

import java.util.List;

public interface ICeladorService {

    //Vehicles
    VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDto);
    List<VehicleResponseDTO> findAllVehicles();

    List<VehicleResponseDTO> findVehiclesByApartmentNumber(Integer number);

    //Visitors
    VisitorResponseDTO createVisitor(VisitorRequestDTO visitorRequestDTO);

    List<VisitorResponseDTO> findVisitorByApartment(Integer number);
}
