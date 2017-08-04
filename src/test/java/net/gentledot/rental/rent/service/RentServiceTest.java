package net.gentledot.rental.rent.service;

import net.gentledot.ids.service.IdsService;
import net.gentledot.rental.rent.service.RentService;
import net.gentledot.rental.rent.service.impl.RentServiceImpl;
import net.gentledot.rental.persistance.RentDAO;
import net.gentledot.rental.vo.RentVO;
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
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RentServiceTest {
	private static final Logger LOG = Logger.getLogger(RentServiceTest.class);
	
	@InjectMocks
	private RentService rentService = new RentServiceImpl();
	
	@Mock
	private RentDAO dao;
	
	private RentVO vo;
	
	@Before
	public void setUp(){
		vo = new RentVO();
	}
	
	@Test
	public void getRentListByMemberTest(){
		String mId = "";
		int pageSize = 10;
		int pageNo = 1;
		int pageScope = 5;
		
		// list 생성
		List<RentVO> returnList = new ArrayList<>();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		// member에서 가져온 id
		String setmId = "170700001";
		// storage에서 가져온 id
		String stId = "2017-item1";
		// 오늘 날짜 설정
		String rRentdate = sdf.format(date);
		
		for (int i = 1; i <= 3; i++){
			RentVO tempVO = new RentVO();
			tempVO.setmId(setmId);
			tempVO.setStId("2017-item" + i);
			tempVO.setrRentdate(rRentdate);
			
			returnList.add(tempVO);
		}

		vo.setmId(mId);
		vo.setPageNo(pageNo);
		vo.setPageSize(pageSize);
		
		int totalCnt = returnList.size();
		
		
		when(dao.selectRentListByMemberID(vo)).thenReturn(returnList);
		when(dao.totalCountOfRentList()).thenReturn(returnList.size());
		
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

	@Test
	public void getRentListByRentdateTest(){
		String rRentdate = "";
		int pageSize = 10;
		int pageNo = 1;
		int pageScope = 5;

		// list 생성
		List<RentVO> returnList = new ArrayList<>();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		// member에서 가져온 id
		String setmId = "170700001";
		// storage에서 가져온 id
		String setstId = "2017-item1";
		// 오늘 날짜 설정
		String setrRentdate = sdf.format(date);

		for (int i = 1; i <= 3; i++){
			RentVO tempVO = new RentVO();
			tempVO.setmId(setmId);
			tempVO.setStId("2017-item" + i);
			tempVO.setrRentdate(setrRentdate);

			returnList.add(tempVO);
		}

		vo.setrRentdate(rRentdate);
		vo.setPageNo(pageNo);
		vo.setPageSize(pageSize);

		int totalCnt = returnList.size();


		when(dao.selectRentListByRentdate(vo)).thenReturn(returnList);
		when(dao.totalCountOfRentList()).thenReturn(returnList.size());

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

	@Test
	public void getRentListByItemTest(){
		String stId = "";
		int pageSize = 10;
		int pageNo = 1;
		int pageScope = 5;

		// list 생성
		List<RentVO> returnList = new ArrayList<>();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		// member에서 가져온 id
		String setmId = "170700001";
		// storage에서 가져온 id
		String setstId = "2017-item1";
		// 오늘 날짜 설정
		String rRentdate = sdf.format(date);

		for (int i = 1; i <= 3; i++){
			RentVO tempVO = new RentVO();
			tempVO.setmId(setmId);
			tempVO.setStId("2017-item" + i);
			tempVO.setrRentdate(rRentdate);

			returnList.add(tempVO);
		}

		vo.setStId(stId);
		vo.setPageNo(pageNo);
		vo.setPageSize(pageSize);

		int totalCnt = returnList.size();


		when(dao.selectRentListByMemberID(vo)).thenReturn(returnList);
		when(dao.totalCountOfRentList()).thenReturn(returnList.size());

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
	
	
}
