package server.smartcond.Infrastructure.Controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.smartcond.Application.SImplementations.UserDetailServiceImpl;
import server.smartcond.Domain.Dto.request.LoginRequestDTO;
import server.smartcond.Domain.Dto.response.LoginResponseDTO;
import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.dao.interfaces.IUserDao;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private IUserDao userDao;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
            return new ResponseEntity<>(this.userDetailService.login(loginRequest), HttpStatus.OK );
    }
}
