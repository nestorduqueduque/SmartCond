package server.smartcond.Domain.dao.implementations;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.smartcond.Domain.Entities.ApartmentEntity;
import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.dao.interfaces.IApartmentDao;
import java.util.Optional;


@Repository
public class ApartmentDaoImpl implements IApartmentDao {
    @PersistenceContext
    private EntityManager em;


    @Override
    public Optional<ApartmentEntity> findById(Long id) {
        return Optional.ofNullable(this.em.find(ApartmentEntity.class, id));
    }

    @Override
    public Optional<ApartmentEntity> findByNumber(Integer number) {
        return em.createQuery("SELECT a FROM ApartmentEntity a WHERE a.number = :number", ApartmentEntity.class)
                .setParameter("number", number)
                .getResultStream()
                .findFirst();
    }


}
