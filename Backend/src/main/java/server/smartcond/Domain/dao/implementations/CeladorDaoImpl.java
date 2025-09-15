package server.smartcond.Domain.dao.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.Utils.RoleEnum;
import server.smartcond.Domain.dao.interfaces.ICeladorDao;

import java.util.List;
import java.util.Optional;


@Repository
public class CeladorDaoImpl implements ICeladorDao {

    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        return this.em.createQuery(
                        "SELECT u FROM UserEntity u WHERE u.role = :rol", UserEntity.class)
                .setParameter("rol", RoleEnum.CELADOR)
                .getResultList();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findById(Long id) {
        return Optional.ofNullable(this.em.find(UserEntity.class, id));
    }

    @Override
    @Transactional
    public void saveUser(UserEntity userEntity) {
        this.em.persist(userEntity);
        this.em.flush();
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        return null;
    }
}
