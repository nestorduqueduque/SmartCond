package server.smartcond.Domain.dao.interfaces;

import server.smartcond.Domain.Entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ICeladorDao {


    List<UserEntity> findAll();

    Optional<UserEntity> findById(Long id);

    void saveUser(UserEntity userEntity);

    UserEntity updateUser(UserEntity userEntity);



}



