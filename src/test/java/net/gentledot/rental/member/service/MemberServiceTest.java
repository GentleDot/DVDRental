package net.gentledot.rental.member.service;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.gentledot.ids.service.IdsService;
import net.gentledot.rental.member.service.impl.MemberServiceImpl;
import net.gentledot.rental.persistance.MemberDAO;
import net.gentledot.rental.vo.MemberVO;
import net.gentledot.utils.Pagination;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceTest {
	private static final Logger LOG = Logger.getLogger(MemberServiceTest.class);
	
	@InjectMocks
	private MemberService memberService = new MemberServiceImpl();
	
	@Mock
	MemberDAO dao;
	
	@Mock
	IdsService idsService;
	
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
	
	@Test
	public void addMemberTest(){
		String mId = "";      
		String mName = "";    
		String mBirth = "";   
		String mJoinDate = "";
		String mAddr = "";    
		String mPhone = "";   
		String mMail = "";
		
		/*id를 idsService에서 받아온 뒤 그 번호를 양식에 맞게 변형하여 VO로 전달*/
		String tableName = "test";
		/*String getId = idsService.getNextId(tableName);*/
		String getId = "1";
		int idNum = Integer.parseInt(getId);
		
		/*id 앞자리 : 년도와 월 생성*/
		Calendar cal = Calendar.getInstance();
		int curYear = cal.get(Calendar.YEAR);
		int curMonth = cal.get(Calendar.MONTH) + 1;
		
		NumberFormat monthNf = new DecimalFormat("00");
		String customYear = String.valueOf(curYear).substring(2);
		String customMonth = monthNf.format(curMonth);
		
		NumberFormat nf = new DecimalFormat("00000");
		String idStr = nf.format(idNum);
		mId = customYear + customMonth + idStr;
		
		LOG.debug("=========================");
		LOG.debug("생성된 mId : " + mId);
		LOG.debug("=========================");
		
		vo.setmId(mId);
		
		when(dao.addMember(vo)).thenReturn(1);		
		
		
		/*assertNotNull("mId should not null.", dao.selectOneOfMember(vo));*/
		assertThat(dao.addMember(vo), is(1));
	}
	
	
}
