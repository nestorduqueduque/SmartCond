package server.smartcond.Domain.dao.interfaces;

import server.smartcond.Domain.Entities.NoticeEntity;

import java.util.List;
import java.util.Optional;

public interface INoticeDao {

    void save(NoticeEntity notice);

    Optional<NoticeEntity> findById(Long id);
    List<NoticeEntity> findAll();
}
