package net.gentledot.rental.rent.service;

import net.gentledot.rental.vo.RentVO;

import java.util.List;
import java.util.Map;

public interface RentService {
	public Map<String, Object> getRentListByMember(String mId, int pageSize, int pageNo, int pageScope);
	public Map<String, Object> getRentListByRentdate(String rRentdate, int pageSize, int pageNo, int pageScope);
	public Map<String, Object> getRentListByItem(String stId, int pageSize, int pageNo, int pageScope);
	public List<RentVO> selectOneOfRent(RentVO vo);
	public RentVO checkRentDetail(RentVO vo);
	public int addRent(RentVO vo);
	public int updateRent(RentVO vo);
	public int delRent(RentVO vo);
}
