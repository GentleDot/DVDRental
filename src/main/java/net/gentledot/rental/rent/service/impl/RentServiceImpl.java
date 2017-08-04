package net.gentledot.rental.rent.service.impl;

import net.gentledot.ids.service.IdsService;
import net.gentledot.rental.persistance.RentDAO;
import net.gentledot.rental.rent.service.RentService;
import net.gentledot.rental.vo.RentVO;
import net.gentledot.utils.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gd on 2017-08-04.
 */
@Service(value = "rentService")
public class RentServiceImpl implements RentService{
    @Resource(name = "rentDao")
    RentDAO dao;

    @Resource(name = "idsService")
    IdsService idsService;

    public Map<String, Object> getRentListByMember(String mId, int pageSize, int pageNo, int pageScope){
        // list 생성
        RentVO vo = new RentVO();
        vo.setmId(mId);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        List<RentVO> returnList = dao.selectRentListByMemberID(vo);

        int totalCnt = dao.totalCountOfRentList();

        // pagination 생성
        Pagination pag = new Pagination(pageSize, pageNo, pageScope, totalCnt);
        pag.setPaging();

        // return 할 map에 list와 pagination 담기
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("resultList", returnList);
        resultMap.put("pagination", pag);

        return resultMap;
    }

    public Map<String, Object> getRentListByRentdate(String rRentdate, int pageSize, int pageNo, int pageScope){
        // list 생성
        RentVO vo = new RentVO();
        vo.setrRentdate(rRentdate);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        List<RentVO> returnList = dao.selectRentListByRentdate(vo);

        int totalCnt = dao.totalCountOfRentList();

        // pagination 생성
        Pagination pag = new Pagination(pageSize, pageNo, pageScope, totalCnt);
        pag.setPaging();

        // return 할 map에 list와 pagination 담기
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("resultList", returnList);
        resultMap.put("pagination", pag);

        return resultMap;
    }

    public Map<String, Object> getRentListByItem(String stId, int pageSize, int pageNo, int pageScope){
        // list 생성
        RentVO vo = new RentVO();
        vo.setStId(stId);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);
        List<RentVO> returnList = dao.selectRentListByItemID(vo);

        int totalCnt = dao.totalCountOfRentList();

        // pagination 생성
        Pagination pag = new Pagination(pageSize, pageNo, pageScope, totalCnt);
        pag.setPaging();

        // return 할 map에 list와 pagination 담기
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("resultList", returnList);
        resultMap.put("pagination", pag);

        return resultMap;
    }

    public List<RentVO> selectOneOfRent(RentVO vo){
        return dao.selectOneOfRent(vo);
    }

    public RentVO checkRentDetail(RentVO vo){
        return dao.checkRentDetail(vo);
    }

    public int addRent(RentVO vo){
        return dao.addRent(vo);
    }

    public int updateRent(RentVO vo){
        return dao.updateRent(vo);
    }

    public int delRent(RentVO vo){
        return dao.delRent(vo);
    }

}
