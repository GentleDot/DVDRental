package net.gentledot.rental.persistance;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import net.gentledot.rental.vo.MemberVO;
import net.gentledot.rental.vo.TempVO;

@Repository("memberDao")
public class MemberDAO extends EgovAbstractMapper {
	public List<MemberVO> selectMemberList(MemberVO vo){
		return selectList("selectMemberList", vo);
	}
	
	public int totalCountOfMemberList(MemberVO vo){
		return selectOne("totalCountOfMemberList", vo);
	}
	
	public int listCountWithNameAndPhone(MemberVO vo){
		return selectOne("listCountWithNameAndPhone", vo);
	}
	
	public MemberVO selectOneOfMember(MemberVO vo){
		return selectOne("selectOneOfMember", vo);
	}
	
	public int addMember(MemberVO vo){
		return insert("addMember", vo);
	}
	
	public int updateMember(MemberVO vo){
		return update("updateMember", vo);
	}
	
	public int leaveMember(MemberVO vo){
		return update("leaveMember", vo);
	}
	
	public int delMember(MemberVO vo){
		return delete("delMember", vo);
	}
}
