package server.smartcond.Domain.dao.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.smartcond.Domain.Entities.VehicleEntity;
import server.smartcond.Domain.dao.interfaces.IVehicleDao;

import java.util.List;

@Repository
public class VehicleDaoImpl implements IVehicleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<VehicleEntity> findAll() {
        return em.createQuery("SELECT v FROM VehicleEntity v", VehicleEntity.class).getResultList();
    }

    @Override
    public List<VehicleEntity> findByApartmentNumber(Integer number) {
        return em.createQuery
                ("SELECT v FROM VehicleEntity v WHERE v.apartment.number = :number", VehicleEntity.class)
                .setParameter("number", number)
                .getResultList();
    }

    @Override
    @Transactional
    public void saveVehicleEntity(VehicleEntity vehicleEntity) {
        this.em.persist(vehicleEntity);
        this.em.flush();
    }
}
