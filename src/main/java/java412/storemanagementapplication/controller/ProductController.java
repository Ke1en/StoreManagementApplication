package java412.storemanagementapplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java412.storemanagementapplication.dto.ProductResponseDto;
import java412.storemanagementapplication.request.ProductRequest;
import java412.storemanagementapplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/{storeId}")
    @Operation(summary = "Добавить продукт в указанный магазин",
            description = "Добавление продукта в магазин по id")
    public ResponseEntity<ProductResponseDto> createProduct(@PathVariable UUID storeId, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(storeId, request));
    }

    @GetMapping("/by-location")
    @Operation(summary = "Найти товары во всех магазинах на указанной улице",
            description = "Все товары в магазинах на указанной улице")
    public ResponseEntity<List<ProductResponseDto>> findAllProductByLocation(
            @Parameter(description = "Название улицы") @RequestParam String location
    ) {

        List<ProductResponseDto> allProducts = productService.findAllProductByLocation(location);

        return ResponseEntity.ok(allProducts);

    }

    @GetMapping("/unique")
    @Operation(summary = "Найти уникальные товары",
            description = "Товары, которые продаются только в одном магазине")
    public ResponseEntity<List<ProductResponseDto>> findUniqueProducts() {
        return ResponseEntity.ok(productService.findUniqueProductsInAllStores());
    }

}
