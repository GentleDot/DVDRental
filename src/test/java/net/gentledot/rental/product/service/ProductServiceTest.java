package net.gentledot.rental.product.service;

import net.gentledot.ids.service.IdsService;
import net.gentledot.rental.product.service.ProductService;
import net.gentledot.rental.product.service.impl.ProductServiceImpl;
import net.gentledot.rental.persistance.ProductDAO;
import net.gentledot.rental.vo.ProductVO;
import net.gentledot.utils.Pagination;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
	private static final Logger LOG = Logger.getLogger(ProductServiceTest.class);
	
	@InjectMocks
	private ProductService productService = new ProductServiceImpl();
	
	@Mock
	ProductDAO dao;
	
	@Mock
	IdsService idsService;
	
	ProductVO vo;
	
	@Before
	public void setUp(){
		vo = new ProductVO();
	}

	/*제품 조회 서비스 테스트*/
	@Test
	public void getProductListTest(){
		String pId = "";
		int pageSize = 10;
		int pageNo = 1;
		int pageScope = 5;
		
		// list 생성
		List<ProductVO> returnList = new ArrayList<>();
		
		for (int i = 1; i <= 10; i++){
			ProductVO tempVO = new ProductVO();
			tempVO.setpId(i+ "");
			
			returnList.add(tempVO);
		}
		
		vo.setpId("");
		vo.setPageNo(pageNo);
		vo.setPageSize(pageSize);
		
		int totalCnt = returnList.size();
		
		when(dao.selectProductList(vo)).thenReturn(returnList);
		when(dao.totalCountOfProductList()).thenReturn(returnList.size());
		
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

	/*제품 조회 서비스 - 제품명 테스트*/
	@Test
	public void getProductListByNameTest(){
		String pName = "";
		int pageSize = 10;
		int pageNo = 1;
		int pageScope = 5;

		// list 생성
		List<ProductVO> returnList = new ArrayList<>();

		for (int i = 1; i <= 10; i++){
			ProductVO tempVO = new ProductVO();
			tempVO.setpId(i + "");
			tempVO.setpName("test" + i);

			returnList.add(tempVO);
		}

		vo.setpId("");
		vo.setpName(pName);
		vo.setPageNo(pageNo);
		vo.setPageSize(pageSize);

		int totalCnt = returnList.size();


		when(dao.selectProductList(vo)).thenReturn(returnList);
		when(dao.totalCountOfProductList()).thenReturn(returnList.size());

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

	/*제품 추가 서비스 테스트*/
	@Test
	public void addProductTest(){
		String pId = "";
		String pName = "";
		String pPrice = "";
		String pGrade = "";

		/*id를 idsService에서 받아온 뒤 그 번호를 양식에 맞게 변형하여 VO로 전달*/
		String tableName = "product";
		/*String getId = idsService.getNextId(tableName);*/
		String getId = "1";
		int idNum = Integer.parseInt(getId);


		vo.setpId(pId);

		when(dao.addProduct(vo)).thenReturn(1);

		/*assertNotNull("mId should be not null.", dao.selectOneOfProduct(vo));*/
		assertThat(dao.addProduct(vo), is(1));
	}
	
}
