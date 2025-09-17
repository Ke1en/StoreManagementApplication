package java412.storemanagementapplication.service;

import java412.storemanagementapplication.TestContainerInitialization;
import java412.storemanagementapplication.dto.ProductResponseDto;
import java412.storemanagementapplication.entity.Product;
import java412.storemanagementapplication.entity.Store;
import java412.storemanagementapplication.entity.StoreProduct;
import java412.storemanagementapplication.repository.ProductRepository;
import java412.storemanagementapplication.repository.StoreProductRepository;
import java412.storemanagementapplication.repository.StoreRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductServiceTest extends TestContainerInitialization {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreProductRepository storeProductRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void clear() {

        storeRepository.deleteAll();
        storeProductRepository.deleteAll();

    }

    @Test
    void findAllProductByLocation_whenStoreDoesNotExist_thenReturnEmptyList() {

        List<ProductResponseDto> storeList = Assertions.assertDoesNotThrow(() -> productService.findAllProductByLocation("test"));

        Assertions.assertTrue(storeList.isEmpty());

    }

    @Test
    void findAllProductByLocation_whenStoreExistAndProductDoesnt_thenReturnEmptyList() {

        Store store = createStore("Test1", "ул. Ленина");

        createStoreProduct(store.getId(), UUID.randomUUID());

        Assertions.assertThrows(NoSuchElementException.class, () -> productService.findAllProductByLocation("ул. Ленина"));

    }

    @Test
    void findAllProductByLocation_whenStoreExistAndProductExist_thenReturnProductList() {

        Store store = createStore("Test1", "ул. Ленина");
        Store store2 = createStore("Test2", "ул. Ленина");

        Product product1 = createProduct("Лимонад", BigDecimal.valueOf(1.00), "Напитки");
        Product product2 = createProduct("Кола", BigDecimal.valueOf(2.00), "Напитки");
        Product product3 = createProduct("Квас", BigDecimal.valueOf(3.00), "Напитки");
        Product product4 = createProduct("Джин", BigDecimal.valueOf(4.00), "Напитки");

        createStoreProduct(store.getId(), product1.getId());
        createStoreProduct(store.getId(), product2.getId());
        createStoreProduct(store.getId(), product3.getId());
        createStoreProduct(store2.getId(), product3.getId());
        createStoreProduct(store2.getId(), product4.getId());

        List<ProductResponseDto> result = Assertions.assertDoesNotThrow(() -> productService.findAllProductByLocation("ул. Ленина"));

        Assertions.assertEquals(4, result.size());
        Assertions.assertEquals(product1.getId(), result.get(0).getId());

    }

    @Test
    void findUniqueProductsInAllStores_whenUniqueProductsExist_thenReturnUniqueProductList() {

        Store store = createStore("Test1", "ул. Ленина");
        Store store2 = createStore("Test2", "ул. Ленина");

        Product product1 = createProduct("Лимонад", BigDecimal.valueOf(1.00), "Напитки");
        Product product2 = createProduct("Кола", BigDecimal.valueOf(2.00), "Напитки");
        Product product3 = createProduct("Квас", BigDecimal.valueOf(3.00), "Напитки");

        createStoreProduct(store.getId(), product1.getId());
        createStoreProduct(store.getId(), product2.getId());
        createStoreProduct(store.getId(), product3.getId());
        createStoreProduct(store2.getId(), product2.getId());
        createStoreProduct(store2.getId(), product3.getId());

        List<ProductResponseDto> result = Assertions.assertDoesNotThrow(() -> productService.findUniqueProductsInAllStores());

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(product1.getName(), result.get(0).getName());

    }

    private StoreProduct createStoreProduct(UUID storeId, UUID productId) {

        StoreProduct storeProduct = new StoreProduct(UUID.randomUUID(), storeId, productId);
        storeProduct = storeProductRepository.save(storeProduct);

        return storeProduct;

    }

    private Product createProduct(String name, BigDecimal price, String category) {

        Product product = new Product(UUID.randomUUID(), name, price, category);
        product = productRepository.saveAndFlush(product);

        return product;

    }

    private Store createStore(String name, String location) {

        Store store = new Store(UUID.randomUUID(), name, location, null, null);
        store = storeRepository.saveAndFlush(store);

        return store;

    }

}
