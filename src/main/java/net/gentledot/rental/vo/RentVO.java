package net.gentledot.rental.vo;

/**
 * Created by gd on 2017-08-03.
 */
public class RentVO extends PaginationVO {
    private String mId;
    private String rRentdate;
    private String stId;
    private String rRentperiod;
    private String rCharge;
    private String rReturndate;
    private String rReturnStatus;
    private String rArrears;
    private String rArrearsClear;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getrRentdate() {
        return rRentdate;
    }

    public void setrRentdate(String rRentdate) {
        this.rRentdate = rRentdate;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getrRentperiod() {
        return rRentperiod;
    }

    public void setrRentperiod(String rRentperiod) {
        this.rRentperiod = rRentperiod;
    }

    public String getrCharge() {
        return rCharge;
    }

    public void setrCharge(String rCharge) {
        this.rCharge = rCharge;
    }

    public String getrReturndate() {
        return rReturndate;
    }

    public void setrReturndate(String rReturndate) {
        this.rReturndate = rReturndate;
    }

    public String getrReturnStatus() {
        return rReturnStatus;
    }

    public void setrReturnStatus(String rReturnStatus) {
        this.rReturnStatus = rReturnStatus;
    }

    public String getrArrears() {
        return rArrears;
    }

    public void setrArrears(String rArrears) {
        this.rArrears = rArrears;
    }

    public String getrArrearsClear() {
        return rArrearsClear;
    }

    public void setrArrearsClear(String rArrearsClear) {
        this.rArrearsClear = rArrearsClear;
    }
}
