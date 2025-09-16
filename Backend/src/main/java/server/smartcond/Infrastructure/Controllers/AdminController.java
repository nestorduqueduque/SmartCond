package server.smartcond.Infrastructure.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.smartcond.Domain.Dto.request.CeladorRequestDTO;
import server.smartcond.Domain.Dto.request.ResidentRequestDTO;
import server.smartcond.Domain.Dto.response.CeladorResponseDTO;
import server.smartcond.Domain.Dto.response.ResidentResponseDTO;
import server.smartcond.Domain.Services.IAdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    //Celador Endpoints
    //Find all Celadors
    @GetMapping("/find-all-celador")
    public ResponseEntity<List<CeladorResponseDTO>> findAllCeladors(){
        return new ResponseEntity<>(this.adminService.findAllCeladors(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public  String hello(){
        return "hello World";
    }

    //Find Celador by id
    @GetMapping("/find-celador/{id}")
    public ResponseEntity<CeladorResponseDTO> findById(@PathVariable Long id){
        return new ResponseEntity<>(this.adminService.finById(id), HttpStatus.OK);
    }

    //Update Celador
    @PutMapping("/update-celador/{id}")
    public ResponseEntity<CeladorResponseDTO> updateCelador(@RequestBody CeladorRequestDTO celadorRequestDTO, @PathVariable Long id){
        return new ResponseEntity<>(this.adminService.updateCelador(celadorRequestDTO, id), HttpStatus.CREATED);
    }
    //Create User Celador
    @PostMapping("/create-celador")
        public ResponseEntity<CeladorResponseDTO> createCelador(@RequestBody CeladorRequestDTO celadorRequestDTO){
        return new ResponseEntity<>(this.adminService.createCelador(celadorRequestDTO), HttpStatus.CREATED);
        }


    //Resident Endpoints
    @PostMapping("/create-resident")
    public ResponseEntity<ResidentResponseDTO> createResident(@RequestBody ResidentRequestDTO residentRequestDTO){
        return new ResponseEntity<>(this.adminService.createResident(residentRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/find-all-resident")
    public ResponseEntity<List<ResidentResponseDTO>> findAllResidents(){
        return new ResponseEntity<>(this.adminService.findAllResidents(), HttpStatus.OK);
    }
    }

