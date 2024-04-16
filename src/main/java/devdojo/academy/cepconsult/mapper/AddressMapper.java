package devdojo.academy.cepconsult.mapper;

import devdojo.academy.cepconsult.domain.Address;
import devdojo.academy.cepconsult.response.AddressGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
    AddressGetResponse toAddressGetResponse(Address address);

}
