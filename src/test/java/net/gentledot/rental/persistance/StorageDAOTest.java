package net.gentledot.rental.persistance;

import net.gentledot.rental.vo.StorageVO;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:**/applicationContext.xml")
//@TransactionConfiguration(defaultRollback=false)
@Transactional(value="txManager")
public class StorageDAOTest {
	private static final Logger LOGGER = Logger.getLogger(StorageDAOTest.class);
	
	@Resource(name = "storageDao")
	StorageDAO dao;
	
	private StorageVO vo;

	@Before
	public void setUp(){
		vo = new StorageVO();
	}
	
	/*재고 입력 테스트*/
	@Test
	public void addStorageTest(){
		Calendar cal = Calendar.getInstance();
		int curYear = cal.get(Calendar.YEAR);

		// stId 는 '현재년도-item순번' 형식으로 생성
		vo.setStId(curYear + "-" + "item1");
		vo.setStGetdate("20170728");
		// pId는 product 테이블에 존재하는 id만 사용 가능
		vo.setpId("1");
		// 초기값은 '정상'
		vo.setStStatus("정상");

		int resultStatus = dao.addStorage(vo);
		
		assertThat(resultStatus == 1, is(true));
	}

	/*재고 목록 조회 테스트*/
	@Test
	public void selectStorageListTest(){
		// stId를 1부터 10까지 입력
		for(int i = 1; i <= 10; i++){
			StorageVO tempVO = new StorageVO();
			tempVO.setStId("2017-item"+i);
			// 테스트를 위해 제품 1을 10개로 가정
			tempVO.setpId("1");

			dao.addStorage(tempVO);
		}
		vo.setStId("");
		vo.setPageNo(1);
		vo.setPageSize(10);
		List<StorageVO> resultList = dao.selectStorageList(vo);

		assertThat(resultList.size(), is(10));
	}

	/*재고 총계 확인 테스트*/
	@Test
	public void totalCountOfStorageListTest(){
		// stId를 1부터 10까지 입력
		for(int i = 1; i <= 10; i++){
			StorageVO tempVO = new StorageVO();
			tempVO.setStId("2017-item"+i);
			// 테스트를 위해 제품 1을 10개로 가정
			tempVO.setpId("1");

			dao.addStorage(tempVO);
		}

		int totalCnt = dao.totalCountOfStorageList();

		assertThat(totalCnt, is(10));
	}

	/* 재고 상세정보 확인 테스트*/
	@Test
	public void selectOneOfStorageTest(){
		vo.setStId("2017-item1");
		vo.setpId("1");
		
		dao.addStorage(vo);
		
		StorageVO resultVO = dao.selectOneOfStorage(vo);
		String stId = resultVO.getStId();
		assertThat(stId, is("2017-item1"));
	}

	/*재고 정보 수정 테스트*/
	@Test
	public void updateStorageTest(){
		StorageVO tempVO = new StorageVO();
		tempVO.setStId("2017-item1");
		tempVO.setpId("1");
		tempVO.setStGetdate("20171222");
		tempVO.setStStatus("정상");

		dao.addStorage(tempVO);

		vo.setStId("2017-item1");
		// 상태는 '정상', '분실', '파손' 기입이 가능하다
		vo.setStStatus("분실");
		// 상태가 '정상'에서 변경되는 경우에만 기입
		vo.setStWastedate("20170820");
		// (고민필요) 구입가가 비용이 되도록 설정
		vo.setStWastecost("22000");
		// (고민필요) 상태 변경 사유를 기입
		vo.setStWasteReason("고객 테스트1(170700001)의 장기 미반납으로 인한 분실 처리");

		int resultStatus = dao.updateStorage(vo);

		StorageVO searchVO = dao.selectOneOfStorage(tempVO);
		String wastDate = searchVO.getStWastedate();
		String stStatus = searchVO.getStStatus();

		assertThat(stStatus, is("분실"));
		assertThat(wastDate, is("20170820"));
	}
/*재고 삭제 테스트*//*
	@Test
	public void delStorageTest(){
		StorageVO tempVO = new StorageVO();
		tempVO.setStId("2017-item1");
		tempVO.setpId("1");
		tempVO.setStGetdate("20171222");
		tempVO.setStStatus("정상");

		dao.addStorage(tempVO);

		vo.setStId("2017-item1");

		int resultStatus = dao.delStorage(vo);

		StorageVO searchVO = dao.selectOneOfStorage(vo);

		// searchVO가 없는 경우 null, 존재하는 경우 false 반환
		assertNull("제거 기능을 거친 상품 정보는 조회되지 않아야 한다.",searchVO);
	}*/

}
