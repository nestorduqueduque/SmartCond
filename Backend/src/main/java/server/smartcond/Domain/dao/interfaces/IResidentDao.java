package server.smartcond.Domain.dao.interfaces;


import server.smartcond.Domain.Entities.UserEntity;

import java.util.List;
import java.util.Optional;
public interface IResidentDao {

    List<UserEntity> findAllResidents();

    Optional<UserEntity> findById(Long id);

    void saveUserResident(UserEntity userEntity);

    UserEntity updateUser(UserEntity userEntity);
}
