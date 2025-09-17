package java412.storemanagementapplication.mapper;

import java412.storemanagementapplication.dto.ProductResponseDto;
import java412.storemanagementapplication.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDto mapToProductResponseDto(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getCategory());
    }

}
