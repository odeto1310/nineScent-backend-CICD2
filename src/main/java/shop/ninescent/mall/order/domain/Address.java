package shop.ninescent.mall.order.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "address")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addrNo;
    @Column(nullable = false)
    private Long userNo;
    @Column(nullable = false)
    private String addrName; // 예: "집", "회사"
    @Column(nullable = false)
    private String addrContact;
    @Column(nullable = false)
    private String addrZipcode;
    @Column(nullable = false)
    private String addrAddress;
    @Column(nullable = false)
    private String addrDetail;
    @Column(nullable = true)
    private String addrRequest; // 배송 요청 사항
    @Column(nullable = false)
    private Boolean isDefault; // 기본 주소 여부
    @Column(nullable = false)
    private Boolean isLiked; // 즐겨찾기 여부
    @Column(nullable = true)
    private LocalDateTime lastUsed; // 마지막 사용 시간
}
