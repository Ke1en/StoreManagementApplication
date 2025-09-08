package java412.storemanagementapplication.repository;

import java412.storemanagementapplication.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
    Page<Supplier> findByNameIgnoreCaseContainingOrEmailIgnoreCaseContaining(String name, String email, Pageable pageable);
}
