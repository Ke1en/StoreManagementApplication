package java412.storemanagementapplication.service;

import jakarta.validation.Valid;
import java412.storemanagementapplication.dto.ProductResponseDto;
import java412.storemanagementapplication.entity.Product;
import java412.storemanagementapplication.entity.StoreProduct;
import java412.storemanagementapplication.mapper.ProductMapper;
import java412.storemanagementapplication.repository.ProductRepository;
import java412.storemanagementapplication.repository.StoreProductRepository;
import java412.storemanagementapplication.repository.StoreRepository;
import java412.storemanagementapplication.request.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Validated
@Transactional(readOnly = true)
public class ProductService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreProductRepository storeProductRepository;

    @Autowired
    private ProductMapper productMapper;

    @Transactional(rollbackFor = Exception.class)
    public ProductResponseDto createProduct(UUID storeId, @Valid ProductRequest request) {

        Product product = new Product(UUID.randomUUID(), request.getName(), request.getPrice(), request.getCategory());
        StoreProduct storeProduct = new StoreProduct(UUID.randomUUID(), storeId, product.getId());

        productRepository.saveAndFlush(product);
        storeProductRepository.saveAndFlush(storeProduct);

        return productMapper.mapToProductResponseDto(product);

    }

    public List<ProductResponseDto> findAllProductByLocation(String street) {
        return storeRepository.findAll().stream()
                .filter(store -> store.getLocation() != null && store.getLocation().contains(street))
                .flatMap(store -> storeProductRepository.findByStoreId(store.getId()).stream()
                        .map(storeProduct -> {
                            Product product = productRepository.findById(storeProduct.getProductId())
                                    .orElseThrow();

                            return productMapper.mapToProductResponseDto(product);
                        })
                )
                .distinct()
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> findUniqueProductsInAllStores() {
        return productRepository.findAll().stream()
                .filter(product -> storeRepository.countStoresByProductId(product.getId()) == 1)
                .map(productMapper::mapToProductResponseDto)
                .collect(Collectors.toList());
    }

}
