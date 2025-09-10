package java412.storemanagementapplication.controller;

import java412.storemanagementapplication.dto.SupplierResponseDto;
import java412.storemanagementapplication.request.SupplierContactRequest;
import java412.storemanagementapplication.request.SupplierRequest;
import java412.storemanagementapplication.service.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierResponseDto> createSupplier(@RequestBody SupplierRequest request) {

        SupplierResponseDto supplierResponseDto = supplierService.createSupplier(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(supplierResponseDto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> findSupplierById(@PathVariable UUID id) {

        SupplierResponseDto supplierResponseDto = supplierService.findById(id);

        return ResponseEntity.ok(supplierResponseDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> updateSupplierById(@PathVariable UUID id, @RequestBody SupplierRequest request) {

        SupplierResponseDto supplierResponseDto = supplierService.updateById(id, request);

        return ResponseEntity.ok(supplierResponseDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplierById(@PathVariable UUID id) {

        supplierService.deleteSupplier(id);

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public Page<SupplierResponseDto> findSuppliers(@RequestParam(value = "q", required = false) String q, Pageable pageable) {
        return supplierService.findSuppliers(q, pageable);
    }

    @PatchMapping("/{id}/contact")
    public ResponseEntity<SupplierResponseDto> updateSupplierContactById(@PathVariable UUID id, @RequestBody SupplierContactRequest request) {

        SupplierResponseDto supplierResponseDto = supplierService.updateContactById(id, request);

        return ResponseEntity.ok(supplierResponseDto);

    }

}
