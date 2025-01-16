package shop.ninescent.mall.mypage.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AddressDto {
    private Long addrNo;
    private Long userNo;
    private String addrName;
    private String addrContact;
    private int addrZipcode;
    private String addrAddress;
    private String addrDetail;
    private String addrRequest;
    private Boolean isDefault;
    private Boolean isLiked;

    // Getters and Setters
}
