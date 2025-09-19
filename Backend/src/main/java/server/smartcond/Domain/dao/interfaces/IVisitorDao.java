package server.smartcond.Domain.dao.interfaces;

import server.smartcond.Domain.Entities.VisitorEntity;

import java.util.List;

public interface IVisitorDao {

    VisitorEntity saveVisitor(VisitorEntity visitor);
    List<VisitorEntity> findByApartmentNumber(Integer number);

}
