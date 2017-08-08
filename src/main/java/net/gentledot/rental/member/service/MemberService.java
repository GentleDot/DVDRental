package net.gentledot.rental.member.service;

import java.util.Map;

import net.gentledot.rental.vo.MemberVO;

public interface MemberService {

	public Map<String, Object> getMemberList(String mId, int pageSize, int pageNo, int pageScope);
	public Map<String, Object> getMemberListByName(String mName, int pageSize, int pageNo, int pageScope);
	public int listCountWithNameAndPhone(MemberVO vo);
	public MemberVO selectOneOfMember(MemberVO vo);
	public int addMember(MemberVO vo);
	public int updateMember(MemberVO vo);
	public int leaveMember(MemberVO vo);
	public int delMember(MemberVO vo);
	public int decreaseLimit(MemberVO vo);
	public int increaseLimit(MemberVO vo);
}
