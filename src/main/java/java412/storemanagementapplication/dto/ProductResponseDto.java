package java412.storemanagementapplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "DTO с основной информацией о товаре")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    @Schema(description = "Идентификатор товара")
    private UUID id;

    @Schema(description = "Наименование товара")
    private String name;

    @Schema(description = "Цена товара")
    private BigDecimal price;

    @Schema(description = "Категория товара")
    private String category;

}
