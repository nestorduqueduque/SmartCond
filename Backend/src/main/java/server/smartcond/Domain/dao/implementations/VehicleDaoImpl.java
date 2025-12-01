package server.smartcond.Domain.dao.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.smartcond.Domain.Entities.NoticeEntity;
import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.Entities.VehicleEntity;
import server.smartcond.Domain.Utils.RoleEnum;
import server.smartcond.Domain.Utils.VehicleOwnerType;
import server.smartcond.Domain.dao.interfaces.IVehicleDao;

import java.util.List;
import java.util.Optional;

@Repository
public class VehicleDaoImpl implements IVehicleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<VehicleEntity> findById(Long id) {
        return Optional.ofNullable(em.find(VehicleEntity.class, id));
    }

    @Override
    public List<VehicleEntity> findAll() {
        return em.createQuery("SELECT v FROM VehicleEntity v", VehicleEntity.class).getResultList();
    }

    @Override
    public List<VehicleEntity> findVisitorAll() {
        return em.createQuery(
                        "SELECT v FROM VehicleEntity v WHERE v.vehicleOwnerType = :type", VehicleEntity.class)
                .setParameter("type", VehicleOwnerType.VISITOR)
                .getResultList();
    }
    @Override
    public List<VehicleEntity> findResidentAll() {
        return em.createQuery(
                        "SELECT v FROM VehicleEntity v WHERE v.vehicleOwnerType = :type", VehicleEntity.class)
                .setParameter("type", VehicleOwnerType.RESIDENT)
                .getResultList();
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

    @Override
    @Transactional
    public void deleteById(Long id) {
        VehicleEntity vehicle = em.find(VehicleEntity.class, id);
        if (vehicle != null) {
            em.remove(vehicle);
        }
    }
}
