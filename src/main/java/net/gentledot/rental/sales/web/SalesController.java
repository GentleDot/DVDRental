package net.gentledot.rental.sales.web;

import net.gentledot.rental.rent.service.RentService;
import net.gentledot.rental.sales.service.SalesService;
import net.gentledot.rental.vo.RentVO;
import net.gentledot.rental.vo.SalesVO;
import net.gentledot.utils.Pagination;
import net.gentledot.utils.Tools;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class SalesController {
	private static final Logger LOGGER = Logger.getLogger(SalesController.class);
	private static final int PAGE_SIZE = 10;
	private static final int PAGE_SCOPE = 5;
	
	@Resource(name="salesService")
	private SalesService salesService;

	@Resource(name="rentService")
	private RentService rentService;
	
	@RequestMapping("/sales/salesList.do")
	public String selectSalesList(@RequestParam HashMap<String, String> req, ModelMap model){
		LOGGER.debug("----------------------");
		LOGGER.debug("선택한 년도 : " + req.get("selectedYear"));
		LOGGER.debug("선택한 월 : " + req.get("selectedMonth"));
		LOGGER.debug("----------------------");

		NumberFormat nf = new DecimalFormat("00");

		Calendar calendar = Calendar.getInstance();
		String curYear = String.valueOf(calendar.get(Calendar.YEAR));
		String curMonth = String.valueOf(nf.format(calendar.get(Calendar.MONTH) + 1));

		String selectedYear = Tools.customToEmptyBlank(req.get("selectedYear"), curYear);

		String selectedMonth = Tools.customToEmptyBlank(req.get("selectedMonth"), curMonth);

		StringBuilder sb = new StringBuilder();
		sb.append(selectedYear);
		sb.append(selectedMonth);
		String sDate = sb.toString();

		String getPageNo = Tools.customToEmptyBlank(req.get("pageNo"), "0");
		
		int pageNo = Integer.parseInt(getPageNo);
		
		Map<String, Object> resultMap = salesService.getSalesList(sDate, PAGE_SIZE, pageNo, PAGE_SCOPE);
		
		List<SalesVO> resultList = (List<SalesVO>) resultMap.get("resultList");
		Pagination pag = (Pagination) resultMap.get("pagination");
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("pagination", pag);
		model.addAttribute("selectedYear", selectedYear);
		model.addAttribute("selectedMonth", selectedMonth);
		model.addAttribute("pageNo", pageNo);
		
		return "rental/sales/list";
	}

	@RequestMapping("/sales/rentStatusOfMember.do")
	public String chkRentDetailOfMember(@RequestParam HashMap<String, String> req, ModelMap model){
		LOGGER.debug("===============");
		LOGGER.debug("받은 id : " + req.get("mId"));
		LOGGER.debug("받은 날짜 : " + req.get("rentdate"));
		LOGGER.debug("===============");

		Calendar calendar = Calendar.getInstance();
		NumberFormat nf = new DecimalFormat("00");

		// 현재 년도와 월로 구성
		String curDate =calendar.get(Calendar.YEAR) + nf.format(calendar.get(Calendar.MONTH) + 1) + "";

		String mId = Tools.customToEmptyBlank(req.get("mId"), "");
		String selectedDate = Tools.customToEmptyBlank(req.get("rentdate"), curDate);

		RentVO vo = new RentVO();
		vo.setmId(mId);
		vo.setrRentdate(selectedDate);

		List<RentVO> rentList = rentService.checkRentDetailOfMember(vo);

		LOGGER.debug("=======================");
		LOGGER.debug("rentListSize = " + rentList.size());
		LOGGER.debug("=======================");

		model.addAttribute("resultList", rentList);
		model.addAttribute("selectedDate", selectedDate);

		return "rental/sales/statusMember";
	}

	@RequestMapping("/sales/rentStatusOfGoods.do")
	public String chkRentDetailOfGoods(@RequestParam HashMap<String, String> req, ModelMap model){
		Calendar calendar = Calendar.getInstance();
		NumberFormat nf = new DecimalFormat("00");

		// 현재 년도와 월로 구성
		String curDate =calendar.get(Calendar.YEAR) + nf.format(calendar.get(Calendar.MONTH) + 1) + "";

		String stId = Tools.customToEmptyBlank(req.get("stId"), "");
		String selectedDate = Tools.customToEmptyBlank(req.get("rentdate"), curDate);

		RentVO vo = new RentVO();
		vo.setStId(stId);
		vo.setrRentdate(selectedDate);

		List<RentVO> rentList = rentService.checkRentDetailOfGoods(vo);

		model.addAttribute("resultList", rentList);
		model.addAttribute("selectedDate", selectedDate);

		return "rental/sales/statusGoods";
	}

}
