package com.tg.OnlineShoppingMall.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tg.OnlineShoppingMall.domin.Commodity;
import com.tg.OnlineShoppingMall.domin.Store;
import com.tg.OnlineShoppingMall.domin.User;
import com.tg.OnlineShoppingMall.service.CommodityService;
import com.tg.OnlineShoppingMall.service.StoreService;
import com.tg.OnlineShoppingMall.tools.AliOssUtils;
import com.tg.OnlineShoppingMall.tools.JsonData;

/**
 * 返回店铺的数据接口,响应浏览器请求
 * 
 * @author follow_tg
 *
 */

@RestController
@PreAuthorize("hasAuthority('ROLE_TG')")
public class StoreController {

	@Autowired
	private StoreService storeService;

	@Autowired
	private CommodityService commodityService;

	@GetMapping("/registershop")
	public ModelAndView regStore(Model model) {
		
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// 最热商品 前五
		Sort commoditySort = new Sort(Direction.DESC, "browse", "joinShoppingCart");
		Pageable commodityPage = PageRequest.of(0, 5, commoditySort);
		Page<Commodity> page1 = commodityService.PageCommodityByIfground(true, commodityPage);
		List<Commodity> hotCommodities = page1.getContent();

		// 最热店铺 前五
		Sort storeSort = new Sort(Direction.DESC, "visit");
		Pageable storePage = PageRequest.of(0, 5, storeSort);
		Page<Store> page2 = storeService.getHotStoreByVisit(storePage);
		List<Store> hotStores = page2.getContent();
		
		model.addAttribute("hasStore", loginUser.getHasStore());
		model.addAttribute("hotCommodities", hotCommodities);
		model.addAttribute("hotStores", hotStores);
		return new ModelAndView("registershop");
	}
	
	
	//管理店铺的商品
	@GetMapping("/store/commodity")
	public ModelAndView commodity(Model model) {
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Store store = storeService.getStoreByUsername(loginUser.getUsername());
		List<Commodity> commodities = commodityService.ListCommodityByStorename(store.getStorename());
		model.addAttribute("user", loginUser);
		model.addAttribute("store", store);
		model.addAttribute("commodities", commodities);
		return new ModelAndView("store/commodity");
	}

	@GetMapping("/store/publish")
	public ModelAndView publish(Model model) {
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Store store = storeService.getStoreByUsername(loginUser.getUsername());
		model.addAttribute("user", loginUser);
		model.addAttribute("store", store);
		return new ModelAndView("store/publish");
	}

	@PostMapping("/regCommodity")
	public JsonData regCommodity(@RequestParam MultipartFile file, String commodityname, String username, int price, String type, String info) {
		System.out.println(price);
		JsonData jsonData;
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String fileName = file.getOriginalFilename();
		String path = "user/" + "945614282@qq.com" + "/files/" + fileName;
		try {
			System.out.println(path);
			AliOssUtils a = new AliOssUtils();
			String picture = a.upload(file, "senlear/" + path);
			Store store=storeService.getStoreByUsername(loginUser.getUsername());
			Commodity commodity = new Commodity(commodityname, store.getStorename(), price, type, info, picture,store.getId());
			commodityService.save(commodity);
			jsonData = new JsonData("200", "Success", "上传成功!", "tg");
		} catch (Exception e) {
			e.printStackTrace();
			jsonData = new JsonData("201", "Error", "上传出错!", "tg");
		}
		return jsonData;

	}

	@PostMapping("/regStore")
	public JsonData regStore(@RequestParam MultipartFile file, String phone, String storename,
			String email, int type, String info) {
		System.out.println(file.getOriginalFilename());
		JsonData jsonData;
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(loginUser.getHasStore()==true) {
			return jsonData = new JsonData("201", "Error", "您已有商铺，请勿重复注册！", "tg");
		}
		String fileName = file.getOriginalFilename();
		String path = "user/" + "945614282@qq.com" + "/files/" + fileName;
		try {
			System.out.println(path);
			AliOssUtils a = new AliOssUtils();
			String fileUrl = a.upload(file, "senlear/" + path);
			Store store = new Store(storename, phone, email, loginUser.getUsername(), type, info, fileUrl);
			loginUser.setHasStore(true);
			storeService.saveStore(store);
			jsonData = new JsonData("200", "Success", "上传成功!", "tg");
		} catch (Exception e) {
			e.printStackTrace();
			jsonData = new JsonData("201", "Error", "上传出错!", "tg");
		}
		return jsonData;
	}

	@PostMapping("/ground")
	public String ground(HttpServletRequest request, int type, Long id) {
		String callback = request.getParameter("callback");
		JsonData jsonData;
		Commodity commodity;
		if (type == 1) {
			commodity = commodityService.getCommodityById(id);
			commodity.setIfground(true);
			commodityService.save(commodity);
		} else {
			commodity = commodityService.getCommodityById(id);
			commodity.setIfground(false);
			commodityService.save(commodity);
		}
		jsonData = new JsonData("200", "更改成功！", "yes", callback);
		return jsonData.toString();
	}

}
