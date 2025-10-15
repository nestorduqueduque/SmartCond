package server.smartcond.Domain.dao.interfaces;

import server.smartcond.Domain.Dto.request.PackageRequestDTO;
import server.smartcond.Domain.Entities.PackageEntity;

import java.util.List;
import java.util.Optional;

public interface IPackageDao {

    void savePackage(PackageEntity packageEntity);
    List<PackageEntity> findByApartmentNumber(Integer number);

    List<PackageEntity> findByStatusNoDelivered();

    Optional<PackageEntity> findById(Long id);
    PackageEntity update(PackageEntity packageEntity);

    List<PackageEntity> findLatestPackagesByApartment(Long apartmentId, int limit);

}
