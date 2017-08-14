package net.gentledot.rental.rent.web;


import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import net.gentledot.rental.rent.service.RentService;
import net.gentledot.rental.vo.RentVO;
import net.gentledot.utils.Pagination;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:**/applicationContext.xml")
//@TransactionConfiguration(defaultRollback=false)
@Transactional(value="txManager")
public class RentControllerTest {

	private RentVO vo;
	private MockMvc mockMvc;

	@InjectMocks
	RentController controller;
	
	@Mock
	RentService service;
	
	/*@Autowired
	private WebApplicationContext context;*/
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		/*mockMvc = MockMvcBuilders.webAppContextSetup(context).dispatchOptions(true).build();*/
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		vo = new RentVO();
	}
	
	@Test
	public void selectRentList() throws Exception{
		/* view에서 받아올 객체 설정 */
		String category = "";
		String keyword = "";
		int pageNo = 1;
		// 아래 2개 변수는 상수 처리
		int pageSize = 10;
		int pageScope = 5;

		/* 받아온 category에 따라 받아올 리스트를 다르게 설정 */
		List<RentVO> returnList = null;
		switch (category) {
		case "date":
			returnList = new ArrayList<>();
			break;
		case "item":
			returnList = new ArrayList<>();
			break;
		default:
			returnList = new ArrayList<>();
			break;
		}

		/* pagination */
		int totalCount = 10;
		Pagination pag = new Pagination(pageSize, pageNo, pageScope, totalCount);

		/* 서비스에서 받아올 returnMap */
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("resultList", returnList);
		returnMap.put("pagination", pag);
		
		when(service.getRentListByMember(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(returnMap);

		Map<String, Object> resultMap = service.getRentListByMember(keyword, pageSize, pageNo, pageScope);
		
		mockMvc.perform(get("/rent/rentList.do"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("resultList"))
					.andExpect(model().attributeExists("pagination"))
					.andExpect(model().attributeExists("keyword"))
					.andExpect(model().attributeExists("category"))
					.andExpect(model().attributeExists("pageNo"));
		
	}
	
	@Test
	public void addEmptyRentInList() throws Exception{
		String mId = "201799999";
		String rentdate = "";
		String stId = "";
		String rentPeriod = "";
		String charge = "";
		
		RentVO insertVO = new RentVO();
		insertVO.setmId(mId);               
		insertVO.setrRentdate(rentdate);    
		insertVO.setStId(stId);             
		insertVO.setrRentperiod(rentPeriod);

		when(service.addRent((RentVO) anyObject())).thenReturn(1);

		int resultStatus = service.addRent(insertVO);
		
		mockMvc.perform(get("/rent/addRent.do")
				.param("inputRmId", mId))
					.andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("/rent/addRentView.do?mId=" + mId));
		
	}
	
	@Test
	public void addRentInList() throws Exception{
		String mId = "";
		String rentdate = "";
		String stId = "";
		String rentPeriod = "";
		String charge = "";
		
		RentVO insertVO = new RentVO();
		insertVO.setmId(mId);               
		insertVO.setrRentdate(rentdate);    
		insertVO.setStId(stId);             
		insertVO.setrRentperiod(rentPeriod);

		when(service.addRent((RentVO) anyObject())).thenReturn(1);

		int resultStatus = service.addRent(insertVO);
		
		mockMvc.perform(get("/rent/addRent.do"))
					.andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("/rent/rentList.do"));
		
	}

	@Test
	public void selectOneOfRentTest() throws Exception{
		String mId = "";
		String rentdate = "";
		String stId = "";

		when(service.checkRentDetail((RentVO) anyObject())).thenReturn(new RentVO());

		vo.setmId(mId);
		vo.setrRentdate(rentdate);
		vo.setStId(stId);

		RentVO resultVO = service.checkRentDetail(vo);

		mockMvc.perform(get("/rent/rentInfo.do")
							.param("mId", "170700001")
							.param("rentdate", "20170804")
							.param("stId", "2017-item3"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("details"));
		
	}
	
	@Test
	public void updateRentInfoViewTest() throws Exception{
		String mId = "";
		String rentdate = "";
		String stId = "";

		when(service.checkRentDetail((RentVO) anyObject())).thenReturn(new RentVO());

		vo.setmId(mId);
		vo.setrRentdate(rentdate);
		vo.setStId(stId);

		RentVO resultVO = service.checkRentDetail(vo);

		mockMvc.perform(get("/rent/rentModifyView.do")
				.param("mId", "170700001")
				.param("rentdate", "20170804")
				.param("stId", "2017-item3"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("details"));
	}

	@Test
	public void updateEmptyRentInfoTest() throws Exception{
		String mId = "170799999";
		String rentdate = "20170808";
		String stId = "999";
		String returndate = "";
		String returnStatus = "";
		String arrears = "";
		String arrearsClear = "";

		vo.setmId(mId);
		vo.setrRentdate(rentdate);
		vo.setStId(stId);
		vo.setrReturndate(returndate);
		vo.setrReturnStatus(returnStatus);
		vo.setrArrears(arrears);
		vo.setrArrearsClear(arrearsClear);

		when(service.updateRent((RentVO) anyObject())).thenReturn(1);
		int resultStatus = service.updateRent(vo);
		
		mockMvc.perform(get("/rent/rentModify.do")
				.param("getRmId", mId)
				.param("getRrentdate", rentdate)
				.param("getRstId", stId))
					.andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("/rent/rentModifyView.do?mId=" + mId + "&rentdate=" + rentdate + "&stId=" + stId));
		
	}
	
	@Test
	public void updateRentInfoTest() throws Exception{
		String mId = "";
		String rentdate = "";
		String stId = "";
		String returndate = "";
		String returnStatus = "";
		String arrears = "";
		String arrearsClear = "";

		vo.setmId(mId);
		vo.setrRentdate(rentdate);
		vo.setStId(stId);
		vo.setrReturndate(returndate);
		vo.setrReturnStatus(returnStatus);
		vo.setrArrears(arrears);
		vo.setrArrearsClear(arrearsClear);

		when(service.updateRent((RentVO) anyObject())).thenReturn(1);
		int resultStatus = service.updateRent(vo);
		
		mockMvc.perform(get("/rent/rentModify.do"))
					.andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("/rent/rentList.do"));
		
	}

	@Test
	public void delRentTest() throws Exception {
		String mId = "";
		String rentdate = "";
		String stId = "";

		when(service.delRent((RentVO) anyObject())).thenReturn(1);

		RentVO vo = new RentVO();
		vo.setmId(mId);
		vo.setrRentdate(rentdate);
		vo.setStId(stId);

		int resultStatus = service.delRent(vo);

		mockMvc.perform(get("/rent/rentDel.do")
				.param("mId", "170700001")
				.param("rentdate", "20170804")
				.param("stId", "2017-item3"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rent/rentList.do"));
	}
}
