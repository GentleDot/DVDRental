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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
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

	@Before
	public void setUp(){
		vo = new RentVO();
	}
	
	/*대여 입력 테스트*/
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
		// 대여 기간 설정
		String rRentPeriod = "5";
		// 대여료 설정
		String rCharge = "5000";

		RentVO vo = new RentVO();
		vo.setmId(mId);
		vo.setStId(stId);
		vo.setrRentdate(rRentdate);
		vo.setrRentperiod(rRentPeriod);
		vo.setrCharge(rCharge);

		int resultStatus = dao.addRent(vo);
		
		assertThat(resultStatus == 1, is(true));
	}

	/*대여 목록 조회 테스트 - id*/
	@Test
	public void selectRentListTest(){
		String mId = "170700001";

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String curDate = sdf.format(date);
		String rRentPeriod = "5";
		String rCharge = "5000";

		// 총 3개 대여건
		for(int i = 1; i <= 3; i++){
			RentVO tempVO = new RentVO();
			tempVO.setmId(mId);
			tempVO.setrRentdate(curDate);
			tempVO.setStId("2017-item"+i);
			/*tempVO.setrRentperiod(rRentPeriod);
			tempVO.setrCharge(rCharge);*/

			dao.addRent(tempVO);
		}

		vo.setmId("");
		vo.setPageNo(1);
		vo.setPageSize(10);
		List<RentVO> resultList = dao.selectRentListByMemberID(vo);

		// 테스트로 넣은 데이터 포함 갯수
//		assertThat(resultList.size(), is(3 + 1));
		assertThat(resultList.size(), is(10)); // 170809 현재 데이터 수
	}

	/*대여 목록 조회 테스트 - 대여일 */
	@Test
	public void selectRentListByRentdateTest(){
		String mId = "170700001";

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String curDate = sdf.format(date);
		String rRentPeriod = "5";
		String rCharge = "5000";

		// 총 3개 대여건
		for(int i = 1; i <= 3; i++){
			RentVO tempVO = new RentVO();
			tempVO.setmId(mId);
			tempVO.setrRentdate(curDate);
			tempVO.setStId("2017-item"+i);
			/*tempVO.setrRentperiod(rRentPeriod);
			tempVO.setrCharge(rCharge);*/

			dao.addRent(tempVO);
		}

		vo.setrRentdate("201707");
		vo.setPageNo(1);
		vo.setPageSize(10);
		List<RentVO> resultList = dao.selectRentListByRentdate(vo);

		// 테스트로 넣은 데이터 포함 갯수
//		assertThat(resultList.size(), is(0));
		assertThat(resultList.size(), is(4)); // 170809 현재 '201707' 로 시작하는 대여일을 가진 데이터 수
	}

	/*대여 목록 조회 테스트 - 상품 */
	@Test
	public void selectRentListByItemTest(){
		String mId = "170700001";

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String curDate = sdf.format(date);
		String rRentPeriod = "5";
		String rCharge = "5000";

		// 총 3개 대여건
		for(int i = 1; i <= 3; i++){
			RentVO tempVO = new RentVO();
			tempVO.setmId(mId);
			tempVO.setrRentdate(curDate);
			tempVO.setStId("2017-item"+i);
			/*tempVO.setrRentperiod(rRentPeriod);
			tempVO.setrCharge(rCharge);*/

			dao.addRent(tempVO);
		}

		vo.setStId("2017-item2");
		vo.setPageNo(1);
		vo.setPageSize(10);
		List<RentVO> resultList = dao.selectRentListByItemID(vo);

		// 테스트로 넣은 데이터 포함 갯수
//		assertThat(resultList.size(), is(1));
		assertThat(resultList.size(), is(2)); // 170809 현재 '2017-item2' 의 데이터 개수
	}


	/*대여 총계 확인 테스트*/
	@Test
	public void totalCountOfRentListTest(){
		// 총 3개 대여건
		for(int i = 1; i <= 3; i++){
			RentVO tempVO = new RentVO();
			tempVO.setmId("170700001");
			tempVO.setrRentdate("20170801");
			tempVO.setStId("2017-item"+i);
			/*tempVO.setrRentperiod(rRentPeriod);
			tempVO.setrCharge(rCharge);*/

			dao.addRent(tempVO);
		}

		int totalCnt = dao.totalCountOfRentList();

//		assertThat(totalCnt, is(3 + 1));
		assertThat(totalCnt, is(12)); // 170809 현재 데이터 수
	}

	/* 회원 대여 목록 확인 테스트*/
	@Test
	public void selectOneOfRentTest(){
		vo.setmId("170700001");
		vo.setrRentdate("20170804");
		//
		vo.setStId("2017-item1");
		
		dao.addRent(vo);
		
		List<RentVO> resultList = dao.selectOneOfRent(vo);
		assertThat(resultList.size(), is(1));
	}

	/* 대여 상세 정보 확인 테스트*/
	@Test
	public void checkRentDetailTest(){
		vo.setmId("170700001");
		vo.setrRentdate("20170804");
		vo.setStId("2017-item1");

		dao.addRent(vo);

		RentVO resultVO = dao.checkRentDetail(vo);
		assertThat(resultVO.getStId(), is("2017-item1"));
	}

	/*대여 정보 수정 테스트*/
	@Test
	public void updateRentTest(){
		String rentdate = "20170804";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date convertedRentdate = null;
		try {
			convertedRentdate = sdf.parse(rentdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(convertedRentdate);
		cal.add(Calendar.DAY_OF_YEAR, 5);

		String returndate = sdf.format(cal.getTime());

		RentVO tempVO = new RentVO();
		tempVO.setmId("170700001");
		tempVO.setrRentdate("20170804");
		tempVO.setStId("2017-item1");

		dao.addRent(tempVO);

		vo.setmId("170700001");
		vo.setrRentdate("20170804");
		vo.setStId("2017-item1");
		// 반납일
		vo.setrReturndate("20170809");
		// 반납 처리되면 "Y"
		vo.setrReturnStatus("Y");
		// 연체료 설정
		vo.setrArrears("0");
		// 연체료 납부일 (연체료가 0이 아닌 경우에만 기입)
		vo.setrArrearsClear("");
		int resultStatus = dao.updateRent(vo);

		RentVO searchVO = dao.checkRentDetail(tempVO);
		String getReturnStatus = searchVO.getrReturnStatus();
		String getReturndate = searchVO.getrReturndate();

		assertThat(getReturnStatus, is("Y"));
		assertThat(getReturndate, is(returndate));
	}
	/*대여 삭제 테스트*/
	@Test
	public void delRentTest(){
		RentVO tempVO = new RentVO();
		tempVO.setmId("170700001");
		tempVO.setrRentdate("20170804");
		tempVO.setStId("2017-item1");

		dao.addRent(tempVO);

		int resultStatus = dao.delRent(tempVO);

		RentVO searchVO = dao.checkRentDetail(tempVO);

		// searchVO가 없는 경우 null, 존재하는 경우 false 반환
		assertNull("제거된 대여 정보는 조회되지 않아야 한다.",searchVO);
	}

	/*회원 대여 내역 조회 테스트*/
	@Test
	public void checkRentDetailOfMemberTest(){
		String mId = "170700001";
		String rentdate = "201707";

		RentVO vo = new RentVO();
		vo.setmId(mId);
		vo.setrRentdate(rentdate);

		List<RentVO> resultList = dao.checkRentDetailOfMember(vo);

		assertThat(resultList.size(), is(2));

	}

	/*상품 대여 내역 조회 테스트*/
	@Test
	public void checkRentDetailOfGoodsTest(){
		String stId = "2017-item1";
		String rentdate = "201708";

		RentVO vo = new RentVO();
		vo.setStId(stId);
		vo.setrRentdate(rentdate);

		List<RentVO> resultList = dao.checkRentDetailOfGoods(vo);

		assertThat(resultList.size(), is(2));

	}
}
