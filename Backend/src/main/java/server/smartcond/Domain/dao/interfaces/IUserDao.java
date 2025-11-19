package server.smartcond.Domain.dao.interfaces;

import server.smartcond.Domain.Entities.UserEntity;

import java.util.Optional;

public interface IUserDao {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByDocument(Long document);
 }
