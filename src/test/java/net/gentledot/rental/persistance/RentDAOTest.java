package net.gentledot.rental.persistance;

import net.gentledot.rental.vo.RentVO;
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
public class RentDAOTest {
	private static final Logger LOGGER = Logger.getLogger(RentDAOTest.class);
	
	@Resource(name = "rentDao")
	RentDAO dao;
	
	private RentVO vo;
	
	/*재고 입력 테스트*/
	@Test
	public void addRentTest(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		// member에서 가져온 id
		String mId = "170700001";
		// storage에서 가져온 id
		String stId = "2017-item1";
		// 오늘 날짜 설정
		String rRentdate = sdf.format(date);

		RentVO vo = new RentVO();
		vo.setmId(mId);
		vo.setStId();

		int resultStatus = dao.addRent(vo);
		
		assertThat(resultStatus == 1, is(true));
	}

	/*재고 목록 조회 테스트*/
	@Test
	public void selectRentListTest(){
		// stId를 1부터 10까지 입력
		for(int i = 1; i <= 10; i++){
			RentVO tempVO = new RentVO();
			tempVO.setStId("2017-item"+i);
			// 테스트를 위해 제품 1을 10개로 가정
			tempVO.setpId("1");

			dao.addRent(tempVO);
		}
		vo.setStId("");
		vo.setPageNo(1);
		vo.setPageSize(10);
		List<RentVO> resultList = dao.selectRentList(vo);

		assertThat(resultList.size(), is(10));
	}

	/*재고 총계 확인 테스트*/
	@Test
	public void totalCountOfRentListTest(){
		// stId를 1부터 10까지 입력
		for(int i = 1; i <= 10; i++){
			RentVO tempVO = new RentVO();
			tempVO.setStId("2017-item"+i);
			// 테스트를 위해 제품 1을 10개로 가정
			tempVO.setpId("1");

			dao.addRent(tempVO);
		}

		int totalCnt = dao.totalCountOfRentList();

		assertThat(totalCnt, is(10));
	}

	/* 재고 상세정보 확인 테스트*/
	@Test
	public void selectOneOfRentTest(){
		vo.setStId("2017-item1");
		vo.setpId("1");
		
		dao.addRent(vo);
		
		RentVO resultVO = dao.selectOneOfRent(vo);
		String stId = resultVO.getStId();
		assertThat(stId, is("2017-item1"));
	}

	/*재고 정보 수정 테스트*/
	@Test
	public void updateRentTest(){
		RentVO tempVO = new RentVO();
		tempVO.setStId("2017-item1");
		tempVO.setpId("1");
		tempVO.setStGetdate("20171222");
		tempVO.setStStatus("정상");

		dao.addRent(tempVO);

		vo.setStId("2017-item1");
		// 상태는 '정상', '분실', '파손' 기입이 가능하다
		vo.setStStatus("분실");
		// 상태가 '정상'에서 변경되는 경우에만 기입
		vo.setStWastedate("20170820");
		// (고민필요) 구입가가 비용이 되도록 설정
		vo.setStWastecost("22000");
		// (고민필요) 상태 변경 사유를 기입
		vo.setStWasteReason("고객 테스트1(170700001)의 장기 미반납으로 인한 분실 처리");

		int resultStatus = dao.updateRent(vo);

		RentVO searchVO = dao.selectOneOfRent(tempVO);
		String wastDate = searchVO.getStWastedate();
		String stStatus = searchVO.getStStatus();

		assertThat(stStatus, is("분실"));
		assertThat(wastDate, is("20170820"));
	}
/*재고 삭제 테스트*//*
	@Test
	public void delRentTest(){
		RentVO tempVO = new RentVO();
		tempVO.setStId("2017-item1");
		tempVO.setpId("1");
		tempVO.setStGetdate("20171222");
		tempVO.setStStatus("정상");

		dao.addRent(tempVO);

		vo.setStId("2017-item1");

		int resultStatus = dao.delRent(vo);

		RentVO searchVO = dao.selectOneOfRent(vo);

		// searchVO가 없는 경우 null, 존재하는 경우 false 반환
		assertNull("제거 기능을 거친 상품 정보는 조회되지 않아야 한다.",searchVO);
	}*/

}
