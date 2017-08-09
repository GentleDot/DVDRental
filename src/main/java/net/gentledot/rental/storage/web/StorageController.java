package net.gentledot.rental.storage.web;

import net.gentledot.rental.product.service.ProductService;
import net.gentledot.rental.sales.service.SalesService;
import net.gentledot.rental.storage.service.StorageService;
import net.gentledot.rental.vo.ProductVO;
import net.gentledot.rental.vo.SalesVO;
import net.gentledot.rental.vo.StorageVO;
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
public class StorageController {
	private static final Logger LOGGER = Logger.getLogger(StorageController.class);
	private static final int PAGE_SIZE = 10;
	private static final int PAGE_SCOPE = 5;
	
	@Resource(name="storageService")
	private StorageService storageService;

	@Resource(name="productService")
	private ProductService productService;

	@Resource(name="salesService")
	private SalesService salesService;

	
	@RequestMapping("/storage/storageList.do")
	public String selectStorageList(@RequestParam HashMap<String, String> req, ModelMap model){
		String stId = Tools.customToEmptyBlank(req.get("stId"), "");
		String getStageNo = Tools.customToEmptyBlank(req.get("pageNo"), "1");
		
		int pageNo = Integer.parseInt(getStageNo);
		
		Map<String, Object> resultMap = storageService.getStorageList(stId, PAGE_SIZE, pageNo, PAGE_SCOPE);
		
		List<StorageVO> resultList = (List<StorageVO>) resultMap.get("resultList");
		Pagination pag = (Pagination) resultMap.get("pagination");

		List<ProductVO> productList = productService.selectTotalProductList();
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("pagination", pag);
		model.addAttribute("stId", stId);
		model.addAttribute("pageNo", pageNo);
		
		return "rental/storage/list";
	}

	@RequestMapping("/storage/addStorageView.do")
	public String addStorageView(@RequestParam HashMap<String, String> req, ModelMap model){
		List<ProductVO> productList = productService.selectTotalProductList();

		model.addAttribute("productList", productList);

		return "rental/storage/insert";
	}

	@RequestMapping("/storage/addStorage.do")
	public String addStorageInList(@RequestParam HashMap<String, String> req, ModelMap model){
		String stGetdate= Tools.customToEmptyBlank(req.get("inputStGetdate"), "");
		String pId= Tools.customToEmptyBlank(req.get("inputPid"), "");

		if (stGetdate.equals("") || pId.equals("")){
			return "redirect:/storage/addStorageView.do";
		}

		StorageVO insertVO = new StorageVO();
		insertVO.setStGetdate(stGetdate);
		insertVO.setpId(pId);

		int resultStatus = storageService.addStorage(insertVO);

		// 매출 목록에 구입비 추가
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		ProductVO productVO = new ProductVO();
		productVO.setpId(pId);
		ProductVO oneOfProduct = productService.selectOneOfProduct(productVO);

		String pPrice = oneOfProduct.getpPrice();

		String curDate = sdf.format(date);

		SalesVO salesData = new SalesVO();
		salesData.setsDate(curDate);
		salesData.setpId(pId);
		salesData.setsPrice(pPrice);
		salesService.addPutPrice(salesData);
		
		return "redirect:/storage/storageList.do";
	}

	@RequestMapping("/storage/storageInfo.do")
	public String selectOneOfStorage(@RequestParam HashMap<String, String> req, ModelMap model){
		String stId = req.get("stId");
		StorageVO vo = new StorageVO();
		vo.setStId(stId);
		
		StorageVO selectedStorage = storageService.getOneOfStorage(vo);
		
		model.addAttribute("oneOfStorage", selectedStorage);
		
		return "rental/storage/info";
	}


	@RequestMapping("/storage/storageModifyView.do")
	public String updateStorageInfoView(@RequestParam HashMap<String, String> req, ModelMap model){
		String stId = req.get("stId");
		StorageVO vo = new StorageVO();
		vo.setStId(stId);

		StorageVO selectedStorage = storageService.getOneOfStorage(vo);
		
		model.addAttribute("oneOfStorage", selectedStorage);
		
		return "rental/storage/modify";
	}


	@RequestMapping("/storage/storageModify.do")
	public String updateStorageInfo(@RequestParam HashMap<String, String> req, ModelMap model){
		
		LOGGER.debug("==================");
		LOGGER.debug(" 처리 확인! ");
		LOGGER.debug("==================");

		String stId = req.get("getStId");
		String stGetdate = Tools.customToEmptyBlank(req.get("getStGetdate"), "99999999");
		String pId = Tools.customToEmptyBlank(req.get("getPId"), "1");
		String stStatus = Tools.customToEmptyBlank(req.get("getStStatus"), "정상");
		String stWastedate = Tools.customToEmptyBlank(req.get("getStWastedate"), "");
		String stWastecost = Tools.customToEmptyBlank(req.get("getStWastecost"), "");
		String stWasteReason = Tools.customToEmptyBlank(req.get("getStWasteReason"), "");

		LOGGER.debug("==================");
		LOGGER.debug(" 입력 받은 상품 상태 : " +  req.get("getStStatus"));
		LOGGER.debug(" 상품 상태 : " +  stStatus);
		LOGGER.debug("==================");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

		Date tempWastedate = null;
		if (!(stWastedate.equals(""))){
			try {
				tempWastedate = sdf.parse(stWastedate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		stWastedate = stWastedate.equals("") ? stWastedate : sdf2.format(tempWastedate);

		if(stStatus.equals("파손") || stStatus.equals("분실")) {
			if(stWastedate.equals("") || stWastecost.equals("")){
				return "redirect:/storage/storageModify.do?stId=" + stId;
			}
		}

		StorageVO updateVO = new StorageVO();
		updateVO.setStId(stId);
		updateVO.setStGetdate(stGetdate);
		updateVO.setpId(pId);
		updateVO.setStStatus(stStatus);
		updateVO.setStWastedate(stWastedate);
		updateVO.setStWastecost(stWastecost);
		updateVO.setStWasteReason(stWasteReason);
		
		int resultStatus = storageService.updateStorage(updateVO);

		// 파손 또는 분실 상태인 경우 매출 목록에 폐기비용 추가
		if(stStatus.equals("파손") || stStatus.equals("분실")){
			SalesVO salesData = new SalesVO();
			salesData.setsDate(stWastedate);
			salesData.setStId(stId);
			salesData.setsWasteCost(stWastecost);
			int result = salesService.addPutWastecost(salesData);
			
			LOGGER.debug("==================");
			LOGGER.debug(" 매출 데이터 입력 확인 : " +  result);
			LOGGER.debug("==================");
		}
		
		return "redirect:/storage/storageList.do";
	}

	/*
	@RequestMapping("/storage/storageDel.do")
	public String delStorage(@RequestParam HashMap<String, String> req, ModelMap model){
		String pId = req.get("pId");

		StorageVO vo = new StorageVO();

		vo.setpId(pId);
		int resultStatus = storageService.delStorage(vo);

		return "redirect:/storage/storageList.do";
	}

	*/
}
