package server.smartcond.Infrastructure.Controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.smartcond.Domain.Dto.request.PackageRequestDTO;
import server.smartcond.Domain.Dto.request.VehicleRequestDTO;
import server.smartcond.Domain.Dto.request.VisitorRequestDTO;
import server.smartcond.Domain.Dto.response.*;
import server.smartcond.Domain.Services.ICeladorService;

import java.util.List;

@RestController
@RequestMapping("/celador")
public class CeladorController {

    @Autowired
    ICeladorService celadorService;

    //Vehicles

    @PostMapping("/create-vehicle")
    public ResponseEntity<VehicleResponseDTO> createVehicle(@RequestBody VehicleRequestDTO vehicleRequestDto) {
        return new ResponseEntity<>(this.celadorService.createVehicle(vehicleRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/create-vehicle-visitor")
    public ResponseEntity<VehicleResponseDTO> createVehicleVisitor(@RequestBody VehicleRequestDTO vehicleRequestDto) {
        return new ResponseEntity<>(this.celadorService.createVisitorVehicle(vehicleRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/find-all-vehicles")
    public ResponseEntity<List<VehicleResponseDTO>> findAllVehicles() {
        return new ResponseEntity<>(this.celadorService.findAllVehicles(), HttpStatus.OK);
    }

    @GetMapping("/find-vehicle-id/{id}")
    public ResponseEntity<List<VehicleResponseDTO>> findVehicleById(@PathVariable Long id){
        return new ResponseEntity<>(this.celadorService.findVehicleByID(id), HttpStatus.OK);
    }
    @GetMapping("/find-resident-vehicles")
    public ResponseEntity<List<VehicleResponseDTO>> findResidentVehicles() {
        return new ResponseEntity<>(this.celadorService.findResidentsVehicles(), HttpStatus.OK);
    }

    @GetMapping("/find-visitor-vehicles")
    public ResponseEntity<List<VehicleResponseDTO>> findVisitorVehicles() {
        return new ResponseEntity<>(this.celadorService.findVisitorsVehicles(), HttpStatus.OK);
    }

    @GetMapping("/find-vehicles-apartment/{number}")
    public ResponseEntity<List<VehicleResponseDTO>> findAllVehiclesByApartment(@PathVariable Integer number){
        return new ResponseEntity<>(this.celadorService.findVehiclesByApartmentNumber(number), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar Vehicle")
    @DeleteMapping("/delete-vehicle/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        this.celadorService.deleteVehicle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Visitors
    @PostMapping("/create-visitor")
    public ResponseEntity<VisitorResponseDTO> createVisitor(@RequestBody VisitorRequestDTO visitorRequestDTO){
        return new ResponseEntity<>(this.celadorService.createVisitor(visitorRequestDTO), HttpStatus.OK );
    }

    @GetMapping("/find-visitors-apartment/{number}")
    public ResponseEntity<List<VisitorResponseDTO>> findVisitorByApartment(@PathVariable Integer number){
        return new ResponseEntity<>(this.celadorService.findVisitorByApartment(number), HttpStatus.OK );
    }

    @GetMapping("/find-all-visitor")
    public ResponseEntity<List<VisitorResponseDTO>> findAllVisitors(){
        return new ResponseEntity<>(this.celadorService.findAllVisitors(), HttpStatus.OK );
    }

    //Packages

    @PostMapping("/create-package")
    public  ResponseEntity<PackageResponseDTO> createPackage(@RequestBody PackageRequestDTO packageRequestDTO){
        return new ResponseEntity<>(this.celadorService.createPackage(packageRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/find-package-apartment/{number}")
    public ResponseEntity<List<PackageResponseDTO>> findPackageByApartment(@PathVariable Integer number){
        return new ResponseEntity<>(this.celadorService.findByApartment(number), HttpStatus.OK);
    }

    @GetMapping("/find-all-package")
    public ResponseEntity<List<PackageResponseDTO>> findAllPackages(){
        return new ResponseEntity<>(this.celadorService.findAllPackage(), HttpStatus.OK);
    }

    @GetMapping("/find-package-not-delivered")
    public ResponseEntity<List<PackageResponseDTO>> findPackageNotDelivered(){
        return new ResponseEntity<>(this.celadorService.findPackageNotDelivered(), HttpStatus.OK);
    }

     @PutMapping("/package-delivered/{id}")
    public ResponseEntity<PackageResponseDTO> deliveredPackage(@PathVariable Long id){
        return  ResponseEntity.ok(celadorService.deliveredPackage(id));
     }

     //Dashboard
//     @Operation(summary = "DashboardCelador")
//     @GetMapping("/{celadorId}")
//     public ResponseEntity<CeladorDashboardDTO> getAdminDashboard(@PathVariable Long celadorId) {
//         CeladorDashboardDTO dashboard = celadorService.getCeladorDashboard(celadorId);
//         return ResponseEntity.ok(dashboard);
//     }

    @Operation(summary = "Dashboard del residente")
    @GetMapping("/dashboard-data-celador")
    public  ResponseEntity<CeladorDashboardDTO> getCeladorDashboard(@AuthenticationPrincipal String username){
        CeladorDashboardDTO dashboardDTO = celadorService.getCeladorDashboardByUsername(username);
        return ResponseEntity.ok(dashboardDTO);
    }

}



