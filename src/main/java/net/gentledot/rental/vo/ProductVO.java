package net.gentledot.rental.vo;

/**
 * Created by gd on 2017-07-26.
 */
public class ProductVO extends PaginationVO{
    private String pId;
    private String pName;
    private String pPrice;
    private String pGrade;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getpGrade() {
        return pGrade;
    }

    public void setpGrade(String pGrade) {
        this.pGrade = pGrade;
    }
}
