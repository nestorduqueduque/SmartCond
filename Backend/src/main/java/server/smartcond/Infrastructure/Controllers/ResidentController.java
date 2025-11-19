package server.smartcond.Infrastructure.Controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.smartcond.Domain.Dto.response.AdminDashboardDTO;
import server.smartcond.Domain.Dto.response.ResidentDashboardDTO;
import server.smartcond.Domain.Services.IResidentService;

@RestController
@RequestMapping("/resident")
@Tag(name ="Resident Endpoints")
public class ResidentController {

    @Autowired
    IResidentService residentService;


//    @Operation(summary = "Dashboard del residente")
//    @GetMapping("/{residentId}")
//    public ResponseEntity<ResidentDashboardDTO> getDashboard(@PathVariable Long residentId) {
//        ResidentDashboardDTO response = residentService.getResidentDashboard(residentId);
//        return ResponseEntity.ok(response);
//    }

    @Operation(summary = "Dashboard del residente")
    @GetMapping("/dashboard-data-resident")
    public  ResponseEntity<ResidentDashboardDTO> getResidentDashboard(@AuthenticationPrincipal String username){
        ResidentDashboardDTO dashboardDTO = residentService.getResidentDashboardByUsername(username);
        return ResponseEntity.ok(dashboardDTO);
    }
}
