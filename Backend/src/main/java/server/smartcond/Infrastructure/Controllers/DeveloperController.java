package server.smartcond.Infrastructure.Controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.smartcond.Domain.Dto.request.AdminRequestDTO;
import server.smartcond.Domain.Dto.request.CeladorRequestDTO;
import server.smartcond.Domain.Dto.response.AdminResponseDTO;
import server.smartcond.Domain.Dto.response.CeladorResponseDTO;
import server.smartcond.Domain.Services.IDeveloperService;

import java.util.List;

@RestController
@RequestMapping("/developer")
@Tag(name ="Developer Endpoints")
public class DeveloperController {

    @Autowired
    private IDeveloperService developerService;

    @Operation(summary = "Create Admin")
    @PostMapping("/create-admin")
    public ResponseEntity<AdminResponseDTO> createAdmin(@RequestBody AdminRequestDTO adminRequestDTO){
        return new ResponseEntity<>(this.developerService.createAdmin(adminRequestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "get all the admins")
    @GetMapping("/find-all-admins")
    public ResponseEntity<List<AdminResponseDTO>> findAllAdmins(){
        return new ResponseEntity<>(this.developerService.findAllAdmins(), HttpStatus.OK);
    }



}
