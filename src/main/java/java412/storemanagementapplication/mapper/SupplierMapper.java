package java412.storemanagementapplication.mapper;

import java412.storemanagementapplication.dto.SupplierResponseDto;
import java412.storemanagementapplication.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public SupplierResponseDto mapToSupplierResponseDto(Supplier supplier) {
        return new SupplierResponseDto(supplier.getId(), supplier.getName(), supplier.getEmail(), supplier.getPhone(), supplier.getAddress(), supplier.getWebsite());
    }
}
