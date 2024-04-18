package devdojo.academy.cepconsult.mapper;

import devdojo.academy.cepconsult.domain.Address;
import devdojo.academy.cepconsult.request.AddressPutRequest;
import devdojo.academy.cepconsult.response.AddressGetResponse;
import devdojo.academy.cepconsult.response.AddressGetResponseCep;
import devdojo.academy.cepconsult.response.AddressPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    List<AddressGetResponse> toAddressList(List<Address> addresses);

    AddressGetResponse toAddressGetResponse(Address address);

    AddressGetResponseCep toAddressGetResponseCep(Address address);

    AddressPostResponse addressPostResponse(Address address);

    Address toAddress(AddressPutRequest addressPutRequest);

}
