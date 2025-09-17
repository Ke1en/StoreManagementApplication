package java412.storemanagementapplication.service;

import jakarta.validation.ConstraintViolationException;
import java412.storemanagementapplication.TestContainerInitialization;
import java412.storemanagementapplication.dto.SupplierResponseDto;
import java412.storemanagementapplication.entity.Supplier;
import java412.storemanagementapplication.repository.SupplierRepository;
import java412.storemanagementapplication.request.SupplierRequest;
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

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SupplierServiceTest extends TestContainerInitialization {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierService supplierService;

    @AfterEach
    void clear() {
        supplierRepository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("invalidData")
    void createSupplier_whenRequestInvalid_thenThrowException(String name, String email, String phone, String address, String website) {

        SupplierRequest supplierRequest = createSupplierRequest(name, email, phone, address, website);

        Assertions.assertThrows(ConstraintViolationException.class, () -> supplierService.createSupplier(supplierRequest));

    }

    @ParameterizedTest
    @MethodSource("validData")
    void createSupplier_whenRequestValid_thenCreateSupplier(String name, String email, String phone, String address, String website) {

        SupplierRequest supplierRequest = createSupplierRequest(name, email, phone, address, website);

        SupplierResponseDto supplierResponseDto = Assertions.assertDoesNotThrow(() -> supplierService.createSupplier(supplierRequest));

        Assertions.assertEquals(supplierRequest.getName(), supplierResponseDto.getName());

    }

    @Test
    void findById_whenSupplierIdInvalid_thenThrowException() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> supplierService.findById(UUID.randomUUID()));
    }

    @Test
    void findById_whenSupplierIdValid_thenGetSupplier() {

        Supplier supplier = createSupplier("stringfind", "find@mail.ru", null, null, null);

        SupplierResponseDto supplierResponseDto = Assertions.assertDoesNotThrow(() -> supplierService.findById(supplier.getId()));

        Assertions.assertEquals(supplier.getName(), supplierResponseDto.getName());

    }

    @Test
    void updateById_whenSupplierIdInvalid_thenThrowException() {

        SupplierRequest supplierRequest = createSupplierRequest("stringfind2", "find2@mail.ru", null, null, null);

        Assertions.assertThrows(NoSuchElementException.class, () -> supplierService.updateById(UUID.randomUUID(), supplierRequest));

    }

    @ParameterizedTest
    @MethodSource("invalidData")
    void updateById_whenRequestInvalid_thenThrowException(String name, String email, String phone, String address, String website) {

        Supplier supplier = createSupplier("stringfind2", "find2@mail.ru", null, null, null);

        SupplierRequest supplierRequest = createSupplierRequest(name, email, phone, address, website);

        Assertions.assertThrows(ConstraintViolationException.class, () -> supplierService.updateById(supplier.getId(), supplierRequest));

    }

    @ParameterizedTest
    @MethodSource("validData")
    void updateById_whenRequestValid_thenUpdateSupplier(String name, String email, String phone, String address, String website) {

        Supplier supplier = createSupplier("stringfind2", "find2@mail.ru", null, null, null);

        SupplierRequest supplierRequest = createSupplierRequest(name, email, phone, address, website);

        SupplierResponseDto supplierResponseDto = Assertions.assertDoesNotThrow(() -> supplierService.updateById(supplier.getId(), supplierRequest));
        Assertions.assertEquals(supplierRequest.getName(), supplierResponseDto.getName());

    }

    @Test
    void deleteById_whenSupplierIdInvalid_thenThrowNoException() {
    //TODO допилить тест

    }

    private Supplier createSupplier(String name, String email, String phone, String address, String website) {

        Supplier supplier = new Supplier(UUID.randomUUID(), name, email, phone, address, website, null);
        supplierRepository.save(supplier);

        return supplier;

    }

    private SupplierRequest createSupplierRequest(String name, String email, String phone, String address, String website) {
        return new SupplierRequest(name, email, phone, address, website);
    }

    private Stream<Arguments> invalidData() {
        return Stream.of(
                Arguments.of("stringname", "", "", "", ""),
                Arguments.of("", "stringemail@mail.ru", "", "", ""),
                Arguments.of("stringname", "stringemail", "", "", ""),
                Arguments.of("stringname", "stringemail@mail.ru", "", "", "websiteNoUrl"),
                Arguments.of("", "", "", "", "")
        );
    }

    private Stream<Arguments> validData() {
        return Stream.of(
                Arguments.of("stringname", "stringemail@mail.ru", "", "", ""),
                Arguments.of("stringname", "stringemail@mail.ru", "stringphone", "", ""),
                Arguments.of("stringname", "stringemail@mail.ru", "", "stringaddress", ""),
                Arguments.of("stringname", "stringemail@mail.ru", "", "", "https://www.string-put.ru"),
                Arguments.of("stringname", "stringemail@mail.ru", "stringphone", "stringaddress", ""),
                Arguments.of("stringname", "stringemail@mail.ru", "stringphone", "", "https://www.string-put.ru"),
                Arguments.of("stringname", "stringemail@mail.ru", "", "stringaddress", "https://www.string-put.ru"),
                Arguments.of("stringname", "stringemail@mail.ru", "stringphone", "stringaddress", "https://www.string-put.ru")
        );
    }

}
