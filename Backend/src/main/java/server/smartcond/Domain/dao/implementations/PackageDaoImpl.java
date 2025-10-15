package server.smartcond.Domain.dao.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.smartcond.Domain.Entities.PackageEntity;
import server.smartcond.Domain.Utils.PackageStatus;
import server.smartcond.Domain.dao.interfaces.IPackageDao;

import java.util.List;
import java.util.Optional;


@Repository
public class PackageDaoImpl implements IPackageDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public void savePackage(PackageEntity packageEntity) {
        this.em.persist(packageEntity);
        this.em.flush();
    }

    @Override
    public List<PackageEntity> findByApartmentNumber(Integer number) {
        return em.createQuery(
                        "SELECT p FROM PackageEntity p WHERE p.apartment.number = :number", PackageEntity.class)
                .setParameter("number", number)
                .getResultList();
    }

    @Override
    public List<PackageEntity> findByStatusNoDelivered() {
        return em.createQuery(
                        "SELECT p FROM PackageEntity p WHERE p.status = :status", PackageEntity.class)
                .setParameter("status", PackageStatus.RECEIVED)
                .getResultList();
    }

    @Override
    public Optional<PackageEntity> findById(Long id) {
        return Optional.ofNullable(em.find(PackageEntity.class, id));
    }

    @Override
    @Transactional
    public PackageEntity update(PackageEntity packageEntity) {
        return  em.merge(packageEntity);
    }

    @Override
    public List<PackageEntity> findLatestPackagesByApartment(Long apartmentId, int limit) {
        return em.createQuery(
                        "SELECT p FROM PackageEntity p WHERE p.apartment.id = :apartmentId ORDER BY p.id DESC",
                        PackageEntity.class)
                .setParameter("apartmentId", apartmentId)
                .setMaxResults(limit)
                .getResultList();
    }

}
