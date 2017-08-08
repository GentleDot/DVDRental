package net.gentledot.rental.product.service.impl;

import net.gentledot.ids.service.IdsService;
import net.gentledot.rental.persistance.ProductDAO;
import net.gentledot.rental.product.service.ProductService;
import net.gentledot.rental.vo.ProductVO;
import net.gentledot.utils.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gd on 2017-07-26.
 */
@Service(value = "productService")
public class ProductServiceImpl implements ProductService {
    @Resource(name = "productDao")
    private ProductDAO dao;

    @Resource(name = "idsService")
    private IdsService idsService;


    public Map<String, Object> getProductList(String pId, int pageSize, int pageNo, int pageScope){

        ProductVO vo = new ProductVO();
        vo.setpId(pId);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);

        // list 생성
        List<ProductVO> returnList = dao.selectProductList(vo);
        // list 갯수 확인
        int totalCnt = dao.totalCountOfProductList();

        // pagination 생성
        Pagination pag = new Pagination(pageSize, pageNo, pageScope, totalCnt);
        pag.setPaging();

        // return 할 map에 list와 pagination 담기
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("resultList", returnList);
        resultMap.put("pagination", pag);

        return resultMap;
    }

    public Map<String, Object> getProductListByName(String pName, int pageSize, int pageNo, int pageScope){
        ProductVO vo = new ProductVO();

        vo.setpName(pName);
        vo.setPageNo(pageNo);
        vo.setPageSize(pageSize);

        // list 생성
        List<ProductVO> returnList = dao.selectProductListByName(vo);
        int totalCnt = dao.totalCountOfProductList();

        // pagination 생성
        Pagination pag = new Pagination(pageSize, pageNo, pageScope, totalCnt);
        pag.setPaging();

        // return 할 map에 list와 pagination 담기
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("resultList", returnList);
        resultMap.put("pagination", pag);

        return resultMap;
    }

    @Override
    public List<ProductVO> selectTotalProductList() {
        return dao.selectTotalProductList();
    }

    public int addProduct(ProductVO vo){
        return dao.addProduct(vo);
    }

    public ProductVO selectOneOfProduct(ProductVO vo){
        return dao.selectOneOfProduct(vo);
    }

    public int updateProduct(ProductVO vo){
        return dao.updateProduct(vo);
    }

    public int delProduct(ProductVO vo){
        return dao.delProduct(vo);
    }




}
