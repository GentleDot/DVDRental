package net.gentledot.rental.persistance;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import net.gentledot.rental.vo.IdsVO;
import net.gentledot.rental.vo.MemberVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:**/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
@Transactional(value="txManager")
public class IdsDAOTest {
	private static final Logger LOGGER = Logger.getLogger(IdsDAOTest.class);
	
	@Resource(name = "idsDao")
	IdsDAO dao;
	
	private IdsVO vo;

	@Before
	public void setUp(){
		vo = new IdsVO();
	}
	
	/*입력 테스트*/
	@Rollback(true)
	@Test
	public void addNextIdTest(){
		vo.setNextId(10);
		vo.setTableName("test2");
		
		int resultStatus = dao.addNextId(vo);
		
		assertThat(resultStatus == 1, is(true));
	}
	
	/*조회 테스트*/
	@Rollback(true)
	@Test
	public void getNextIdTest(){
		String tableName = "test";
		
		String nextId = dao.getNextId(tableName);

		assertThat(nextId, is("1"));
	}
	
	/*변경 테스트*/
	@Rollback(true)
	@Test
	public void updateNextIdTest(){
		String tableName = "test";
		
		int resultStatus = dao.updateNextId(tableName);
		
		String nextId = dao.getNextId(tableName);
		
		assertThat(nextId, is("2"));
	}
	
	
}
