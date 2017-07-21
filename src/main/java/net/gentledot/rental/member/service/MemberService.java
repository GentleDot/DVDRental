package net.gentledot.rental.member.service;

import java.util.Map;

public interface MemberService {

	Map<String, Object> getMemberList(String mId, int pageSize, int pageNo, int pageRange);
	
}
