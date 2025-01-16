package shop.ninescent.mall.address.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private String addrName;
    private String addrContact;
    private Long addrZipcode;
    private String addrAddress;
    private String addrDetail;
    private String addrRequest;
}
