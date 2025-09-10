package java412.storemanagementapplication.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Schema(description = "DTO запроса данных поставщика")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierContactRequest {

    @Schema(description = "Номер телефона поставщика")
    @JsonProperty("phone")
    private String phone;

    @Schema(description = "Электронная почта поставщика")
    @JsonProperty("email")
    @NotBlank
    @Email(message = "Электронная почта должна быть валидной")
    private String email;

    @Schema(description = "Вебсайт(URL) поставщика")
    @JsonProperty("website")
    @URL(message = "Адрес вебсайта должен быть валидным")
    private String website;

}
