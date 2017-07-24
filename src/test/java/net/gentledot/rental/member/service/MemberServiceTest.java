package net.gentledot.rental.member.service;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.gentledot.rental.member.service.impl.MemberServiceImpl;
import net.gentledot.rental.persistance.MemberDAO;
import net.gentledot.rental.vo.MemberVO;
import net.gentledot.utils.Pagination;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceTest {
	
	@InjectMocks
	private MemberService memberService = new MemberServiceImpl();
	
	@Mock
	MemberDAO dao;
	
	MemberVO vo;
	
	@Before
	public void setUp(){
		vo = new MemberVO();
	}
	
	@Test
	public void getMemberListTest(){
		String mId = "";
		int pageSize = 10;
		int pageNo = 1;
		int pageScope = 5;
		
		// list 생성
		List<MemberVO> returnList = new ArrayList<>();
		
		for (int i = 1; i <= 10; i++){
			MemberVO tempVO = new MemberVO();
			tempVO.setmId("1707" + i);
			
			returnList.add(tempVO);
		}
		
		vo.setmId("");
		vo.setPageNo(pageNo);
		vo.setPageSize(pageSize);
		
		int totalCnt = returnList.size();
		
		
		when(dao.selectMemberList(vo)).thenReturn(returnList);
		when(dao.totalCountOfMemberList()).thenReturn(returnList.size());
		
		// pagination 생성
		Pagination pag = new Pagination(pageSize, pageNo, pageScope, totalCnt);
		pag.setPaging();
		
		// return 할 map에 list와 pagination 담기
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("resultList", returnList);
		resultMap.put("pagination", pag);
		
		assertThat(resultMap.containsKey("resultList"), is(true));
		assertThat(resultMap.containsKey("pagination"), is(true));
	}
	
	@Test
	public void getMemberListByNameTest(){
		String mName = "";
		int pageSize = 10;
		int pageNo = 1;
		int pageScope = 5;
		
		// list 생성
		List<MemberVO> returnList = new ArrayList<>();
		
		for (int i = 1; i <= 10; i++){
			MemberVO tempVO = new MemberVO();
			tempVO.setmId("1707" + i);
			tempVO.setmName("test" + i);
			
			returnList.add(tempVO);
		}
		
		vo.setmId("");
		vo.setmName(mName);
		vo.setPageNo(pageNo);
		vo.setPageSize(pageSize);
		
		int totalCnt = returnList.size();
		
		
		when(dao.selectMemberList(vo)).thenReturn(returnList);
		when(dao.totalCountOfMemberList()).thenReturn(returnList.size());
		
		// pagination 생성
		Pagination pag = new Pagination(pageSize, pageNo, pageScope, totalCnt);
		pag.setPaging();
		
		// return 할 map에 list와 pagination 담기
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("resultList", returnList);
		resultMap.put("pagination", pag);
		
		assertThat(resultMap.containsKey("resultList"), is(true));
		assertThat(resultMap.containsKey("pagination"), is(true));
	}
	
}
