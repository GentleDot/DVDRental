package net.gentledot.rental.vo;

/**
 * Created by gd on 2017-08-07.
 */
public class SalesVO extends PaginationVO {
    private String sDate;
    private String sCharge;
    private String sArrears;
    private String sPrice;
    private String sWasteCost;
    private String mId;
    private String stId;
    private String pId;

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getsCharge() {
        return sCharge;
    }

    public void setsCharge(String sCharge) {
        this.sCharge = sCharge;
    }

    public String getsArrears() {
        return sArrears;
    }

    public void setsArrears(String sArrears) {
        this.sArrears = sArrears;
    }

    public String getsPrice() {
        return sPrice;
    }

    public void setsPrice(String sPrice) {
        this.sPrice = sPrice;
    }

    public String getsWasteCost() {
        return sWasteCost;
    }

    public void setsWasteCost(String sWasteCost) {
        this.sWasteCost = sWasteCost;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
