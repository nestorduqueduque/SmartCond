package server.smartcond.Application.SImplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.smartcond.Domain.Dto.request.LoginRequestDTO;
import server.smartcond.Domain.Dto.response.LoginResponseDTO;
import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.Utils.Jwtutils;
import server.smartcond.Domain.dao.interfaces.IUserDao;


import java.util.ArrayList;
import java.util.List;


@Service

public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private Jwtutils jwtutils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         UserEntity userEntity = userDao.findByEmail(email)
                 .orElseThrow(() -> new UsernameNotFoundException("El usuario con email " + email + " no existe"));

         List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
         authorityList.add(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name()));
         return new User(
                 userEntity.getEmail(),
                 userEntity.getPassword(),
                 userEntity.isEnabled(),
                 userEntity.isAccountNoExpired(),
                 userEntity.isCredentialNoExpired(),
                 userEntity.isAccountNoLocked(),
                 authorityList
         );
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
         String username = loginRequestDTO.getEmail();
         String password = loginRequestDTO.getPassword();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtutils.createToken(authentication);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(username, "User loged successfuly", accessToken, true);

        return  loginResponseDTO;
}

    public Authentication authenticate(String username, String password){
        UserDetails userDetails = this.loadUserByUsername(username);


        if(userDetails == null){
            throw  new BadCredentialsException("Invalid username or password");
        }

         if (!userDetails.isEnabled()) {
            throw new BadCredentialsException("El usuario está deshabilitado");
        }

        if (!userDetails.isAccountNonLocked()) {
            throw new BadCredentialsException("La cuenta está bloqueada");
        }

            if (!userDetails.isAccountNonExpired()) {
            throw new BadCredentialsException("La cuenta está expirada");
        }

         if (!userDetails.isCredentialsNonExpired()) {
            throw new BadCredentialsException("Las credenciales han expirado");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());

    }
}
