package net.gentledot.rental.vo;

import org.springframework.stereotype.Repository;

public class TempVO {
	private String tempStr;
	private String tempNum;
	
	public String getTempStr() {
		return tempStr;
	}
	public void setTempStr(String tempStr) {
		this.tempStr = tempStr;
	}
	public String getTempNum() {
		return tempNum;
	}
	public void setTempNum(String tempNum) {
		this.tempNum = tempNum;
	}
}
