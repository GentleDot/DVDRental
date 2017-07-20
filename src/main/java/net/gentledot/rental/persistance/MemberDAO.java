package net.gentledot.rental.persistance;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import net.gentledot.rental.vo.MemberVO;
import net.gentledot.rental.vo.TempVO;

@Repository(value = "memberDao")
public class MemberDAO extends EgovAbstractMapper {
	List<MemberVO> selectMemberList(MemberVO vo){
		return selectList("selectMemberList", vo);
	}
	
	int listCountWithNameAndPhone(MemberVO vo){
		return selectOne("listCountWithNameAndPhone", vo);
	}
	
	MemberVO selectOneOfMember(MemberVO vo){
		return selectOne("selectOneOfMember", vo);
	}
	
	int addMember(MemberVO vo){
		return insert("addMember", vo);
	}
	
	int updateMember(MemberVO vo){
		return update("updateMember", vo);
	}
	
	int leaveMember(MemberVO vo){
		return update("leaveMember", vo);
	}
	
	int delMember(MemberVO vo){
		return delete("delMember", vo);
	}
}
