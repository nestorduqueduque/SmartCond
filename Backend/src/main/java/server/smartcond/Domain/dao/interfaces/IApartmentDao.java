package server.smartcond.Domain.dao.interfaces;

import server.smartcond.Domain.Entities.ApartmentEntity;
import server.smartcond.Domain.Entities.UserEntity;

import java.util.Optional;

public interface IApartmentDao {

    Optional<ApartmentEntity> findById(Long id);

    Optional<ApartmentEntity> findByNumber(Integer number);
}
