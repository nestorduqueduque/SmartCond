package server.smartcond.Domain.dao.interfaces;

import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.Entities.VehicleEntity;

import java.util.List;

public interface IVehicleDao {

    List<VehicleEntity> findAll();

    List<VehicleEntity> findByApartmentNumber(Integer number);
     void saveVehicleEntity(VehicleEntity vehicleEntity);


}
