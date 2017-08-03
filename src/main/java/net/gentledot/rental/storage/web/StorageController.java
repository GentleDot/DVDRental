package net.gentledot.rental.storage.web;

import net.gentledot.rental.product.service.ProductService;
import net.gentledot.rental.storage.service.StorageService;
import net.gentledot.rental.vo.ProductVO;
import net.gentledot.rental.vo.StorageVO;
import net.gentledot.utils.Pagination;
import net.gentledot.utils.Tools;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
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
		String stGetdate= Tools.customToEmptyBlank(req.get("inputStGetdate"), "99999999");
		String pId= Tools.customToEmptyBlank(req.get("inputPid"), "1");


		StorageVO insertVO = new StorageVO();
		insertVO.setStGetdate(stGetdate);
		insertVO.setpId(pId);

		int resultStatus = storageService.addStorage(insertVO);
		
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

		String stId = req.get("getStId");
		String stGetdate = Tools.customToEmptyBlank(req.get("getStGetdate"), "99999999");
		String pId = Tools.customToEmptyBlank(req.get("getPId"), "1");
		String stStatus = Tools.customToEmptyBlank(req.get("getStStatus"), "정상");
		String stWastedate = Tools.customToEmptyBlank(req.get("getStWastedate"), "");
		String stWastecost = Tools.customToEmptyBlank(req.get("getStWastecost"), "");
		String stWasteReason = Tools.customToEmptyBlank(req.get("getStWasteReason"), "");

		StorageVO updateVO = new StorageVO();
		updateVO.setStId(stId);
		updateVO.setStGetdate(stGetdate);
		updateVO.setpId(pId);
		updateVO.setStStatus(stStatus);
		updateVO.setStWastedate(stWastedate);
		updateVO.setStWastecost(stWastecost);
		updateVO.setStWasteReason(stWasteReason);
		
		int resultStatus = storageService.updateStorage(updateVO);
		
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
