package server.smartcond.Domain.dao.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.Utils.RoleEnum;
import server.smartcond.Domain.dao.interfaces.IAdminDao;

import java.util.List;
import java.util.Optional;


@Repository
public class AdminDaoImpl implements IAdminDao {

    @PersistenceContext
    private EntityManager em;
    @Override
    public List<UserEntity> findAllAdmins() {
        return this.em.createQuery(
                        "SELECT u FROM UserEntity u WHERE u.role = :rol", UserEntity.class)
                .setParameter("rol", RoleEnum.ADMIN)
                .getResultList();
    }

    @Override
    public Optional<UserEntity> findAdminById(Long id) {
        return Optional.ofNullable(this.em.find(UserEntity.class, id));
    }

    @Override
    @Transactional
    public void saveUserAdmin(UserEntity userEntity) {
        this.em.persist(userEntity);
        this.em.flush();

    }

    @Override
    public UserEntity updateUserAdmin(UserEntity userEntity) {
        return null;
    }
}
