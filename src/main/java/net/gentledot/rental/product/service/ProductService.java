package net.gentledot.rental.product.service;

import net.gentledot.rental.vo.ProductVO;

import java.util.Map;

/**
 * Created by gd on 2017-07-26.
 */
public interface ProductService {
    public Map<String, Object> getProductList(String pId, int pageSize, int pageNo, int pageScope);
    public Map<String, Object> getProductListByName(String pName, int pageSize, int pageNo, int pageScope);
    public int addProduct(ProductVO vo);
    public ProductVO selectOneOfProduct(ProductVO vo);
    public int updateProduct(ProductVO vo);
    public int delProduct(ProductVO vo);
}
