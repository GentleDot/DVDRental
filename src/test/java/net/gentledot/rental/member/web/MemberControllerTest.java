package net.gentledot.rental.member.web;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

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
					.andExpect(model().attributeExists("mId"))
					.andExpect(model().attributeExists("pageNo"));
		
	}
	

}
