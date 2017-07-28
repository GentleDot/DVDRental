package net.gentledot.rental.storage.service.impl;

import net.gentledot.ids.service.IdsService;
import net.gentledot.rental.persistance.StorageDAO;
import net.gentledot.rental.storage.service.StorageService;
import net.gentledot.rental.vo.StorageVO;
import net.gentledot.utils.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gd on 2017-07-28.
 */
@Service(value = "storageService")
public class StorageServiceImpl implements StorageService{
    @Resource(name = "storageDao")
    StorageDAO dao;

    @Resource(name = "idsService")
    IdsService idsService;

    public Map<String, Object> getStorageList(String stId, int pageSize, int pageNo, int pageScope){
        // list 생성
        StorageVO vo = new StorageVO();
        vo.setPageNo(pageNo);
        vo.setStId(stId);
        vo.setPageSize(pageSize);
        List<StorageVO> returnList = dao.selectStorageList(vo);

        int totalCnt = dao.totalCountOfStorageList();

        // pagination 생성
        Pagination pag = new Pagination(pageSize, pageNo, pageScope, totalCnt);
        pag.setPaging();

        // return 할 map에 list와 pagination 담기
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("resultList", returnList);
        resultMap.put("pagination", pag);

        return resultMap;
    }

    public StorageVO getOneOfStorage(StorageVO vo){
        return dao.selectOneOfStorage(vo);
    }

    public int addStorage(StorageVO vo){
        /*String stGetdate = "20170728";
        String pId = "1";*/
        Calendar cal = Calendar.getInstance();
        int curYear = cal.get(Calendar.YEAR);

        String tableName = "storage";
		String getId = idsService.getNextId(tableName);
        String stId = curYear + "-item" + getId;
        String stStatus = "정상";

        vo.setStId(stId);
        vo.setStStatus(stStatus);

        return dao.addStorage(vo);
    }

    public int updateStorage(StorageVO vo){
        return dao.updateStorage(vo);
    }

    /*public int delStorage(StorageVO vo){
        return dao.delStorage(vo);
    }*/

}
