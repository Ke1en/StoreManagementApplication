package java412.storemanagementapplication.service;

import jakarta.validation.Valid;
import java412.storemanagementapplication.dto.SupplierResponseDto;
import java412.storemanagementapplication.entity.Supplier;
import java412.storemanagementapplication.mapper.SupplierMapper;
import java412.storemanagementapplication.repository.SupplierRepository;
import java412.storemanagementapplication.request.SupplierContactRequest;
import java412.storemanagementapplication.request.SupplierRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
@Transactional(readOnly = true)
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper supplierMapper;


    @Transactional(rollbackFor = Exception.class)
    public SupplierResponseDto createSupplier(@Valid SupplierRequest request) {

        Supplier supplier = new Supplier(UUID.randomUUID(), request.getName(), request.getEmail(), request.getPhone(), request.getAddress(), request.getWebsite(), null);

        supplierRepository.saveAndFlush(supplier);

        return supplierMapper.mapToSupplierResponseDto(supplier);

    }

    public SupplierResponseDto findById(UUID supplierId) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow();

        return supplierMapper.mapToSupplierResponseDto(supplier);

    }

    @Transactional(rollbackFor = Exception.class)
    public SupplierResponseDto updateById(UUID supplierId, @Valid SupplierRequest request) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow();

        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPhone(request.getPhone());
        supplier.setAddress(request.getAddress());
        supplier.setWebsite(request.getWebsite());

        supplierRepository.saveAndFlush(supplier);

        return supplierMapper.mapToSupplierResponseDto(supplier);

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteSupplier(UUID supplierId) {
        supplierRepository.deleteById(supplierId);
    }

    public Page<SupplierResponseDto> findSuppliers(String q, Pageable pageable) {

        Page<Supplier> page;

        if (q == null || q.isBlank()) {
            page = supplierRepository.findAll(pageable);
        } else {
            page = supplierRepository.findByNameIgnoreCaseContainingOrEmailIgnoreCaseContaining(q.toLowerCase(), q.toLowerCase(), pageable);
        }

        return page.map(supplierMapper::mapToSupplierResponseDto);

    }

    @Transactional(rollbackFor = Exception.class)
    public SupplierResponseDto updateContactById(UUID supplierId, SupplierContactRequest request) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow();

        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setWebsite(request.getWebsite());

        supplierRepository.saveAndFlush(supplier);

        return supplierMapper.mapToSupplierResponseDto(supplier);

    }

}
