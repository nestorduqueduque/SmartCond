package server.smartcond.Application.SImplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.dao.interfaces.IUserDao;


import java.util.ArrayList;
import java.util.List;


@Service

public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserDao userDao;
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
}
