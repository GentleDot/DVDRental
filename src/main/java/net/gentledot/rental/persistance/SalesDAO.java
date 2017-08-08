package net.gentledot.rental.persistance;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import net.gentledot.rental.vo.SalesVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gd on 2017-08-07.
 */
@Repository(value = "salesDao")
public class SalesDAO extends EgovAbstractMapper {
    public List<SalesVO> selectSalesList(SalesVO vo){
        return selectList("selectSalesList", vo);
    }

    public int totalCountOfSalesList(){
        return selectOne("totalCountOfSalesList");
    }

    public int addGetCharge(SalesVO vo){
        return insert("addGetCharge", vo);
    }
    public int addGetArrears(SalesVO vo){
        return insert("addGetArrears", vo);
    }
    public int addPutPrice(SalesVO vo){
        return insert("addPutPrice", vo);
    }
    public int addPutWastecost(SalesVO vo){
        return insert("addPutWastecost", vo);
    }

}
