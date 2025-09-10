package java412.storemanagementapplication.service;

import jakarta.validation.Valid;
import java412.storemanagementapplication.dto.AllStoresResponseDto;
import java412.storemanagementapplication.dto.ProductResponseDto;
import java412.storemanagementapplication.dto.StoreResponseDto;
import java412.storemanagementapplication.entity.Product;
import java412.storemanagementapplication.entity.Store;
import java412.storemanagementapplication.entity.StoreProduct;
import java412.storemanagementapplication.mapper.StoreMapper;
import java412.storemanagementapplication.repository.ProductRepository;
import java412.storemanagementapplication.repository.StoreProductRepository;
import java412.storemanagementapplication.repository.StoreRepository;
import java412.storemanagementapplication.request.StoreRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Validated
@Transactional(readOnly = true)
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;
    
    @Autowired
    private StoreProductRepository storeProductRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreMapper storeMapper;

    @Transactional(rollbackFor = Exception.class)
    public StoreResponseDto createStore(@Valid StoreRequest request) {

        Store store = new Store(UUID.randomUUID(), request.getName(), request.getLocation(), null, request.getEmail());

        storeRepository.saveAndFlush(store);

        return storeMapper.mapToStoreResponseDto(store);

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteStore(UUID storeId) {
        storeRepository.deleteById(storeId);
    }

    public StoreResponseDto findById(UUID storeId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow();

        return storeMapper.mapToStoreResponseDto(store);

    }

    @Transactional(rollbackFor = Exception.class)
    public StoreResponseDto updateStoreById(UUID storeId, @Valid StoreRequest request) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow();

        store.setName(request.getName());
        store.setLocation(request.getLocation());

        storeRepository.saveAndFlush(store);

        return storeMapper.mapToStoreResponseDto(store);

    }

    public List<AllStoresResponseDto> findAllStores() {

        List<Store> stores = storeRepository.findAll();

        return stores.stream()
                .map(storeMapper::mapToAllStoresResponseDto)
                .toList();

    }

    public List<AllStoresResponseDto> findAllStoresByLocation(String location) {

        List<Store> stores = storeRepository.findByLocation(location);

        return stores.stream()
                .map(storeMapper::mapToAllStoresResponseDto)
                .toList();

    }

    public List<AllStoresResponseDto> findAllStoresByName() {

        List<Store> stores = storeRepository.findAll(Sort.by(Sort.Order.asc("name")));

        return stores.stream()
                .map(storeMapper::mapToAllStoresResponseDto)
                .toList();

    }

    @Transactional(rollbackFor = Exception.class)
    public StoreResponseDto copy(UUID storeId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow();

        Store copyStore = new Store(UUID.randomUUID(), store.getName(), store.getLocation(), store.getUpdatedAt(), store.getEmail());

        storeRepository.saveAndFlush(copyStore);

        return storeMapper.mapToStoreResponseDto(copyStore);

    }

    public List<ProductResponseDto> findAllProductByLocation(String street) {
        
        // Шаг 1, получаем все магазины
        List<Store> allStores = storeRepository.findAll();
        // Шаг 2, фильтруем магазины по указанной улице
        List<Store> storesOnStreet = new ArrayList<>();
        
        for (Store store : allStores) {
            if (store.getLocation().contains(street) && store.getLocation() != null) {
                storesOnStreet.add(store);
            }
        }
        
        List<ProductResponseDto> result = new ArrayList<>();
        
        // Шаг 3, собираем все товары из найденных магазинов
        for (Store store : storesOnStreet) {
            
            List<StoreProduct> storeProduct = storeProductRepository.findByStoreId(store.getId());
            
            for (StoreProduct sp : storeProduct) {
                
                Product product = productRepository.findById(sp.getProductId())
                        .orElseThrow();

                ProductResponseDto productResponseDto = storeMapper.mapToProductResponseDto(product);

                result.add(productResponseDto);

            }

        }

        return result.stream()
                .distinct()
                .toList();

    }

}
