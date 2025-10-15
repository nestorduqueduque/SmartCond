package server.smartcond.Domain.dao.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.Utils.RoleEnum;
import server.smartcond.Domain.dao.interfaces.IResidentDao;

import java.util.List;
import java.util.Optional;


@Repository
public class ResidentDaoImpl implements IResidentDao {

    @PersistenceContext
    private EntityManager em;
    @Override
    public List<UserEntity> findAllResidents() {
        return this.em.createQuery(
                        "SELECT u FROM UserEntity u WHERE u.role = :rol", UserEntity.class)
                .setParameter("rol", RoleEnum.RESIDENT)
                .getResultList();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.ofNullable(this.em.find(UserEntity.class, id));
    }

    @Override
    @Transactional
    public void saveUserResident(UserEntity userEntity) {
        this.em.persist(userEntity);
        this.em.flush();
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        return null;
    }
}
