package net.gentledot.rental.sales.service.impl;

import net.gentledot.ids.service.IdsService;
import net.gentledot.rental.persistance.SalesDAO;
import net.gentledot.rental.persistance.SalesDAO;
import net.gentledot.rental.sales.service.SalesService;
import net.gentledot.rental.vo.SalesVO;
import net.gentledot.utils.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("salesService")
public class SalesServiceImpl implements SalesService {
	
	@Resource(name="salesDao")
	SalesDAO dao;

	public Map<String, Object> getSalesList(String sDate, int pageSize, int pageNo, int pageScope){
		int totalCnt = dao.totalCountOfSalesList();

		// list 생성
		SalesVO vo = new SalesVO();
		vo.setsDate(sDate);
		vo.setPageNo(pageNo);
		vo.setPageSize(totalCnt);

		List<SalesVO> returnList = dao.selectSalesList(vo);

		// pagination 생성
		Pagination pag = new Pagination(totalCnt, pageNo, pageScope, totalCnt);
		pag.setPaging();
		
		// return 할 map에 list와 pagination 담기
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("resultList", returnList);
		resultMap.put("pagination", pag);
		
		return resultMap;
	}

	public int addGetCharge(SalesVO vo){
		return dao.addGetCharge(vo);
	}
	public int addGetArrears(SalesVO vo){
		return dao.addGetArrears(vo);
	}
	public int addPutPrice(SalesVO vo){
		return dao.addPutPrice(vo);
	}
	public int addPutWastecost(SalesVO vo){
		return dao.addPutWastecost(vo);
	}
	
}
