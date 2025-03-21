package shop.ninescent.mall.item.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import shop.ninescent.mall.item.domain.Item;

public class ItemDTO {

    private Long itemId;              // 상품 ID
    private Long categoryId;       // 카테고리 ID
    private Long subCategoryId;       // 서브 카테고리 ID
    private String itemName;          // 상품 이름
    private String itemSize;          // 상품 크기
    private String itemTitle;
    private String itemDesc;          // 상품 설명

    private BigDecimal price;         // 상품 가격
    private Integer discountRate;     // 할인율
    private BigDecimal discountedPrice; // 할인된 가격
    private LocalDate discountStart;  // 할인 시작일
    private LocalDate discountEnd;    // 할인 종료일
    private String discountDesc;      // 할인 설명
    private Integer stock;            // 재고
    private String photo;             // 사진 URL
    private String detail;            // 상세 설명 사진

    // 기본 생성자
    public ItemDTO() {
    }

    // 모든 필드 생성자
    public ItemDTO(Long itemId, Long subCategoryId, String itemName, Long categoryId, String itemSize, String itemTitle,String itemDesc,
                   BigDecimal price, Integer discountRate, BigDecimal discountedPrice, LocalDate discountStart,
                   LocalDate discountEnd, String discountDesc, Integer stock, String photo, String detail) {
        this.itemId = itemId;
        this.subCategoryId = subCategoryId;
        this.itemName = itemName;
        this.categoryId = categoryId;
        this.itemSize = itemSize;
        this.itemTitle = itemTitle;
        this.itemDesc = itemDesc;
        this.price = price;
        this.discountRate = discountRate;
        this.discountedPrice = discountedPrice;
        this.discountStart = discountStart;
        this.discountEnd = discountEnd;
        this.discountDesc = discountDesc;
        this.stock = stock;
        this.photo = photo;
        this.detail = detail;
    }

    // Getters and Setters
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getItemTitle() {return itemTitle;}

    public void setItemTitle(String itemTitle) {this.itemTitle = itemTitle;}

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Integer discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public LocalDate getDiscountStart() {
        return discountStart;
    }

    public void setDiscountStart(LocalDate discountStart) {
        this.discountStart = discountStart;
    }

    public LocalDate getDiscountEnd() {
        return discountEnd;
    }

    public void setDiscountEnd(LocalDate discountEnd) {
        this.discountEnd = discountEnd;
    }

    public String getDiscountDesc() {
        return discountDesc;
    }

    public void setDiscountDesc(String discountDesc) {
        this.discountDesc = discountDesc;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "itemId=" + itemId +
                ", subCategoryId=" + subCategoryId +
                ", itemName='" + itemName + '\'' +
                ", categoryId=" + categoryId +
                ", itemSize='" + itemSize + '\'' +
                ", itemTitle='" + itemTitle + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", price=" + price +
                ", discountRate=" + discountRate +
                ", discountedPrice=" + discountedPrice +
                ", discountStart=" + discountStart +
                ", discountEnd=" + discountEnd +
                ", discountDesc='" + discountDesc + '\'' +
                ", stock=" + stock +
                ", photo='" + photo + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }

    //itemDTOdㅔ서 Item 엔터티 변환
    public Item toEntity() {
        Item item = new Item();
        item.setItemId(this.itemId);
        item.setCategoryId(this.categoryId);
        item.setSubCategoryId(this.subCategoryId);
        item.setItemName(this.itemName);

        item.setItemSize(this.itemSize);
        item.setItemTitle(this.itemTitle);
        item.setItemDescription(this.itemDesc);
        item.setPrice(this.price);
        item.setDiscountRate(this.discountRate);
        item.setDiscountedPrice(this.discountedPrice);
        item.setDiscountStart(this.discountStart);
        item.setDiscountEnd(this.discountEnd);
        item.setDiscountDescription(this.discountDesc);
        item.setStock(this.stock);
        item.setMainPhoto(this.photo);
        item.setDetailPhotos(Collections.singletonList(this.detail));
        return item;
    }



}