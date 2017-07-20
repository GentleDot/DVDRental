package net.gentledot.rental.persistance;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.gentledot.rental.persistance.TempDAO;
import net.gentledot.rental.vo.MemberVO;
import net.gentledot.rental.vo.TempVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:**/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
@Transactional(value="txManager")
public class MemberDAOTest {
	private static final Logger LOGGER = Logger.getLogger(MemberDAOTest.class);
	
	@Resource(name = "memberDao")
	private MemberDAO dao;
	
	private MemberVO vo;

	@Before
	public void setUp(){
		vo = new MemberVO();
	}
	
	/*회원 가입 테스트*/
	@Rollback(true)
	@Test
	public void addMemberTest(){
		// id = 가입년월+00001
		vo.setmId("170700001");
		vo.setmName("홍길동");
		// 생년월일 = YYMMDD
		vo.setmBirth("001222");
		// 가입일 = YYMMDD
		vo.setmJoinDate("170720");
		vo.setmAddr("서울시 서대문구");
		// 전화번호 = 000-0000-0000 또는 000-000-0000 에서 하이픈 제외
		vo.setmPhone("01022223333");
		vo.setmMail("test@test.te");
		// 빌릴 수 있는 한도 (최대 5)
		vo.setmLimit("5");
		// 회원 상태 : M = 회원, O = 탈퇴
		vo.setmStatus("M");
		
		int resultStatus = dao.addMember(vo);
		
		assertThat(resultStatus == 1, is(true));
	}
	
	/*회원 개별 조회 테스트*/
	@Rollback(true)
	@Test
	public void selectOneOfMemberTest(){
		vo.setmId("170700001");
		
		dao.addMember(vo);
		
		MemberVO resultVO = dao.selectOneOfMember(vo);
		String memberID = resultVO.getmId();
		assertThat(memberID, is("170700001"));
	}
	
	/*회원 리스트 조회 테스트*/
	@Rollback(true)
	@Test
	public void selectMemberListTest(){
		// mId를 170700001 부터 170700010까지 입력
		for(int i = 1; i <= 10; i++){
			MemberVO tempVO = new MemberVO();
			NumberFormat nf = new DecimalFormat("00000");
			String idStr = nf.format(i);
			tempVO.setmId("1707" + idStr);
			
			LOGGER.debug("----------------------");
			LOGGER.debug("결합한 문자 : " + "1707" + idStr);
			LOGGER.debug("----------------------");
			
			dao.addMember(tempVO);
		}
		vo.setmId("");
		List<MemberVO> resultList = dao.selectMemberList(vo);
		
		assertThat(resultList.size(), is(10));
	}
	
	/*회원 등록 여부 확인 테스트*/
	@Rollback(true)
	@Test
	public void listCountWithNameAndPhoneTest(){
		// 회원 10명 입력
		for(int i = 1; i <= 10; i++){
			MemberVO tempVO = new MemberVO();
			NumberFormat nf = new DecimalFormat("00000");
			NumberFormat nf2 = new DecimalFormat("0000");
			String idStr = nf.format(i);
			tempVO.setmId("1707" + idStr);
			tempVO.setmName("길동" + i);
			tempVO.setmPhone("0100001" + nf2.format(i));
			
			LOGGER.debug("----------------------");
			LOGGER.debug("결합한 문자 : " + "1707" + idStr);
			LOGGER.debug("생성된 전번 : " + "0100001" + nf2.format(i));
			LOGGER.debug("----------------------");
			
			dao.addMember(tempVO);
		}
		
		// 길동7 과 01000010007 번호인 사람 확인
		vo.setmName("길동7");
		vo.setmPhone("01000010007");
		
		int resultCount = dao.listCountWithNameAndPhone(vo);
		
		assertThat(resultCount, is(1));
	}
	
	/*회원 정보 업데이트 테스트*/
	@Rollback(true)
	@Test
	public void updateMemberTest(){
		MemberVO tempVO = new MemberVO();
		tempVO.setmId("170700001");
		tempVO.setmName("홍길동");
		tempVO.setmBirth("001222");
		tempVO.setmJoinDate("170720");
		tempVO.setmAddr("서울시 서대문구");
		tempVO.setmPhone("01022223333");
		tempVO.setmMail("test@test.te");
		tempVO.setmLimit("5");
		tempVO.setmStatus("M");
		
		dao.addMember(tempVO);
		
		vo.setmId("170700001");
		vo.setmName("고길동");
		vo.setmAddr("서울시 강남구");
		
		int resultStatus = dao.updateMember(vo);
		
		MemberVO searchVO = dao.selectOneOfMember(vo);
		String updatedAddr = searchVO.getmAddr();
		
		assertThat(updatedAddr, is("서울시 강남구"));
	}
	
	/*회원 탈퇴 처리 테스트*/
	@Rollback(true)
	@Test
	public void leaveMemberTest(){
		MemberVO tempVO = new MemberVO();
		tempVO.setmId("170700001");
		tempVO.setmName("홍길동");
		tempVO.setmBirth("001222");
		tempVO.setmJoinDate("170720");
		tempVO.setmAddr("서울시 서대문구");
		tempVO.setmPhone("01022223333");
		tempVO.setmMail("test@test.te");
		tempVO.setmLimit("5");
		tempVO.setmStatus("M");
		
		dao.addMember(tempVO);
		
		vo.setmId("170700001");
		vo.setmStatus("O");
		
		int resultStatus = dao.leaveMember(vo);
		
		MemberVO searchVO = dao.selectOneOfMember(vo);
		String memberStatus = searchVO.getmStatus();
		
		assertThat(memberStatus, is("O"));
	}
	
	/*회원 정보 삭제 처리 테스트*/
	@Rollback(true)
	@Test
	public void delMemberTest(){
		MemberVO tempVO = new MemberVO();
		tempVO.setmId("170700001");
		tempVO.setmName("홍길동");
		tempVO.setmBirth("001222");
		tempVO.setmJoinDate("170720");
		tempVO.setmAddr("서울시 서대문구");
		tempVO.setmPhone("01022223333");
		tempVO.setmMail("test@test.te");
		tempVO.setmLimit("5");
		tempVO.setmStatus("M");
		
		dao.addMember(tempVO);
		
		vo.setmId("170700001");
		
		int resultStatus = dao.delMember(vo);
		
		MemberVO searchVO = dao.selectOneOfMember(vo);
		
		// searchVO가 없는 경우 null, 존재하는 경우 false 반환
		assertNull(searchVO);
	}
	
}
