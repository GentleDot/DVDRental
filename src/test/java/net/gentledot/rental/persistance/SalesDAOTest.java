package net.gentledot.rental.persistance;

import net.gentledot.rental.vo.SalesVO;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:**/applicationContext.xml")
//@TransactionConfiguration(defaultRollback=false)
@Transactional(value="txManager")
public class SalesDAOTest {
	private static final Logger LOGGER = Logger.getLogger(SalesDAOTest.class);
	
	@Resource(name = "salesDao")
	SalesDAO dao;
	
	private SalesVO vo;

	@Before
	public void setUp(){
		vo = new SalesVO();
	}
	
	/*대여료 입력 테스트*/
	@Test
	public void addGetChargeTest(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		String curDate = sdf.format(date);
		String charge = "1000";

		// 발생한 건의 날짜 입력
		vo.setsDate(curDate);
		// 대여 처리 시 대여료 입력
		vo.setsCharge(charge);

		int resultStatus = dao.addGetCharge(vo);
		
		assertThat(resultStatus == 1, is(true));
	}

	/*연체료 입력 테스트*/
	@Test
	public void addGetArrearsTest(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		String curDate = sdf.format(date);
		String arrears = "1000";

		// 발생한 건의 날짜 입력
		vo.setsDate(curDate);
		// 대여 처리 시 대여료 입력
		vo.setsArrears(arrears);

		int resultStatus = dao.addGetArrears(vo);

		assertThat(resultStatus == 1, is(true));
	}

	/*제품 구입비 입력 테스트*/
	@Test
	public void addPutPriceTest(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		String curDate = sdf.format(date);
		String price = "1000";

		// 발생한 건의 날짜 입력
		vo.setsDate(curDate);
		// 대여 처리 시 대여료 입력
		vo.setsPrice(price);

		int resultStatus = dao.addPutPrice(vo);

		assertThat(resultStatus == 1, is(true));
	}

	/*폐기 비용 입력 테스트*/
	@Test
	public void addPutWastecostTest(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		String curDate = sdf.format(date);
		String wasteCost = "1000";

		// 발생한 건의 날짜 입력
		vo.setsDate(curDate);
		// 대여 처리 시 대여료 입력
		vo.setsWasteCost(wasteCost);

		int resultStatus = dao.addPutWastecost(vo);

		assertThat(resultStatus == 1, is(true));
	}

	/*매출 정보 조회 테스트*/
	@Test
	public void selectSalesListTest(){
		// 대여료 10번 입력
		for(int i = 1; i <= 10; i++){
			String curDate = "20170808";
			String charge = "1000";

			SalesVO tempVO = new SalesVO();
			tempVO.setsDate(curDate);
			tempVO.setsCharge(charge);

			dao.addGetCharge(tempVO);
		}
		// 조회 시 년도와 월을 받아 처리
		vo.setsDate("201708");
		vo.setPageNo(1);
		vo.setPageSize(10);
		List<SalesVO> resultList = dao.selectSalesList(vo);

		assertThat(resultList.size(), is(10));
	}

	/*재고 총계 확인 테스트*/
	@Test
	public void totalCountOfSalesListTest(){
		// 대여료 10번 입력
		for(int i = 1; i <= 10; i++){
			String curDate = "20170808";
			String charge = "1000";

			SalesVO tempVO = new SalesVO();
			tempVO.setsDate(curDate);
			tempVO.setsCharge(charge);

			dao.addGetCharge(tempVO);
		}

		int totalCnt = dao.totalCountOfSalesList();

		assertThat(totalCnt, is(10));
	}

}
