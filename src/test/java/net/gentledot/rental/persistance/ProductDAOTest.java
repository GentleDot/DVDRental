package net.gentledot.rental.persistance;

import net.gentledot.rental.vo.ProductVO;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:**/applicationContext.xml")
//@TransactionConfiguration(defaultRollback=false)
@Transactional(value="txManager")
public class ProductDAOTest {
	private static final Logger LOGGER = Logger.getLogger(ProductDAOTest.class);
	
	@Resource(name = "productDao")
	ProductDAO dao;
	
	private ProductVO vo;

	@Before
	public void setUp(){
		vo = new ProductVO();
	}
	
	/*상품 입력 테스트*/
	@Test
	public void addProductTest(){
		vo.setpId("1");
		vo.setpName("test");
		vo.setpPrice("2000");
		vo.setpGrade("19");
		
		int resultStatus = dao.addProduct(vo);
		
		assertThat(resultStatus == 1, is(true));
	}

	/*상품 목록 조회 테스트*/
	@Test
	public void selectProductListTest(){
		// pId를 1부터 10까지 입력
		for(int i = 1; i <= 10; i++){
			ProductVO tempVO = new ProductVO();
			tempVO.setpId(i + "");

			dao.addProduct(tempVO);
		}
		vo.setpId("");
		vo.setPageNo(1);
		vo.setPageSize(10);
		List<ProductVO> resultList = dao.selectProductList(vo);

		assertThat(resultList.size(), is(10));
	}

	/*상품 목록 조회 - 상품명 테스트*/
	@Test
	public void selectProductListByNameTest(){
		// pId를 1부터 10까지 입력
		for(int i = 1; i <= 10; i++){
			ProductVO tempVO = new ProductVO();
			tempVO.setpId(i + "");
			tempVO.setpName("test" + i);

			dao.addProduct(tempVO);
		}
		vo.setpName("");
		vo.setPageNo(1);
		vo.setPageSize(10);
		List<ProductVO> resultList = dao.selectProductListByName(vo);

		assertThat(resultList.size(), is(10));
	}

	/*상품 총계 확인 테스트*/
	@Test
	public void totalCountOfProductListTest(){
		// pId를 1부터 10까지 입력
		for(int i = 1; i <= 10; i++){
			ProductVO tempVO = new ProductVO();
			tempVO.setpId(i + "");
			tempVO.setpName("test" + i);

			dao.addProduct(tempVO);
		}

		int totalCnt = dao.totalCountOfProductList();

		assertThat(totalCnt, is(10));
	}

	/* 상품 상세정보 확인 테스트*/
	@Test
	public void selectOneOfProductTest(){
		vo.setpId("1");
		
		dao.addProduct(vo);
		
		ProductVO resultVO = dao.selectOneOfProduct(vo);
		String productID = resultVO.getpId();
		assertThat(productID, is("1"));
	}

	/*상품 정보 수정 테스트*/
	@Test
	public void updateProductTest(){
		ProductVO tempVO = new ProductVO();
		tempVO.setpId("1");
		tempVO.setpName("바람과");
		tempVO.setpPrice("12000");
		tempVO.setpGrade("12");

		dao.addProduct(tempVO);

		vo.setpId("1");
		vo.setpPrice("22000");
		vo.setpGrade("15");

		int resultStatus = dao.updateProduct(vo);

		ProductVO searchVO = dao.selectOneOfProduct(vo);
		String modifiedPrice = searchVO.getpPrice();
		String modifiedGrade = searchVO.getpGrade();

		assertThat(modifiedPrice, is("22000"));
		assertThat(modifiedGrade, is("15"));
	}

	/*상품 정보 삭제 테스트*/
	@Test
	public void delProductTest(){
		ProductVO tempVO = new ProductVO();
		tempVO.setpId("1");
		tempVO.setpName("바람과");
		tempVO.setpPrice("12000");
		tempVO.setpGrade("12");

		dao.addProduct(tempVO);

		vo.setpId("1");

		int resultStatus = dao.delProduct(vo);

		ProductVO searchVO = dao.selectOneOfProduct(vo);

		// searchVO가 없는 경우 null, 존재하는 경우 false 반환
		assertNull("제거 기능을 거친 상품 정보는 조회되지 않아야 한다.",searchVO);
	}
}
