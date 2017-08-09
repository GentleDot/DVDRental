package net.gentledot.rental.storage.web;


import net.gentledot.ids.service.IdsService;
import net.gentledot.rental.product.service.ProductService;
import net.gentledot.rental.storage.service.StorageService;
import net.gentledot.rental.vo.ProductVO;
import net.gentledot.rental.vo.StorageVO;
import net.gentledot.utils.Pagination;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:**/applicationContext.xml")
//@TransactionConfiguration(defaultRollback=false)
@Transactional(value="txManager")
public class StorageControllerTest {

	private StorageVO vo;
	private MockMvc mockMvc;

	@InjectMocks
	StorageController controller;
	
	@Mock
	StorageService service;

	@Mock
	ProductService productService;
	
	@Mock
	IdsService idsService;
	
	/*@Autowired
	private WebApplicationContext context;*/
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		/*mockMvc = MockMvcBuilders.webAppContextSetup(context).dispatchOptions(true).build();*/
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		vo = new StorageVO();
	}
	
	@Test
	public void selectStorageList() throws Exception{
		/* view에서 받아올 객체 설정 */
		String stId = "";
		int pageNo = 1;
		// 아래 2개 변수는 상수 처리
		int pageSize = 10;
		int pageScope = 5;

		/*리스트 생성*/
		List<StorageVO> returnList = new ArrayList<>();

		/* pagination */
		int totalCount = 10;
		Pagination pag = new Pagination(pageSize, pageNo, pageScope, totalCount);
		pag.setPaging();

		/* 서비스에서 받아올 returnMap */
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("resultList", returnList);
		returnMap.put("pagination", pag);

		when(service.getStorageList(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(returnMap);

		Map<String, Object> resultMap = service.getStorageList(stId, pageSize, pageNo, pageScope);
		
		mockMvc.perform(get("/storage/storageList.do"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("resultList"))
					.andExpect(model().attributeExists("pagination"))
					.andExpect(model().attributeExists("stId"))
					.andExpect(model().attributeExists("pageNo"));
		
	}

	@Test
	public void addStorageViewTest() throws Exception{
		List<ProductVO> returnList = new ArrayList<>();

		when(productService.selectTotalProductList()).thenReturn(returnList);

		List<ProductVO> productList = productService.selectTotalProductList();

		mockMvc.perform(get("/storage/addStorageView.do"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("productList"));
	}
	
	@Test
	public void addEmptyStorageInListTest() throws Exception{
		String stGetdate = "";
		String pId = "";

		StorageVO insertVO = new StorageVO();
		insertVO.setStGetdate(stGetdate);
		insertVO.setpId(pId);

		when(service.addStorage((StorageVO) anyObject())).thenReturn(1);

		int resultStatus = service.addStorage(insertVO);

		mockMvc.perform(get("/storage/addStorage.do"))
					.andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("/storage/addStorageView.do"));

	}
	
	@Test
	public void addStorageInListTest() throws Exception{
//		String stId = "2017-item999";
		String stGetdate = "20170101";
		String pId = "1";
		String curDate = "20170808";

		StorageVO insertVO = new StorageVO();
//		insertVO.setStId(stId);
		insertVO.setStGetdate(stGetdate);
		insertVO.setpId(pId);

		when(service.addStorage((StorageVO) anyObject())).thenReturn(1);

		int resultStatus = service.addStorage(insertVO);

		mockMvc.perform(get("/storage/addStorage.do"))
						.andExpect(status().is3xxRedirection())
						.andExpect(redirectedUrl("/storage/storageList.do"));

	}

	@Test
	public void selectOneOfStorageTest() throws Exception{
		String stId = "2017-item1";

		when(service.getOneOfStorage((StorageVO) anyObject())).thenReturn(new StorageVO());

		vo.setStId(stId);
		StorageVO resultVO = service.getOneOfStorage(vo);
		
		mockMvc.perform(get("/storage/storageInfo.do")
							.param("stId", stId))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("oneOfStorage"));
		
	}

	@Test
	public void updateStorageInfoViewTest() throws Exception{
		String stId = "2017-item1";

		when(service.getOneOfStorage((StorageVO) anyObject())).thenReturn(new StorageVO());

		vo.setStId(stId);
		StorageVO selectedStorage = service.getOneOfStorage(vo);
		
		mockMvc.perform(get("/storage/storageModifyView.do")
				.param("stId", stId))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("oneOfStorage"));
		
	}

	@Test
	public void updateStorageInfoTest() throws Exception{
		String stId = "2017-item1";
		String stGetdate = "";
		String pId = "";
		String stStatus = "";
		String stWastedate = "";
		String stWastecost = "0";
		String stWasteReason = "사유";

		StorageVO updateVO = new StorageVO();
		updateVO.setStId(stId);
		updateVO.setStGetdate(stGetdate);
		updateVO.setpId(pId);
		updateVO.setStStatus(stStatus);
		updateVO.setStWastedate(stWastedate);
		updateVO.setStWastecost(stWastecost);
		updateVO.setStWasteReason(stWasteReason);

		when(service.updateStorage((StorageVO) anyObject())).thenReturn(1);
		int resultStatus = service.updateStorage(updateVO);
		
		mockMvc.perform(get("/storage/storageModify.do"))
					.andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("/storage/storageList.do"));
		
	}

	/*
	@Test
	public void delStorageTest() throws Exception {
		String pId = "1";

		when(service.delStorage((StorageVO) anyObject())).thenReturn(1);

		vo.setpId(pId);
		int resultStatus = service.delStorage(vo);

		mockMvc.perform(get("/storage/storageDel.do")
			.param("pId", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/storage/storageList.do"));


	}

	*/

}
