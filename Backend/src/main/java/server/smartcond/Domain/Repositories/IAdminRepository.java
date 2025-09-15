package server.smartcond.Domain.Repositories;

import org.springframework.data.repository.CrudRepository;
import server.smartcond.Domain.Entities.UserEntity;

public interface IAdminRepository extends CrudRepository <UserEntity, Long>{


}
