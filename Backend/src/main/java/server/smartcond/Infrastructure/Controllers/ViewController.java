package server.smartcond.Infrastructure.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }

    @GetMapping("/registro-celador")
    public String showRegistroCelador() {
        return "registro-celador";
    }

    @GetMapping("/registro-residente")
    public String showRegistroResidente() {
        return "registro-residente";
    }

    @GetMapping("/registro-vehiculo")
    public String showRegistroVehiculo() {
        return "registro-vehiculo";
    }

    @GetMapping("panel-vehicles")
    public String panelVehicles() {
        return "panel-vehicles";
    }


    @GetMapping("/create-notice")
    public String createNotice() {
        return "create-notice";
    }

    @GetMapping("/dashboard-celador")
    public String showCeladorDashboard() {
        return "dashboard-celador";
    }


    @GetMapping("/dashboard-resident")
    public String showResidentDashboard() {
        return "dashboard-resident";
    }
    @GetMapping("/register-visitor")
    public String showRegisterVisitor() {
        return "register-visitor";
    }

    @GetMapping("/panel-visitors")
    public String panelVisitors() {
        return "panel-visitors";
    }

    @GetMapping("/register-package")
    public String showRegisterPackage() {
        return "register-package";
    }

    @GetMapping("/package-panel")
    public String viewPackagePanel() {
        return "package-panel";
    }
}
