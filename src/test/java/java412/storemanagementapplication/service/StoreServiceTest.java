package java412.storemanagementapplication.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import java412.storemanagementapplication.TestContainerInitialization;
import java412.storemanagementapplication.dto.StoreResponseDto;
import java412.storemanagementapplication.entity.Store;
import java412.storemanagementapplication.repository.ProductRepository;
import java412.storemanagementapplication.repository.StoreProductRepository;
import java412.storemanagementapplication.repository.StoreRepository;
import java412.storemanagementapplication.request.StoreRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StoreServiceTest extends TestContainerInitialization {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreProductRepository storeProductRepository;

    @Autowired
    private StoreService storeService;
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void clear() {

        storeRepository.deleteAll();
        storeProductRepository.deleteAll();

    }

    @Test
    @Transactional
    void createStore_whenNameAndLocationNotEmpty_thenReturnStoreBody() { //TODO::сделать параметризированные тесты

        StoreRequest storeRequest = createStoreRequest("Test Test Test", "Test Street Test Street Test Street");

        StoreResponseDto testStore = storeService.createStore(storeRequest);

        assertThat(testStore).isNotNull();
        assertThat(testStore.getId()).isNotNull();
        assertThat(testStore.getName()).isEqualTo(storeRequest.getName());
        assertThat(testStore.getLocation()).isEqualTo(storeRequest.getLocation());

    }

    @Test
    void createStore_whenNameIsEmpty_thenThrowException() {

        StoreRequest storeRequest = createStoreRequest("", "Test Street Test Street Test Street");

        Assertions.assertThrows(ConstraintViolationException.class, () -> storeService.createStore(storeRequest));

    }

    @Test
    void createStore_whenLocationIsEmpty_thenThrowException() {

        StoreRequest storeRequest = createStoreRequest("Test Test Test", "");

        Assertions.assertThrows(ConstraintViolationException.class, () -> storeService.createStore(storeRequest));

    }

    @ParameterizedTest
    @MethodSource("invalidData")
    void updateStore_whenRequestInvalid_thenThrowException(String name, String location) {

        Store store = createStore("Пятерочка", "Ленина");

        StoreRequest storeRequest = createStoreRequest(name, location);

        Assertions.assertThrows(ConstraintViolationException.class, () -> storeService.updateStoreById(store.getId(), storeRequest));

    }

    @Test
    void updateStore_whenStoreNotFoundById_thenThrowException() {

        StoreRequest storeRequest = createStoreRequest("Яр", "Лермонтова");

        Assertions.assertThrows(NoSuchElementException.class, () -> storeService.updateStoreById(UUID.randomUUID(), storeRequest));

    }

    @Test
    void updateStore_whenStoreExist_thenUpdateStore() {

        Store store = createStore("Пятерочка", "Ленина");

        StoreRequest storeRequest = createStoreRequest("Яр", "Лермонтова");

        StoreResponseDto storeResponseDto = Assertions.assertDoesNotThrow(() -> storeService.updateStoreById(store.getId(), storeRequest));
        Assertions.assertEquals(storeRequest.getName(), storeResponseDto.getName());

    }

    @Test
    void deleteStore_whenStoreNotFoundById_thenThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.deleteStore(UUID.fromString("777777")));
    }

    @Test
    void deleteStore_whenStoreExist_thenDeleteStore() {

        Store store = createStore("Пятерочка", "Ленина");

        Assertions.assertDoesNotThrow(() -> storeService.deleteStore(store.getId()));

    }

    @Test
    void findById_whenStoreIdInvalid_thenThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> storeService.findById(UUID.fromString("777777")));
    }

    @Test
    void findById_whenStoreNotFoundById_thenThrowException() {
        Assertions.assertThrows(NoSuchElementException.class, () -> storeService.findById(UUID.randomUUID()));
    }

    @Test
    void findById_whenStoreExist_thenGetStore() {

        Store store = createStore("Пятерочка", "Ленина");

        StoreResponseDto storeResponseDto = Assertions.assertDoesNotThrow(() -> storeService.findById(store.getId()));

        Assertions.assertEquals(store.getName(), storeResponseDto.getName());

    }

    private Store createStore(String name, String location) {

        Store store = new Store(UUID.randomUUID(), name, location, null, null);
        store = storeRepository.saveAndFlush(store);

        return store;

    }

    private StoreRequest createStoreRequest(String name, String location) {
        return new StoreRequest(name, location, "1223123");
    }

    private Stream<Arguments> invalidData() {
        return Stream.of(
                Arguments.of("", "ул. Колокольчикова"),
                Arguments.of("Красновица", ""),
                Arguments.of("", "")
        );
    }

}
