package net.gentledot.rental.rent.web;

import net.gentledot.rental.rent.service.RentService;
import net.gentledot.rental.storage.service.StorageService;
import net.gentledot.rental.vo.RentVO;
import net.gentledot.rental.vo.StorageVO;
import net.gentledot.utils.Pagination;
import net.gentledot.utils.Tools;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RentController {
	private static final Logger LOGGER = Logger.getLogger(RentController.class);
	private static final int PAGE_SIZE = 10;
	private static final int PAGE_SCOPE = 5;
	
	@Resource(name="rentService")
	private RentService rentService;

	@Resource(name="storageService")
	private StorageService storageService;
	
	@RequestMapping("/rent/rentList.do")
	public String selectRentList(@RequestParam HashMap<String, String> req, ModelMap model){
		LOGGER.debug("----------------------");
		LOGGER.debug("검색 설정 : " + req.get("category"));
		LOGGER.debug("검색 키워드 : " + req.get("keyword"));
		LOGGER.debug("----------------------");
		
		String category = Tools.toEmptyBlank(req.get("category"));
		String keyword = Tools.toEmptyBlank(req.get("keyword"));
		String getPageNo = Tools.customToEmptyBlank(req.get("pageNo"), "1");
		
		int pageNo = Integer.parseInt(getPageNo);
		
		Map<String, Object> resultMap = null;
		
		switch (category) {
		case "date":
			resultMap = rentService.getRentListByRentdate(keyword, PAGE_SIZE, pageNo, PAGE_SCOPE);
			break;
		case "item":
			resultMap = rentService.getRentListByItem(keyword, PAGE_SIZE, pageNo, PAGE_SCOPE);
			break;
		default:
			resultMap = rentService.getRentListByMember(keyword, PAGE_SIZE, pageNo, PAGE_SCOPE);
			break;
		}
		
		List<RentVO> resultList = (List<RentVO>) resultMap.get("resultList");

		LOGGER.debug("----------------------");
		LOGGER.debug("resultMap 존재 여부 : " +  resultMap.containsKey("resultList"));
		LOGGER.debug("결과 리스트 크기 : " +  resultList.size());
		LOGGER.debug("----------------------");

		Pagination pag = (Pagination) resultMap.get("pagination");
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("pagination", pag);
		model.addAttribute("keyword", keyword);
		model.addAttribute("category", category);
		model.addAttribute("pageNo", pageNo);
		
		return "rental/rent/list";
	}
	
	@RequestMapping("/rent/addRentView.do")
	public String addRentView(@RequestParam HashMap<String, String> req, ModelMap model){
		String mId = Tools.customToEmptyBlank(req.get("mId"), "");

		List<StorageVO> itemList = storageService.totalStorageItems();

		model.addAttribute("mId", mId);
		model.addAttribute("itemList", itemList);

		return "rental/rent/insert";
	}

	@RequestMapping("/rent/addRent.do")
	public String addRentInList(@RequestParam HashMap<String, String> req, ModelMap model){

		String mId = Tools.customToEmptyBlank(req.get("inputRmId"),"");
		String rentdate = Tools.customToEmptyBlank(req.get("inputRrentdate"),"");
		String stId = Tools.customToEmptyBlank(req.get("inputRstId"),"");
		String rentPeriod = Tools.customToEmptyBlank(req.get("inputRrentPeriod"),"");
		String charge = Tools.customToEmptyBlank(req.get("inputRcharge"),"");

		RentVO vo = new RentVO();
		vo.setmId(mId);
		vo.setrRentdate(rentdate);
		vo.setStId(stId);
		vo.setrRentperiod(rentPeriod);
		vo.setrCharge(charge);
		
		int resultStatus = rentService.addRent(vo);
		
		return "redirect:/rent/rentList.do";
	}


	@RequestMapping("/rent/rentInfo.do")
	public String selectOneOfRent(@RequestParam HashMap<String, String> req, ModelMap model){
		String mId = Tools.customToEmptyBlank(req.get("mId"), "");
		String rentdate = Tools.customToEmptyBlank(req.get("rentdate"), "");
		String stId = Tools.customToEmptyBlank(req.get("stId"), "");

		RentVO vo = new RentVO();
		vo.setmId(mId);
		vo.setrRentdate(rentdate);
		vo.setStId(stId);
		
		RentVO selectedRent = rentService.checkRentDetail(vo);
		
		model.addAttribute("details", selectedRent);
		
		return "rental/rent/info";
	}


	@RequestMapping("/rent/rentModifyView.do")
	public String updateRentInfoView(@RequestParam HashMap<String, String> req, ModelMap model){
		String mId = Tools.customToEmptyBlank(req.get("mId"), "");
		String rentdate = Tools.customToEmptyBlank(req.get("rentdate"), "");
		String stId = Tools.customToEmptyBlank(req.get("stId"), "");

		RentVO vo = new RentVO();
		vo.setmId(mId);
		vo.setrRentdate(rentdate);
		vo.setStId(stId);

		RentVO selectedRent = rentService.checkRentDetail(vo);

		model.addAttribute("details", selectedRent);
		
		return "rental/rent/modify";
	}
	
	@RequestMapping("/rent/rentModify.do")
	public String updateRentInfo(@RequestParam HashMap<String, String> req, ModelMap model){

		String mId = Tools.customToEmptyBlank(req.get("getRmId"), "");
		String rentdate = Tools.customToEmptyBlank(req.get("getRrentdate"), "");
		String stId = Tools.customToEmptyBlank(req.get("getRstId"), "");
		String returndate = Tools.customToEmptyBlank(req.get("getRreturndate"), "");
		String returnStatus = Tools.customToEmptyBlank(req.get("getRreturnStatus"), "");
		String arrears = Tools.customToEmptyBlank(req.get("getRarrears"), "");
		String arrearsClear = Tools.customToEmptyBlank(req.get("getRarrearsClear"), "");

		RentVO vo = new RentVO();
		vo.setmId(mId);
		vo.setrRentdate(rentdate);
		vo.setStId(stId);
		vo.setrReturndate(returndate);
		vo.setrReturnStatus(returnStatus);
		vo.setrArrears(arrears);
		vo.setrArrearsClear(arrearsClear);
		
		int resultStatus = rentService.updateRent(vo);
		
		return "redirect:/rent/rentList.do";
	}
	
	@RequestMapping("/rent/rentDel.do")
	public String delRent(@RequestParam HashMap<String, String> req, ModelMap model){
		String mId = Tools.customToEmptyBlank(req.get("mId"), "");
		String rentdate = Tools.customToEmptyBlank(req.get("rentdate"), "");
		String stId = Tools.customToEmptyBlank(req.get("stId"), "");

		RentVO vo = new RentVO();
		vo.setmId(mId);
		vo.setrRentdate(rentdate);
		vo.setStId(stId);

		int resultStatus = rentService.delRent(vo);

		return "redirect:/rent/rentList.do";
	}

}
