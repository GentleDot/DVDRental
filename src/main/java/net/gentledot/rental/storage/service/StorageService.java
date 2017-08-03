package net.gentledot.rental.storage.service;

import net.gentledot.rental.vo.StorageVO;

import java.util.Map;

/**
 * Created by gd on 2017-07-28.
 */
public interface StorageService {
    public Map<String, Object> getStorageList(String stId, int pageSize, int pageNo, int pageScope);
    public StorageVO getOneOfStorage(StorageVO vo);
    public int addStorage(StorageVO vo);
    public int updateStorage(StorageVO vo);
}
