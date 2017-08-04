package net.gentledot.rental.rent.web;

import net.gentledot.rental.rent.service.RentService;
import net.gentledot.rental.vo.RentVO;
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
	
	@RequestMapping("/rent/rentList.do")
	public String selectRentList(@RequestParam HashMap<String, String> req, ModelMap model){
		LOGGER.debug("----------------------");
		LOGGER.debug("검색 설정 : " + req.get("category"));
		LOGGER.debug("검색 키워드 : " + req.get("keyword"));
		LOGGER.debug("----------------------");
		
		String category = Tools.toEmptyBlank(req.get("category"));
		String keyword = Tools.toEmptyBlank(req.get("keyword"));
		String getPageNo = Tools.customToEmptyBlank(req.get("pageNo"), "0");
		
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
			resultMap = resultMap = rentService.getRentListByMember(keyword, PAGE_SIZE, pageNo, PAGE_SCOPE);
			break;
		}
		
		List<RentVO> resultList = (List<RentVO>) resultMap.get("resultList");
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
		return "rental/rent/insert";
	}
/*
	@RequestMapping("/rent/addRent.do")
	public String addRentInList(@RequestParam HashMap<String, String> req, ModelMap model){
		LOGGER.debug("====================");
		LOGGER.debug("전달받은 생일 : " + req.get("inputMBirth"));
		LOGGER.debug("전달받은 전화번호 : " + req.get("inputMPhone"));
		LOGGER.debug("====================");
		
		
		String mName = Tools.customToEmptyBlank(req.get("inputMName"),"");
		String mBirthStr = Tools.customToEmptyBlank(req.get("inputMBirth"),"9999-99-99");
		String mJoinDate = Tools.customToEmptyBlank(req.get("inputMJoinDate"),"");
		String mAddr = Tools.customToEmptyBlank(req.get("inputMAddr"),"");
		String mPhone = Tools.customToEmptyBlank(req.get("inputMPhone"),"");
		String mMail = Tools.customToEmptyBlank(req.get("inputMMail"),"");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		Date tempBirth = null;
		try {
			tempBirth = sdf.parse(mBirthStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String mBirth = sdf2.format(tempBirth);
		
		RentVO vo = new RentVO();
		vo.setmName(mName);
		vo.setmBirth(mBirth);
		vo.setmJoinDate(mJoinDate);
		vo.setmAddr(mAddr);
		vo.setmPhone(mPhone);
		vo.setmMail(mMail);
		
		int resultStatus = rentService.addRent(vo);
		
		return "redirect:/rent/rentList.do";
	}
	
	@RequestMapping("/rent/rentInfo.do")
	public String selectOneOfRent(@RequestParam HashMap<String, String> req, ModelMap model){
		String mId = Tools.customToEmptyBlank(req.get("mId"), "");
		RentVO vo = new RentVO();
		vo.setmId(mId);
		
		RentVO selectedRent = rentService.selectOneOfRent(vo);
		
		model.addAttribute("oneOfRent", selectedRent);
		
		return "rental/rent/info";
	}
	
	@RequestMapping("/rent/rentModifyView.do")
	public String updateRentInfoView(@RequestParam HashMap<String, String> req, ModelMap model){
		String mId = Tools.customToEmptyBlank(req.get("mId"), "");
		
		RentVO vo = new RentVO();
		vo.setmId(mId);
		
		RentVO selectedRent = rentService.selectOneOfRent(vo);
		
		model.addAttribute("oneOfRent", selectedRent);
		
		return "rental/rent/modify";
	}
	
	@RequestMapping("/rent/rentModify.do")
	public String updateRentInfo(@RequestParam HashMap<String, String> req, ModelMap model){
		
		LOGGER.debug("======================");
		LOGGER.debug("넘어온 phone 값 : " + req.get("getMPhone"));
		LOGGER.debug("======================");
		
		String mId = Tools.customToEmptyBlank(req.get("getMId"),"");
		String mName = Tools.customToEmptyBlank(req.get("getMName"),"");
		String mBirthStr = Tools.customToEmptyBlank(req.get("getMBirth"),"9999-99-99");
		String mJoinDateStr = Tools.customToEmptyBlank(req.get("getMJoinDate"),"9999-99-99");
		String mAddr = Tools.customToEmptyBlank(req.get("getMAddr"),"");
		String mPhone = Tools.customToEmptyBlank(req.get("getMPhone"),"");
		String mMail = Tools.customToEmptyBlank(req.get("getMMail"),"");
		String mLimit = Tools.customToEmptyBlank(req.get("getMLimit"),"5");
		String mStatusStr = Tools.customToEmptyBlank(req.get("getMStatus"),"회원");
		String mOutdateStr = Tools.customToEmptyBlank(req.get("getMOutdate"),"");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		Date tempBirth= null;
		Date tempJoindate= null;
		try {
			tempBirth = sdf.parse(mBirthStr);
			tempJoindate = sdf.parse(mJoinDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String mBirth = sdf2.format(tempBirth);
		String mJoindate = sdf2.format(tempJoindate);
		
		
		String mOutdate = "";
		if (!mOutdateStr.equals("")){
			Date tempOutdate = null;
			try {
				tempOutdate = sdf.parse(mOutdateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mOutdate = sdf2.format(tempOutdate);
		}else{
			mOutdate = mOutdateStr;
		}
		
		String mStatus =  mStatusStr.equals("회원") ? "M" : "O";
		
		
		/*RentVO vo = new RentVO();
		vo.setmId(mId);
		vo.setmName(mName);
		vo.setmBirth(mBirth);
		vo.setmJoinDate(mJoindate);
		vo.setmAddr(mAddr);
		vo.setmPhone(mPhone);
		vo.setmMail(mMail);
		vo.setmLimit(mLimit);
		vo.setmStatus(mStatus);
		vo.setmOutDate(mOutdate);*/
		
		/*int resultStatus = rentService.updateRent(vo);
		
		return "redirect:/rent/rentList.do";
	}
	
	@RequestMapping("/rent/rentDel.do")
	public String delRent(@RequestParam HashMap<String, String> req, ModelMap model){
		return "redirect:/rent/rentList.do";
	}*/

}
