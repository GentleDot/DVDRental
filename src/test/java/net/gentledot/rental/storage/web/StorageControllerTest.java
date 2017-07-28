package net.gentledot.rental.storage.web;


import net.gentledot.ids.service.IdsService;
import net.gentledot.rental.product.service.ProductService;
import net.gentledot.rental.product.web.ProductController;
import net.gentledot.rental.vo.ProductVO;
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

	private ProductVO vo;
	private MockMvc mockMvc;

	@InjectMocks
	ProductController controller;
	
	@Mock
	ProductService service;
	
	@Mock
	IdsService idsService;
	
	/*@Autowired
	private WebApplicationContext context;*/
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		/*mockMvc = MockMvcBuilders.webAppContextSetup(context).dispatchOptions(true).build();*/
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		vo = new ProductVO();
	}
	
	@Test
	public void selectProductList() throws Exception{
		/* view에서 받아올 객체 설정 */
		String category = "id";
		String keyword = "1";
		int pageNo = 1;
		// 아래 2개 변수는 상수 처리
		int pageSize = 10;
		int pageScope = 5;

		/* 받아온 category에 따라 받아올 리스트를 다르게 설정 */
		List<ProductVO> returnList = null;
		switch (category){
			case "id":
				List<ProductVO> getProductList = new ArrayList<>();
				returnList = getProductList;
				break;
			case "name":
				List<ProductVO> getProductListByName = new ArrayList<>();
				returnList = getProductListByName;
				break;
		}

		/* pagination */
		int totalCount = 10;
		Pagination pag = new Pagination(pageSize, pageNo, pageScope, totalCount);

		/* 서비스에서 받아올 returnMap */
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("resultList", returnList);
		returnMap.put("pagination", pag);
		
		when(service.getProductList(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(returnMap);

		Map<String, Object> resultMap = service.getProductList(keyword, pageSize, pageNo, pageScope);
		
		mockMvc.perform(get("/product/productList.do"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("resultList"))
					.andExpect(model().attributeExists("pagination"))
					.andExpect(model().attributeExists("keyword"))
					.andExpect(model().attributeExists("category"))
					.andExpect(model().attributeExists("pageNo"));
		
	}
	
	@Test
	public void addProductInList() throws Exception{
		String pName= "";
		String pPrice = "";
		String pGrade = "";
		
		ProductVO insertVO = new ProductVO();
		insertVO.setpId("1");
		insertVO.setpName(pName);
		insertVO.setpPrice(pPrice);
		insertVO.setpGrade(pGrade);

		when(service.addProduct((ProductVO) anyObject())).thenReturn(1);

		int resultStatus = service.addProduct(insertVO);
		
		mockMvc.perform(get("/product/addProduct.do"))
					.andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("/product/productList.do"));
		
	}

	/*@Test
	public void selectOneOfProductTest() throws Exception{
		String pId = "1";

		when(service.selectOneOfProduct((ProductVO) anyObject())).thenReturn(new ProductVO());

		vo.setpId(pId);
		ProductVO resultVO = service.selectOneOfProduct(vo);
		
		mockMvc.perform(get("/product/productInfo.do")
							.param("pId", "1"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("oneOfProduct"));
		
	}*/


	@Test
	public void updateProductInfoViewTest() throws Exception{
		String pId = "1";

		when(service.selectOneOfProduct((ProductVO) anyObject())).thenReturn(new ProductVO());

		vo.setpId(pId);
		ProductVO selectVO = service.selectOneOfProduct(vo);
		
		mockMvc.perform(get("/product/productModifyView.do")
				.param("pId", "1"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("oneOfProduct"));
		
	}

	@Test
	public void updateProductInfoTest() throws Exception{
		String pId = "1";
		String pName = "";
		String pPrice = "0";
		String pGrade = "ALL";

		ProductVO insertVO = new ProductVO();
		insertVO.setpId(pId);
		insertVO.setpName(pName);
		insertVO.setpPrice(pPrice);
		insertVO.setpGrade(pGrade);
		
		when(service.updateProduct((ProductVO) anyObject())).thenReturn(1);
		int resultStatus = service.updateProduct(insertVO);
		
		mockMvc.perform(get("/product/productModify.do"))
					.andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("/product/productList.do"));
		
	}

	@Test
	public void delProductTest() throws Exception {
		String pId = "1";

		when(service.delProduct((ProductVO) anyObject())).thenReturn(1);

		vo.setpId(pId);
		int resultStatus = service.delProduct(vo);

		mockMvc.perform(get("/product/productDel.do")
			.param("pId", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/product/productList.do"));


	}

	

}
