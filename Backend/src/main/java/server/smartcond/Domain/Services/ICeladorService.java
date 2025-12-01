package server.smartcond.Domain.Services;

import server.smartcond.Domain.Dto.request.PackageRequestDTO;
import server.smartcond.Domain.Dto.request.VehicleRequestDTO;
import server.smartcond.Domain.Dto.request.VisitorRequestDTO;
import server.smartcond.Domain.Dto.response.*;

import java.util.List;

public interface ICeladorService {

    //Vehicles
    VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDto);
    VehicleResponseDTO createVisitorVehicle(VehicleRequestDTO vehicleRequestDto);
    List<VehicleResponseDTO> findAllVehicles();
    List<VehicleResponseDTO> findVisitorsVehicles();
    List<VehicleResponseDTO> findResidentsVehicles();
    List<VehicleResponseDTO> findVehiclesByApartmentNumber(Integer number);
    void deleteVehicle(Long id);
    List<VehicleResponseDTO> findVehicleByID(Long id);


    //Visitors
    VisitorResponseDTO createVisitor(VisitorRequestDTO visitorRequestDTO);
    List<VisitorResponseDTO> findVisitorByApartment(Integer number);
    List<VisitorResponseDTO> findAllVisitors();


    //Packages
    List<PackageResponseDTO> findPackageNotDelivered();
    List<PackageResponseDTO>findAllPackage();
    List<PackageResponseDTO> findByApartment(Integer number);
    PackageResponseDTO createPackage(PackageRequestDTO packageRequestDTO);
    PackageResponseDTO deliveredPackage(Long id);


    //Dashboard
    CeladorDashboardDTO getCeladorDashboard(Long id);

    CeladorDashboardDTO getCeladorDashboardByUsername(String username);
}
