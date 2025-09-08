package java412.storemanagementapplication.controller;

import jakarta.validation.Valid;
import java412.storemanagementapplication.dto.AllStoresResponseDto;
import java412.storemanagementapplication.dto.StoreResponseDto;
import java412.storemanagementapplication.request.StoreRequest;
import java412.storemanagementapplication.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping
    public ResponseEntity<StoreResponseDto> createStore(@RequestBody StoreRequest request) {

        StoreResponseDto storeResponseDto = storeService.createStore(request);

        return ResponseEntity.ok(storeResponseDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable UUID id) {

        storeService.deleteStore(id);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreResponseDto> findStoreById(@PathVariable UUID id) {

        StoreResponseDto storeResponseDto = storeService.findById(id);

        return ResponseEntity.ok(storeResponseDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreResponseDto> updateStore(@PathVariable UUID id, @RequestBody StoreRequest request) {

        StoreResponseDto storeResponseDto = storeService.updateStoreById(id, request);

        return ResponseEntity.ok(storeResponseDto);

    }

    @GetMapping
    public ResponseEntity<List<AllStoresResponseDto>> findAllStores() {

        List<AllStoresResponseDto> allStoresResponseDto = storeService.findAllStores();

        return ResponseEntity.ok(allStoresResponseDto);

    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<AllStoresResponseDto>> findAllStoreByLocation(@PathVariable String location) {

        List<AllStoresResponseDto> allStoresByLocationResponseDto = storeService.findAllStoresByLocation(location);

        return ResponseEntity.ok(allStoresByLocationResponseDto);

    }

    @GetMapping("/sorted")
    public ResponseEntity<List<AllStoresResponseDto>> findAllStoresByName() {

        List<AllStoresResponseDto> allStoresByNameResponseDto = storeService.findAllStoresByName();

        return ResponseEntity.ok(allStoresByNameResponseDto);

    }

    @PostMapping("/{id}/copy")
    public ResponseEntity<StoreResponseDto> copyStore(@PathVariable UUID id) {

        StoreResponseDto copyStoreResponseDto = storeService.copy(id);

        return ResponseEntity.ok(copyStoreResponseDto);

    }

}
