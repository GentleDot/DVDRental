package net.gentledot.ids.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.gentledot.ids.service.IdsService;
import net.gentledot.rental.persistance.IdsDAO;
import net.gentledot.rental.vo.IdsVO;

@Service("idsService")
public class IdsServiceImpl implements IdsService{
	@Resource(name = "idsDao")
	IdsDAO dao;
	
	public int addNextId(IdsVO vo){
		return dao.addNextId(vo);
	}
	
	public String getNextId(String tableName){
		String nextId = dao.getNextId(tableName);
		dao.updateNextId(tableName);
		return nextId;
	}
}

