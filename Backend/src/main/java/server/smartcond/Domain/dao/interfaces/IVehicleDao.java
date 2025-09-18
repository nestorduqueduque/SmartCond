package server.smartcond.Domain.dao.interfaces;

import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.Entities.VehicleEntity;

import java.util.List;

public interface IVehicleDao {

    List<VehicleEntity> findAll();
    void saveVehicleEntity(VehicleEntity vehicleEntity);


}
