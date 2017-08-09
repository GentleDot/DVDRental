package net.gentledot.rental.member.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		LOGGER.debug("검색 설정 : " + req.get("category"));
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
			break;
		default:
			resultMap = memberService.getMemberList(keyword, PAGE_SIZE, pageNo, pageScope);
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
	
	@RequestMapping("/member/addMemberView.do")
	public String addMemberView(@RequestParam HashMap<String, String> req, ModelMap model){
		
		return "rental/member/insert";
	}
	
	@RequestMapping("/member/addMember.do")
	public String addMemberInList(@RequestParam HashMap<String, String> req, ModelMap model){
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

		if(mName.equals("") || mBirthStr.equals("") || mAddr.equals("") || mMail.equals("") ){
			return "redirect:/member/addMemberView.do";
		}
		
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
		
		MemberVO vo = new MemberVO();
		vo.setmName(mName);
		vo.setmBirth(mBirth);
		vo.setmJoinDate(mJoinDate);
		vo.setmAddr(mAddr);
		vo.setmPhone(mPhone);
		vo.setmMail(mMail);
		
		int resultStatus = memberService.addMember(vo);
		
		return "redirect:/member/memberList.do";
	}
	
	@RequestMapping("/member/memberInfo.do")
	public String selectOneOfMember(@RequestParam HashMap<String, String> req, ModelMap model){
		String mId = Tools.customToEmptyBlank(req.get("mId"), "");
		MemberVO vo = new MemberVO();
		vo.setmId(mId);
		
		MemberVO selectedMember = memberService.selectOneOfMember(vo);
		
		model.addAttribute("oneOfMember", selectedMember);
		
		return "rental/member/info";
	}
	
	@RequestMapping("/member/memberModifyView.do")
	public String updateMemberInfoView(@RequestParam HashMap<String, String> req, ModelMap model){
		String mId = Tools.customToEmptyBlank(req.get("mId"), "");
		
		MemberVO vo = new MemberVO();
		vo.setmId(mId);
		
		MemberVO selectedMember = memberService.selectOneOfMember(vo);
		
		model.addAttribute("oneOfMember", selectedMember);
		
		return "rental/member/modify";
	}
	
	@RequestMapping("/member/memberModify.do")
	public String updateMemberInfo(@RequestParam HashMap<String, String> req, ModelMap model){
		
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
		
		
		String mOutdate = mOutdateStr;
		
		String mStatus =  mStatusStr.equals("회원") ? "M" : "O";
		
		
		MemberVO vo = new MemberVO();
		vo.setmId(mId);
		vo.setmName(mName);
		vo.setmBirth(mBirth);
		vo.setmJoinDate(mJoindate);
		vo.setmAddr(mAddr);
		vo.setmPhone(mPhone);
		vo.setmMail(mMail);
		vo.setmLimit(mLimit);
		vo.setmStatus(mStatus);
		vo.setmOutDate(mOutdate);
		
		int resultStatus = memberService.updateMember(vo);
		
		return "redirect:/member/memberList.do";
	}
	
	@RequestMapping("/member/memberDel.do")
	public String delMember(@RequestParam HashMap<String, String> req, ModelMap model){
		String mId = Tools.customToEmptyBlank(req.get("mId"), "");

		MemberVO vo = new MemberVO();
		vo.setmId(mId);

		int resultStatus = memberService.delMember(vo);

		return "redirect:/member/memberList.do";
	}
	
}
