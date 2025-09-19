package server.smartcond.Infrastructure.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.smartcond.Domain.Dto.request.VehicleRequestDTO;
import server.smartcond.Domain.Dto.request.VisitorRequestDTO;
import server.smartcond.Domain.Dto.response.CeladorResponseDTO;
import server.smartcond.Domain.Dto.response.VehicleResponseDTO;
import server.smartcond.Domain.Dto.response.VisitorResponseDTO;
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

    @GetMapping("/find-all-vehicles")
    public ResponseEntity<List<VehicleResponseDTO>> findAllVehicles() {
        return new ResponseEntity<>(this.celadorService.findAllVehicles(), HttpStatus.OK);
    }

    @GetMapping("/find-vehicles-apartment/{number}")
    public ResponseEntity<List<VehicleResponseDTO>> findAllVehiclesByApartment(@PathVariable Integer number){
        return new ResponseEntity<>(this.celadorService.findVehiclesByApartmentNumber(number), HttpStatus.OK);
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

}



