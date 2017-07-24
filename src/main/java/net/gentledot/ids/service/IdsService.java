package net.gentledot.ids.service;

import net.gentledot.rental.vo.IdsVO;

public interface IdsService {

	public int addNextId(IdsVO vo);
	public String getNextId(String tableName);

}
