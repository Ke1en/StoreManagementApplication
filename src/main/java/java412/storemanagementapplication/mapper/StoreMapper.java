package java412.storemanagementapplication.mapper;

import java412.storemanagementapplication.dto.AllStoresResponseDto;
import java412.storemanagementapplication.dto.StoreResponseDto;
import java412.storemanagementapplication.entity.Store;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {

    public StoreResponseDto mapToStoreResponseDto(Store store) {
        return new StoreResponseDto(store.getId(), store.getName(), store.getLocation(), store.getEmail());
    }

    public AllStoresResponseDto mapToAllStoresResponseDto(Store store) {
        return new AllStoresResponseDto(store.getId(), store.getName(), store.getLocation());
    }



}
