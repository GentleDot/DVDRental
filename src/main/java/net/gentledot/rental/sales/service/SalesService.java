package net.gentledot.rental.sales.service;

import net.gentledot.rental.vo.MemberVO;
import net.gentledot.rental.vo.SalesVO;

import java.util.Map;

public interface SalesService {
    public Map<String, Object> getSalesList(String sDate, int pageSize, int pageNo, int pageScope);
    public int addGetCharge(SalesVO vo);
    public int addGetArrears(SalesVO vo);
    public int addPutPrice(SalesVO vo);
    public int addPutWastecost(SalesVO vo);

}
