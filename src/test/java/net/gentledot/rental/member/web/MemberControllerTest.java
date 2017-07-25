package net.gentledot.rental.member.web;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import net.gentledot.ids.service.IdsService;
import net.gentledot.rental.member.service.MemberService;
import net.gentledot.rental.vo.MemberVO;
import net.gentledot.utils.Pagination;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:**/applicationContext.xml")
//@TransactionConfiguration(defaultRollback=false)
@Transactional(value="txManager")
public class MemberControllerTest {

	private MemberVO vo;
	private MockMvc mockMvc;
	
	@InjectMocks
	MemberController controller;
	
	@Mock
	MemberService service;
	
	@Mock
	IdsService idsService;
	
	/*@Autowired
	private WebApplicationContext context;*/
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		/*mockMvc = MockMvcBuilders.webAppContextSetup(context).dispatchOptions(true).build();*/
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		vo = new MemberVO();
	}
	
	@Test
	public void selectMemberList() throws Exception{
		int pageSize = 10;
		int pageNo = 1;
		int pageScope = 5;
		int totalCount = 10;
		
		Map<String, Object> returnMap = new HashMap<>();
		List<MemberVO> returnList = new ArrayList<>();
		Pagination pag = new Pagination(pageSize, pageNo, pageScope, totalCount);
		
		returnMap.put("resultList", returnList);
		returnMap.put("pagination", pag);
		
		when(service.getMemberList(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(returnMap);
		
		mockMvc.perform(get("/member/memberList.do"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("resultList"))
					.andExpect(model().attributeExists("pagination"))
					.andExpect(model().attributeExists("keyword"))
					.andExpect(model().attributeExists("category"))
					.andExpect(model().attributeExists("pageNo"));
		
	}
	
	@Test
	public void addMemberInList() throws Exception{
		String mId = "";      
		String mName = "";    
		String mBirth = "";   
		String mJoinDate = "";
		String mAddr = "";    
		String mPhone = "";   
		String mMail = "";
		
		MemberVO insertVO = new MemberVO();
		insertVO.setmId(mId);
		insertVO.setmName(mName);
		insertVO.setmBirth(mBirth);
		insertVO.setmJoinDate(mJoinDate);
		insertVO.setmAddr(mAddr);
		insertVO.setmPhone(mPhone);
		insertVO.setmMail(mMail);
		
		when(service.addMember((MemberVO) anyObject())).thenReturn(1);
		
		mockMvc.perform(get("/member/addMember.do"))
					.andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("/member/memberList.do"));
		
	}
	
	@Test
	public void selectOneOfMemberTest() throws Exception{
		when(service.selectOneOfMember((MemberVO) anyObject())).thenReturn(new MemberVO());
		
		mockMvc.perform(get("/member/memberInfo.do")
							.param("mid", "170700001"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("oneOfMember"));
		
	}
	
	@Test
	public void updateMemberInfoViewTest() throws Exception{
		when(service.selectOneOfMember((MemberVO) anyObject())).thenReturn(new MemberVO());
		
		MemberVO vo = new MemberVO();
		vo.setmId("170700001");
		
		MemberVO selectVO = service.selectOneOfMember(vo);
		
		mockMvc.perform(get("/member/memberModifyView.do")
				.param("mid", "170700001"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("oneOfMember"));
		
	}
	
	@Test
	public void updateMemberInfoTest() throws Exception{
		String mId = "";      
		String mName = "";    
		String mBirth = "";   
		String mJoinDate = "";
		String mAddr = "";    
		String mPhone = "";   
		String mMail = "";
		
		MemberVO insertVO = new MemberVO();
		insertVO.setmId(mId);
		insertVO.setmName(mName);
		insertVO.setmBirth(mBirth);
		insertVO.setmJoinDate(mJoinDate);
		insertVO.setmAddr(mAddr);
		insertVO.setmPhone(mPhone);
		insertVO.setmMail(mMail);
		
		when(service.updateMember((MemberVO) anyObject())).thenReturn(1);
		
		mockMvc.perform(get("/member/memberModifyView.do"))
					.andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("/member/memberList.do"));
		
	}
	
	

}
