package net.gentledot.rental.persistance;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import net.gentledot.rental.vo.IdsVO;
import net.gentledot.rental.vo.MemberVO;
import net.gentledot.rental.vo.TempVO;

@Repository("idsDao")
public class IdsDAO extends EgovAbstractMapper {
	public String getNextId(String tableName){
		return selectOne("getNextId", tableName);
	}
	
	public int addNextId(IdsVO vo){
		return insert("addNextId", vo);
	}
	
	public int updateNextId(String tableName){
		return update("updateNextId", tableName);
	}
}
