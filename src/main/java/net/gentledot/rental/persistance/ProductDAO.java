package net.gentledot.rental.persistance;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import net.gentledot.rental.vo.ProductVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gd on 2017-07-26.
 */
@Repository(value = "productDao")
public class ProductDAO extends EgovAbstractMapper {
    public List<ProductVO> selectProductList(ProductVO vo){
        return selectList("selectProductList", vo);
    }

    public List<ProductVO> selectProductListByName(ProductVO vo){
        return selectList("selectProductListByName", vo);
    }

    public List<ProductVO> selectTotalProductList(){
        return selectList("selectTotalProductList");
    }

    public int totalCountOfProductList(){
        return selectOne("totalCountOfProductList");
    }

    public ProductVO selectOneOfProduct(ProductVO vo){
        return selectOne("selectOneOfProduct", vo);
    }

    public int addProduct(ProductVO vo){
        return insert("addProduct", vo);
    }

    public int updateProduct(ProductVO vo){
        return update("updateProduct", vo);
    }

    public int delProduct(ProductVO vo){
        return delete("delProduct", vo);
    }

}
