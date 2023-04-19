package com.example.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TbShoes implements Serializable {
    private String id;

    private String brandname;

    private String productid;

    private String title;

    private BigDecimal price;

    private String categoryid;

    private String spuid;

    private String spuminsaleprice;

    private Integer soldnum;

    private String images;

    private String brandlogourl;

    private String articlenumber;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getSpuid() {
        return spuid;
    }

    public void setSpuid(String spuid) {
        this.spuid = spuid;
    }

    public String getSpuminsaleprice() {
        return spuminsaleprice;
    }

    public void setSpuminsaleprice(String spuminsaleprice) {
        this.spuminsaleprice = spuminsaleprice;
    }

    public Integer getSoldnum() {
        return soldnum;
    }

    public void setSoldnum(Integer soldnum) {
        this.soldnum = soldnum;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getBrandlogourl() {
        return brandlogourl;
    }

    public void setBrandlogourl(String brandlogourl) {
        this.brandlogourl = brandlogourl;
    }

    public String getArticlenumber() {
        return articlenumber;
    }

    public void setArticlenumber(String articlenumber) {
        this.articlenumber = articlenumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", brandname=").append(brandname);
        sb.append(", productid=").append(productid);
        sb.append(", title=").append(title);
        sb.append(", price=").append(price);
        sb.append(", categoryid=").append(categoryid);
        sb.append(", spuid=").append(spuid);
        sb.append(", spuminsaleprice=").append(spuminsaleprice);
        sb.append(", soldnum=").append(soldnum);
        sb.append(", images=").append(images);
        sb.append(", brandlogourl=").append(brandlogourl);
        sb.append(", articlenumber=").append(articlenumber);
        sb.append("]");
        return sb.toString();
    }
}