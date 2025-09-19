package server.smartcond.Domain.dao.implementations;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.smartcond.Domain.Entities.VisitorEntity;
import server.smartcond.Domain.dao.interfaces.IVisitorDao;

import java.util.List;

@Repository
public class VisitorDaoImpl implements IVisitorDao {


    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public VisitorEntity saveVisitor(VisitorEntity visitor) {
        em.persist(visitor);
        return visitor;
    }

    @Override
    public List<VisitorEntity> findByApartmentNumber(Integer number) {
        return em.createQuery(
                        "SELECT v FROM VisitorEntity v WHERE v.apartment.number = :number", VisitorEntity.class)
                .setParameter("number", number)
                .getResultList();
    }
}
