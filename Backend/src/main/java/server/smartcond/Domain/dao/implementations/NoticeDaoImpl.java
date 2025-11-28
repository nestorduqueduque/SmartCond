package server.smartcond.Domain.dao.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.smartcond.Domain.Entities.NoticeEntity;
import server.smartcond.Domain.dao.interfaces.INoticeDao;

import java.util.List;
import java.util.Optional;

@Repository
public class NoticeDaoImpl implements INoticeDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(NoticeEntity notice) {
        this.em.persist(notice);
        this.em.flush();
    }

    @Override
    public Optional<NoticeEntity> findById(Long id) {
        return Optional.ofNullable(em.find(NoticeEntity.class, id));
    }

    @Override
    public List<NoticeEntity> findAll() {
        return em.createQuery("SELECT c FROM NoticeEntity c ORDER BY c.createdAt DESC", NoticeEntity.class)
                .getResultList();
    }

    @Override
    public List<NoticeEntity> findLatestNotices() {
        String jpql = "SELECT n FROM NoticeEntity n ORDER BY n.createdAt DESC";
        TypedQuery<NoticeEntity> query = em.createQuery(jpql, NoticeEntity.class);
        query.setMaxResults(3);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        NoticeEntity notice = em.find(NoticeEntity.class, id);
        if (notice != null) {
            em.remove(notice);
        }
    }

    @Override
    @Transactional
    public void updateNotice(NoticeEntity notice) {
        em.merge(notice);
    }


}
