package net.gentledot.rental.persistance;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import net.gentledot.rental.vo.RentVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gd on 2017-08-03.
 */
@Repository(value = "rentDao")
public class RentDAO extends EgovAbstractMapper {
    public List<RentVO> selectRentListByMemberID(RentVO vo){
        return selectList("selectRentListByMemberID", vo);
    }

    public List<RentVO> selectRentListByRentdate(RentVO vo){
        return selectList("selectRentListByRentdate", vo);
    }

    public List<RentVO> selectRentListByItemID(RentVO vo){
        return selectList("selectRentListByItemID", vo);
    }

    public List<RentVO> selectOneOfRent(RentVO vo){
        return selectList("selectOneOfRent", vo);
    }

    public RentVO checkRentDetail(RentVO vo){
        return selectOne("checkRentDetail", vo);
    }

    public int totalCountOfRentList(){
        return selectOne("totalCountOfRentList");
    }

    public int addRent(RentVO vo){
        return insert("addRent", vo);
    }

    public int updateRent(RentVO vo){
        return update("updateRent", vo);
    }

    public int delRent(RentVO vo){
        return delete("delRent", vo);
    }

}
