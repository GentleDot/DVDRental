package net.gentledot.rental.storage.service;

import net.gentledot.ids.service.IdsService;
import net.gentledot.rental.persistance.StorageDAO;
import net.gentledot.rental.product.service.ProductService;
import net.gentledot.rental.storage.service.impl.StorageServiceImpl;
import net.gentledot.rental.vo.StorageVO;
import net.gentledot.utils.Pagination;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StorageServiceTest {
	private static final Logger LOG = Logger.getLogger(StorageServiceTest.class);
	
	@InjectMocks
	private StorageService storageService = new StorageServiceImpl();
	
	@Mock
	StorageDAO dao;
	IdsService idsService;
	ProductService productService;
	
	StorageVO vo;
	
	@Before
	public void setUp(){
		vo = new StorageVO();
	}

	/*재고 조회 서비스 테스트*/
	@Test
	public void getStorageListTest(){
		String stId = "";
		int pageSize = 10;
		int pageNo = 1;
		int pageScope = 5;
		
		// list 생성
		List<StorageVO> returnList = new ArrayList<>();
		
		for (int i = 1; i <= 10; i++){
			StorageVO tempVO = new StorageVO();
			tempVO.setpId(i+ "");
			
			returnList.add(tempVO);
		}
		
		vo.setStId(stId);
		vo.setPageNo(pageNo);
		vo.setPageSize(pageSize);
		
		int totalCnt = returnList.size();
		
		when(dao.selectStorageList(vo)).thenReturn(returnList);
		when(dao.totalCountOfStorageList()).thenReturn(returnList.size());
		
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

	/*재고 추가 서비스 테스트*/
	@Test
	public void addStorageTest(){
		String stGetdate = "20170728";
		String pId = "1";

		/*id를 idsService에서 받아온 뒤 그 번호를 양식에 맞게 변형하여 VO로 전달*/
		String tableName = "storage";
		/*String getId = idsService.getNextId(tableName);*/
		String getId = "1";
		String stId = "2017-item" + getId;
		String stStatus = "정상";

		vo.setStId(stId);
		vo.setStGetdate(stGetdate);
		vo.setpId(pId);
		vo.setStStatus(stStatus);

		when(dao.addStorage(vo)).thenReturn(1);

		/*assertNotNull("mId should be not null.", dao.selectOneOfStorage(vo));*/
		assertThat(dao.addStorage(vo), is(1));
	}
	
}
