package net.gentledot.rental.persistance;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import net.gentledot.rental.vo.TempVO;

@Repository(value = "tempDao")
public class TempDAO extends EgovAbstractMapper {
	public TempVO selectTemp(TempVO vo){
		return selectOne("selectTemp", vo);
	}
}
