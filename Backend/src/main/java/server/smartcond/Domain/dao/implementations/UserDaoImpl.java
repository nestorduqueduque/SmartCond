package server.smartcond.Domain.dao.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import server.smartcond.Domain.Entities.UserEntity;
import server.smartcond.Domain.dao.interfaces.IUserDao;

import java.util.Optional;


@Service
public class UserDaoImpl implements IUserDao {

    @PersistenceContext
    private EntityManager em;
    @Override
    public Optional<UserEntity> findByEmail(String email) {
        try {
            UserEntity user = em.createQuery(
                            "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();

            return Optional.of(user);
        } catch (jakarta.persistence.NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findByDocument(Long document) {
        try {
            UserEntity user = em.createQuery(
                            "SELECT u FROM UserEntity u WHERE u.document = :document", UserEntity.class)
                    .setParameter("document", document)
                    .getSingleResult();

            return Optional.of(user);
        } catch (jakarta.persistence.NoResultException e) {
            return Optional.empty();
        }
    }

}
