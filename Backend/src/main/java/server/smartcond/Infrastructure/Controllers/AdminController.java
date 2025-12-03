package server.smartcond.Infrastructure.Controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.smartcond.Domain.Dto.request.CeladorRequestDTO;
import server.smartcond.Domain.Dto.request.NoticeRequestDTO;
import server.smartcond.Domain.Dto.request.ResidentRequestDTO;
import server.smartcond.Domain.Dto.response.AdminDashboardDTO;
import server.smartcond.Domain.Dto.response.CeladorResponseDTO;
import server.smartcond.Domain.Dto.response.NoticeResponseDTO;
import server.smartcond.Domain.Dto.response.ResidentResponseDTO;
import server.smartcond.Domain.Services.IAdminService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Tag(name ="Admin Endpoints")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @GetMapping("/check/document/{document}")
    public ResponseEntity<Map<String, Boolean>> checkDocument(@PathVariable String document) {
        boolean exists = adminService.checkDocumentExists(document);

        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/check/email/{email}")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@PathVariable String email) {
        boolean exists = adminService.checkEmailExists(email);

        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);

        return ResponseEntity.ok(response);
    }

    //Celador Endpoints    @Operation(summary = "get all the celadors")
    @GetMapping("/find-all-celador")
    public ResponseEntity<List<CeladorResponseDTO>> findAllCeladors(){
        return new ResponseEntity<>(this.adminService.findAllCeladors(), HttpStatus.OK);
    }


    @Operation(summary = "Get Celadors by Id")
    @GetMapping("/find-celador-byID/{id}")
    public ResponseEntity<CeladorResponseDTO> findCeladorById(@PathVariable Long id){
        return new ResponseEntity<>(this.adminService.finCeladorById(id), HttpStatus.OK);
    }


    @Operation(summary = "Update Celadors by Id")
    @PutMapping("/update-celador/{id}")
    public ResponseEntity<CeladorResponseDTO> updateCelador(@RequestBody CeladorRequestDTO celadorRequestDTO, @PathVariable Long id){
        return new ResponseEntity<>(this.adminService.updateCelador(celadorRequestDTO, id), HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar Celador (Soft Delete)")
    @DeleteMapping("/delete-celador/{id}")
    public ResponseEntity<Void> deleteCelador(@PathVariable Long id) {
        this.adminService.deleteCelador(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @Operation(summary = "Create Celador")
    @PostMapping("/create-celador")
        public ResponseEntity<CeladorResponseDTO> createCelador(@RequestBody CeladorRequestDTO celadorRequestDTO){
        return new ResponseEntity<>(this.adminService.createCelador(celadorRequestDTO), HttpStatus.CREATED);
        }



    @Operation(summary = "Get Residents by Id")
    @GetMapping("/find-resident-byID/{id}")
    public ResponseEntity<ResidentResponseDTO> findResidentById(@PathVariable Long id){
        return new ResponseEntity<>(this.adminService.findResidentById(id), HttpStatus.OK);
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

    @Operation(summary = "Actualizar Residente por ID")
    @PutMapping("/update-resident/{id}")
    public ResponseEntity<ResidentResponseDTO> updateResident(@RequestBody ResidentRequestDTO residentRequestDTO, @PathVariable Long id) {
        ResidentResponseDTO updatedResident = this.adminService.updateResident(residentRequestDTO, id);
        return new ResponseEntity<>(updatedResident, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar Residente (Soft Delete)")
    @DeleteMapping("/delete-resident/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id) {
        this.adminService.deleteResident(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


//    @Operation(summary = "Post a Notice")
//    @PostMapping("/{authorId}/create-notice")
//    public ResponseEntity<NoticeResponseDTO> create(@PathVariable Long authorId,@RequestBody NoticeRequestDTO noticeRequestDTO) {
//        return ResponseEntity.ok(adminService.createNotice(authorId, noticeRequestDTO));
//    }
    @Operation(summary = "Post a Notice")
    @PostMapping("/create-notice")
    public ResponseEntity<NoticeResponseDTO> createNotice(
            @AuthenticationPrincipal String username,
            @RequestBody NoticeRequestDTO noticeRequestDTO) {

        NoticeResponseDTO response = adminService.createNoticeByUsername(username, noticeRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all Notices")
    @GetMapping("/find-all-notices")
    public ResponseEntity<List<NoticeResponseDTO>> getAll() {
        return ResponseEntity.ok(adminService.getAllNotice());
    }


    @Operation(summary = "Get Notices by Id")
    @GetMapping("/find-notice-byID/{id}")
    public ResponseEntity<NoticeResponseDTO> findnoticeById(@PathVariable Long id){
        return new ResponseEntity<>(this.adminService.findNoticeById(id), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar Notice")
    @DeleteMapping("/delete-notice/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        this.adminService.deleteNotice(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Actualizar Notice por ID")
    @PutMapping("/update-notice/{id}")
    public ResponseEntity<NoticeResponseDTO> updateNotice(
            @RequestBody NoticeRequestDTO dto,
            @PathVariable Long id) {
        NoticeResponseDTO updatedNotice = this.adminService.updateNotice(id, dto);
        return new ResponseEntity<>(updatedNotice, HttpStatus.OK);
    }


    //Dashboard
//    @Operation(summary = "DashboardAdmin")
//    @GetMapping("/{adminId}")
//    public ResponseEntity<AdminDashboardDTO> getAdminDashboard(@PathVariable Long adminId) {
//        AdminDashboardDTO dashboard = adminService.getAdminDashboard(adminId);
//        return ResponseEntity.ok(dashboard);
//    }

    @Operation(summary = "Dashboard")
    @GetMapping("/dashboard-data")
    public  ResponseEntity<AdminDashboardDTO> getAdminDashboard(@AuthenticationPrincipal String username){
        AdminDashboardDTO dashboardDTO = adminService.getAdminDashboardUsername(username);
        return ResponseEntity.ok(dashboardDTO);
    }


    }

