package server.smartcond.Domain.dao.interfaces;

import server.smartcond.Domain.Entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IAdminDao {

    List<UserEntity> findAllAdmins();

    Optional<UserEntity> findAdminById(Long id);

    void saveUserAdmin(UserEntity userEntity);

    UserEntity updateUserAdmin(UserEntity userEntity);



}
