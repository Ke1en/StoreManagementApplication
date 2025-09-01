package java412.storemanagementapplication.service;

import jakarta.validation.Valid;
import java412.storemanagementapplication.dto.StoreResponseDto;
import java412.storemanagementapplication.entity.Store;
import java412.storemanagementapplication.mapper.StoreMapper;
import java412.storemanagementapplication.repository.StoreRepository;
import java412.storemanagementapplication.request.StoreRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
@Transactional(readOnly = true)
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreMapper storeMapper;

    @Transactional(rollbackFor = Exception.class)
    public StoreResponseDto createStore(@Valid StoreRequest request) {

        Store store = new Store(UUID.randomUUID(), request.getName(), request.getLocation(), null, request.getEmail());

        storeRepository.saveAndFlush(store);

        return storeMapper.mapToStoreResponseDto(store);

    }

    public void deleteStore(UUID storeId) {

        storeRepository.deleteById(storeId);

    }

    public StoreResponseDto findById(UUID storeId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow();

        return storeMapper.mapToStoreResponseDto(store);

    }

    public StoreResponseDto updateStoreById(UUID storeId, @Valid StoreRequest request) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow();

        store.setName(request.getName());
        store.setLocation(request.getLocation());

        storeRepository.saveAndFlush(store);

        return storeMapper.mapToStoreResponseDto(store);

    }
}
