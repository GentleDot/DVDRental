package net.gentledot.rental.persistance;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import net.gentledot.rental.vo.StorageVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gd on 2017-07-28.
 */
@Repository(value = "storageDao")
public class StorageDAO extends EgovAbstractMapper {
    public List<StorageVO> selectStorageList(StorageVO vo){
        return selectList("selectStorageList", vo);
    }

    public int totalCountOfStorageList(){
        return selectOne("totalCountOfStorageList");
    }

    public List<StorageVO> totalStorageItems(){
        return selectList("totalStorageItems");
    }

    public StorageVO selectOneOfStorage(StorageVO vo){
        return selectOne("selectOneOfStorage", vo);
    }

    public int addStorage(StorageVO vo){
        return insert("addStorage", vo);
    }

    public int updateStorage(StorageVO vo){
        return update("updateStorage", vo);
    }

    /*public int delStorage(StorageVO vo){
        return delete("delStorage", vo);
    }*/

}
