package net.gentledot.rental.vo;

import lombok.Data;

//@Data
public class MemberVO extends PaginationVO {
	private String mId;      
	private String mName;    
	private String mBirth;   
	private String mJoinDate;
	private String mAddr;    
	private String mPhone;   
	private String mMail;        
	private String mLimit;
	private String mOutDate;
	private String mStatus;
	
	
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getmBirth() {
		return mBirth;
	}
	public void setmBirth(String mBirth) {
		this.mBirth = mBirth;
	}
	public String getmJoinDate() {
		return mJoinDate;
	}
	public void setmJoinDate(String mJoinDate) {
		this.mJoinDate = mJoinDate;
	}
	public String getmAddr() {
		return mAddr;
	}
	public void setmAddr(String mAddr) {
		this.mAddr = mAddr;
	}
	public String getmPhone() {
		return mPhone;
	}
	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}
	public String getmMail() {
		return mMail;
	}
	public void setmMail(String mMail) {
		this.mMail = mMail;
	}
	public String getmLimit() {
		return mLimit;
	}
	public void setmLimit(String mLimit) {
		this.mLimit = mLimit;
	}
	public String getmOutDate() {
		return mOutDate;
	}
	public void setmOutDate(String mOutDate) {
		this.mOutDate = mOutDate;
	}
	public String getmStatus() {
		return mStatus;
	}
	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}
	
}                   
                                 
                                 
                                 
                                 
                                 
                                 
                                 
                                 
                                 
                                 