package shop.ninescent.mall.mypage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Date lastUsed;


}

