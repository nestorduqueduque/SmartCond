package server.smartcond.Domain.dao.interfaces;

import server.smartcond.Domain.Entities.NoticeEntity;
import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.Entities.VehicleEntity;

import java.util.List;
import java.util.Optional;

public interface IVehicleDao {

    Optional<VehicleEntity> findById(Long id);
    List<VehicleEntity> findAll();
    List<VehicleEntity> findVisitorAll();
    List<VehicleEntity> findResidentAll();
    List<VehicleEntity> findByApartmentNumber(Integer number);
    void saveVehicleEntity(VehicleEntity vehicleEntity);
    void deleteById(Long id);


}
