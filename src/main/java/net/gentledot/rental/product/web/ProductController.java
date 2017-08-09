package net.gentledot.rental.product.web;

import net.gentledot.ids.service.IdsService;
import net.gentledot.rental.product.service.ProductService;
import net.gentledot.rental.sales.service.SalesService;
import net.gentledot.rental.vo.ProductVO;
import net.gentledot.rental.vo.SalesVO;
import net.gentledot.utils.Pagination;
import net.gentledot.utils.Tools;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
	private static final Logger LOGGER = Logger.getLogger(ProductController.class);
	private static final int PAGE_SIZE = 10;
	private static final int PAGE_SCOPE = 5;
	
	@Resource(name="productService")
	private ProductService productService;

	@Resource(name="salesService")
	private SalesService salesService;

	@Resource(name="idsService")
	private IdsService idsService;
	
	@RequestMapping("/product/productList.do")
	public String selectProductList(@RequestParam HashMap<String, String> req, ModelMap model){
		LOGGER.debug("----------------------");
		LOGGER.debug("검색 설정 : " + req.get("category"));
		LOGGER.debug("검색 키워드 : " + req.get("keyword"));
		LOGGER.debug("----------------------");
		
		String category = Tools.toEmptyBlank(req.get("category"));
		String keyword = Tools.toEmptyBlank(req.get("keyword"));
		String getPageNo = Tools.customToEmptyBlank(req.get("pageNo"), "1");
		
		int pageNo = Integer.parseInt(getPageNo);
		
		Map<String, Object> resultMap = null;
		
		switch (category) {
		case "name":
			resultMap = productService.getProductListByName(keyword, PAGE_SIZE, pageNo, PAGE_SCOPE);
			break;
		default:
			resultMap = productService.getProductList(keyword, PAGE_SIZE, pageNo, PAGE_SCOPE);
			break;
		}
		
		List<ProductVO> resultList = (List<ProductVO>) resultMap.get("resultList");
		Pagination pag = (Pagination) resultMap.get("pagination");
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("pagination", pag);
		model.addAttribute("keyword", keyword);
		model.addAttribute("category", category);
		model.addAttribute("pageNo", pageNo);
		
		return "rental/product/list";
	}
	
	@RequestMapping("/product/addProductView.do")
	public String addProductView(@RequestParam HashMap<String, String> req, ModelMap model){
		
		return "rental/product/insert";
	}
	
	@RequestMapping("/product/addProduct.do")
	public String addProductInList(@RequestParam HashMap<String, String> req, ModelMap model){
		String pName= Tools.customToEmptyBlank(req.get("inputPName"), "");
		String pPrice = Tools.customToEmptyBlank(req.get("inputPPrice"), "0");
		String pGrade = Tools.customToEmptyBlank(req.get("inputPGrade"), "all");

		if(pName.equals("") || pPrice.equals("")){
			return "redirect:/product/addProductView.do";
		}

		/*id를 idsService에서 받아온 뒤 그 번호를 양식에 맞게 변형하여 VO로 전달*/
		String tableName = "product";
		String pId = idsService.getNextId(tableName);


		ProductVO insertVO = new ProductVO();		insertVO.setpId(pId);
		insertVO.setpName(pName);
		insertVO.setpPrice(pPrice);
		insertVO.setpGrade(pGrade);

		int resultStatus = productService.addProduct(insertVO);
		
		return "redirect:/product/productList.do";
	}

	/*@RequestMapping("/product/productInfo.do")
	public String selectOneOfProduct(@RequestParam HashMap<String, String> req, ModelMap model){
		String pId = Tools.customToEmptyBlank(req.get("pId"), "");
		ProductVO vo = new ProductVO();
		vo.setpId(pId);
		
		ProductVO selectedProduct = productService.selectOneOfProduct(vo);
		
		model.addAttribute("oneOfProduct", selectedProduct);
		
		return "rental/product/info";
	}*/


	@RequestMapping("/product/productModifyView.do")
	public String updateProductInfoView(@RequestParam HashMap<String, String> req, ModelMap model){
		String pId = Tools.customToEmptyBlank(req.get("pId"), "");
		
		ProductVO vo = new ProductVO();
		vo.setpId(pId);
		
		ProductVO selectedProduct = productService.selectOneOfProduct(vo);
		
		model.addAttribute("oneOfProduct", selectedProduct);
		
		return "rental/product/modify";
	}


	@RequestMapping("/product/productModify.do")
	public String updateProductInfo(@RequestParam HashMap<String, String> req, ModelMap model){

		String pId = req.get("getPId");
		String pName = Tools.customToEmptyBlank(req.get("getPName"), "");
		String pPrice = Tools.customToEmptyBlank(req.get("getPPrice"), "0");
		String pGrade = Tools.customToEmptyBlank(req.get("getPGrade"), "ALL");

		ProductVO insertVO = new ProductVO();
		insertVO.setpId(pId);
		insertVO.setpName(pName);
		insertVO.setpPrice(pPrice);
		insertVO.setpGrade(pGrade);
		
		int resultStatus = productService.updateProduct(insertVO);
		
		return "redirect:/product/productList.do";
	}
	
	@RequestMapping("/product/productDel.do")
	public String delProduct(@RequestParam HashMap<String, String> req, ModelMap model){
		String pId = req.get("pId");

		ProductVO vo = new ProductVO();

		vo.setpId(pId);
		int resultStatus = productService.delProduct(vo);

		return "redirect:/product/productList.do";
	}

	
}
