package server.smartcond.Infrastructure.Controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.smartcond.Domain.Dto.request.CeladorRequestDTO;
import server.smartcond.Domain.Dto.request.NoticeRequestDTO;
import server.smartcond.Domain.Dto.request.ResidentRequestDTO;
import server.smartcond.Domain.Dto.response.CeladorResponseDTO;
import server.smartcond.Domain.Dto.response.NoticeResponseDTO;
import server.smartcond.Domain.Dto.response.ResidentResponseDTO;
import server.smartcond.Domain.Services.IAdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name ="Admin Endpoints")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    //Celador Endpoints


    @Operation(summary = "get all the celadors")
    @GetMapping("/find-all-celador")
    public ResponseEntity<List<CeladorResponseDTO>> findAllCeladors(){
        return new ResponseEntity<>(this.adminService.findAllCeladors(), HttpStatus.OK);
    }


    @Operation(summary = "Get Celadors by Id")
    public ResponseEntity<CeladorResponseDTO> findById(@PathVariable Long id){
        return new ResponseEntity<>(this.adminService.finById(id), HttpStatus.OK);
    }


    @Operation(summary = "Update Celadors by Id")
    @PutMapping("/update-celador/{id}")
    public ResponseEntity<CeladorResponseDTO> updateCelador(@RequestBody CeladorRequestDTO celadorRequestDTO, @PathVariable Long id){
        return new ResponseEntity<>(this.adminService.updateCelador(celadorRequestDTO, id), HttpStatus.CREATED);
    }

    @Operation(summary = "Create Celador")
    @PostMapping("/create-celador")
        public ResponseEntity<CeladorResponseDTO> createCelador(@RequestBody CeladorRequestDTO celadorRequestDTO){
        return new ResponseEntity<>(this.adminService.createCelador(celadorRequestDTO), HttpStatus.CREATED);
        }



    @Operation(summary = "Create Resident")
    @PostMapping("/create-resident")
    public ResponseEntity<ResidentResponseDTO> createResident(@RequestBody ResidentRequestDTO residentRequestDTO){
        return new ResponseEntity<>(this.adminService.createResident(residentRequestDTO), HttpStatus.CREATED);
    }


    @Operation(summary = "Get all Residents")
    @GetMapping("/find-all-resident")
    public ResponseEntity<List<ResidentResponseDTO>> findAllResidents(){
        return new ResponseEntity<>(this.adminService.findAllResidents(), HttpStatus.OK);
    }


    @Operation(summary = "Post a Notice")
    @PostMapping("/create-notice")
    public ResponseEntity<NoticeResponseDTO> create(@RequestBody NoticeRequestDTO noticeRequestDTO) {
        return ResponseEntity.ok(adminService.createNotice(noticeRequestDTO));
    }

    @Operation(summary = "Get all Notices")
    @GetMapping("/find-all-notices")
    public ResponseEntity<List<NoticeResponseDTO>> getAll() {
        return ResponseEntity.ok(adminService.getAllNotice());
    }


    }

