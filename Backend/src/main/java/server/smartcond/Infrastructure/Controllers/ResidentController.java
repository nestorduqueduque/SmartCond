package server.smartcond.Infrastructure.Controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.smartcond.Domain.Dto.response.ResidentDashboardResponseDTO;
import server.smartcond.Domain.Services.IResidentService;

@RestController
@RequestMapping("/resident")
@Tag(name ="Resident Endpoints")
public class ResidentController {

    @Autowired
    IResidentService residentService;

    @GetMapping("/{number}/dashboard")
    public ResponseEntity<ResidentDashboardResponseDTO> getDashboard(@PathVariable Integer number) {
        ResidentDashboardResponseDTO response = residentService.getResidentDashboard(number);
        return ResponseEntity.ok(response);
    }
}
