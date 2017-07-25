package net.gentledot.rental.member.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.gentledot.ids.service.IdsService;
import net.gentledot.rental.member.service.MemberService;
import net.gentledot.rental.persistance.MemberDAO;
import net.gentledot.rental.vo.MemberVO;
import net.gentledot.utils.Pagination;

@Service("memberService")
public class MemberServiceImpl implements MemberService{
	
	@Resource(name="memberDao")
	MemberDAO memberDao;
	
	@Resource(name="idsService")
	IdsService idsService;
	
	
	public Map<String, Object> getMemberList(String mId, int pageSize, int pageNo, int pageScope){
		// list 생성
		MemberVO vo = new MemberVO();
		vo.setmId(mId);
		vo.setPageNo(pageNo);
		vo.setPageSize(pageSize);
		
		List<MemberVO> returnList = memberDao.selectMemberList(vo);
		
		// pagination 생성
		int totalCnt = memberDao.totalCountOfMemberList();
		Pagination pag = new Pagination(pageSize, pageNo, pageScope, totalCnt);
		pag.setPaging();
		
		// return 할 map에 list와 pagination 담기
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("resultList", returnList);
		resultMap.put("pagination", pag);
		
		return resultMap;
	}
	
	public Map<String, Object> getMemberListByName(String mName, int pageSize, int pageNo, int pageScope){
		// list 생성
		MemberVO vo = new MemberVO();
		vo.setmName(mName);
		vo.setPageNo(pageNo);
		vo.setPageSize(pageSize);
		
		List<MemberVO> returnList = memberDao.selectMemberListByName(vo);
		
		// pagination 생성
		int totalCnt = memberDao.totalCountOfMemberList();
		Pagination pag = new Pagination(pageSize, pageNo, pageScope, totalCnt);
		pag.setPaging();
		
		// return 할 map에 list와 pagination 담기
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("resultList", returnList);
		resultMap.put("pagination", pag);
		
		return resultMap;
	}
	
	public int listCountWithNameAndPhone(MemberVO vo){
		return memberDao.listCountWithNameAndPhone(vo);
	}
	
	public MemberVO selectOneOfMember(MemberVO vo){
		return memberDao.selectOneOfMember(vo);
	}
	
	public int addMember(MemberVO vo){
		String tableName = "member";
		String getId = idsService.getNextId(tableName);
		
		int idNum = Integer.parseInt(getId);
		
		Calendar cal = Calendar.getInstance();
		int curYear = cal.get(Calendar.YEAR);
		int curMonth = cal.get(Calendar.MONTH) + 1;
		
		NumberFormat monthNf = new DecimalFormat("00");
		String customYear = String.valueOf(curYear).substring(2);
		String customMonth = monthNf.format(curMonth);
		
		NumberFormat nf = new DecimalFormat("00000");
		String idStr = nf.format(idNum);
		String mId = customYear + customMonth + idStr;
		
		vo.setmId(mId);
		
		return memberDao.addMember(vo);
	}
	
	public int updateMember(MemberVO vo){
		return memberDao.updateMember(vo);
	}
	
	public int leaveMember(MemberVO vo){
		return memberDao.leaveMember(vo);
	}
	
	public int delMember(MemberVO vo){
		return memberDao.delMember(vo);
	}
	
}
