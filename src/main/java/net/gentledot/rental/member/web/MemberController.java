package net.gentledot.rental.member.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
	private static final int PAGE_SIZE = 10;
	
	@Resource(name="memberService")
	private MemberService memberService;
	
	@RequestMapping("/member/memberList.do")
	public String selectMemberList(@RequestParam HashMap<String, String> req, ModelMap model){
		int pageScope = 5;
		
		String mId = Tools.toEmptyBlank(req.get("mId"));
		String getPageNo = Tools.customToEmptyBlank(req.get("pageNo"), "0");
		
		int pageNo = Integer.parseInt(getPageNo);
		
		Map<String, Object> resultMap = memberService.getMemberList(mId, PAGE_SIZE, pageNo, pageScope);
		
		List<MemberVO> resultList = (List<MemberVO>) resultMap.get("resultList");
		Pagination pag = (Pagination) resultMap.get("pagination");
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("pagination", pag);
		model.addAttribute("mId", mId);
		model.addAttribute("pageNo", pageNo);
		
		return "rental/member/list";
	}
	
}
