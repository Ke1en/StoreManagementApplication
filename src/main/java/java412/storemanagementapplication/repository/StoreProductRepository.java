package java412.storemanagementapplication.repository;

import java412.storemanagementapplication.entity.StoreProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, UUID> {
    List<StoreProduct> findByStoreId(UUID id);
}
