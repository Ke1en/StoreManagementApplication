package java412.storemanagementapplication.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "DTO запроса создания продукта")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @Schema(description = "Название продукта")
    @JsonProperty("name")
    @NotBlank
    private String name;

    @Schema(description = "Цена продукта")
    @JsonProperty("price")
    @NotNull
    private BigDecimal price;

    @Schema(description = "Категория продукта")
    @JsonProperty("category")
    @NotBlank
    private String category;

}
