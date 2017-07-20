package net.gentledot.rental;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import net.gentledot.rental.persistance.TempDAO;
import net.gentledot.rental.vo.TempVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:**/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
@Transactional(value="txManager")
public class DBconnectionTest {
	
	@Resource(name = "tempDao")
	private TempDAO dao;
	
	private TempVO vo;

	@Before
	public void setUp(){
		vo = new TempVO();
	}
	
	@Test
	public void selectTempTest(){
		vo.setTempStr("a");
		
		TempVO selectedVO = dao.selectTemp(vo);
		String tempNum = selectedVO.getTempNum();
		int tempNumber = Integer.parseInt(tempNum);
		
		assertThat(tempNumber == 8, is(true));
				
	}
}
