package net.gentledot.rental.member.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.gentledot.rental.member.service.MemberService;
import net.gentledot.rental.vo.MemberVO;
import net.gentledot.utils.Pagination;
import net.gentledot.utils.Tools;

@Controller
public class MemberController {
	private static final Logger LOGGER = Logger.getLogger(MemberController.class);
	private static final int PAGE_SIZE = 10;
	
	@Resource(name="memberService")
	private MemberService memberService;
	
	@RequestMapping("/member/memberList.do")
	public String selectMemberList(@RequestParam HashMap<String, String> req, ModelMap model){
		LOGGER.debug("----------------------");
		LOGGER.debug("실행 확인 : 확인");
		LOGGER.debug("----------------------");
		
		int pageScope = 5;
		
		String category = Tools.toEmptyBlank(req.get("category"));
		String keyword = Tools.toEmptyBlank(req.get("keyword"));
		String getPageNo = Tools.customToEmptyBlank(req.get("pageNo"), "0");
		
		int pageNo = Integer.parseInt(getPageNo);
		
		Map<String, Object> resultMap = null;
		
		switch (category) {
		case "id":
			resultMap = memberService.getMemberList(keyword, PAGE_SIZE, pageNo, pageScope);
			break;
		case "name":
			resultMap = memberService.getMemberListByName(keyword, PAGE_SIZE, pageNo, pageScope);
		default:
			resultMap = resultMap = memberService.getMemberList(keyword, PAGE_SIZE, pageNo, pageScope);
			break;
		}
		
		List<MemberVO> resultList = (List<MemberVO>) resultMap.get("resultList");
		Pagination pag = (Pagination) resultMap.get("pagination");
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("pagination", pag);
		model.addAttribute("keyword", keyword);
		model.addAttribute("category", category);
		model.addAttribute("pageNo", pageNo);
		
		return "rental/member/list";
	}
	
	@RequestMapping("/member/addMember.do")
	public String addMemberInList(@RequestParam HashMap<String, String> req, ModelMap model){
		return "rental/member/insert";
	}
	
	
}
