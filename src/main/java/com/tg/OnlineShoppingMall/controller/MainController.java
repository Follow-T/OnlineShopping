package com.tg.OnlineShoppingMall.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tg.OnlineShoppingMall.domin.Authority;
import com.tg.OnlineShoppingMall.domin.Commodity;
import com.tg.OnlineShoppingMall.domin.Store;
import com.tg.OnlineShoppingMall.domin.User;
import com.tg.OnlineShoppingMall.domin.UserScan;
import com.tg.OnlineShoppingMall.service.AuthorityService;
import com.tg.OnlineShoppingMall.service.CommodityService;
import com.tg.OnlineShoppingMall.service.StoreService;
import com.tg.OnlineShoppingMall.service.UserScanService;
import com.tg.OnlineShoppingMall.service.UserService;
import com.tg.OnlineShoppingMall.tools.CaptchaUtil;
import com.tg.OnlineShoppingMall.tools.JsonData;

/**
 * 首页控制器，负责首页的请求
 * 
 * @author follow_tg
 *
 */
@RestController
public class MainController {

	@Autowired
	private UserService userService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private CommodityService commodityService;

	@Autowired
	private UserScanService userScanService;

	@GetMapping("/")
	public ModelAndView toIndex(Model model) {
		return new ModelAndView("redirect:index");
	}

	@GetMapping("/index")
	public ModelAndView index(Model model) {
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !SecurityContextHolder
						.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("hasStore", loginUser.getHasStore());
			model.addAttribute("ifLoginUser", true);
			model.addAttribute("loginUser", loginUser);
			if(loginUser.getHasStore()==true) {
				model.addAttribute("storeid", storeService.getStoreByUsername(loginUser.getUsername()).getId());
			}	
		} else {
			model.addAttribute("ifLoginUser", false);
		}
		List<Commodity> commodities = commodityService.ListCommodityByIfground(true);
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

		model.addAttribute("page",
				commodities.size() % 12 == 0 ? commodities.size() / 12 : (commodities.size() / 12) + 1);
		model.addAttribute("hotCommodities", hotCommodities);
		model.addAttribute("hotStores", hotStores);
		return new ModelAndView("index");
	}

	// 首页返回商品的信息
	@PostMapping("/index/commodities")
	public String getCommodities(HttpServletRequest request, Integer startIndex, Integer displayLength, String type) {
		JsonData jsonData;
		String callback = request.getParameter("callback");
		System.out.println(startIndex);
		Gson gson = new Gson();
		if (type.equals("all")) {
			List<Commodity> commodities = commodityService.ListCommodityAtIndex(startIndex, displayLength);
			if (commodities.size() == 0 || commodities == null) {
				jsonData = new JsonData("201", "商品为空！", "商品已经见底啦！", callback);
			} else {
				jsonData = new JsonData("200", "获取商品数据成功", gson.toJson(commodities), callback);
			}
		} else {
			List<Commodity> commodities = commodityService.ListCommodityByTypeAndPage(type, startIndex, displayLength);
			if (commodities.size() == 0 || commodities == null) {
				jsonData = new JsonData("201", "商品为空！", "商品已经见底啦！", callback);
			} else {
				jsonData = new JsonData("200", "获取商品数据成功", gson.toJson(commodities), callback);
			}
		}

		return jsonData.toString();
	}

	// 根据商铺的id返回店铺界面
	@GetMapping("/store/{id}")
	public ModelAndView commodity(@PathVariable("id") Long id, Model model) {
		Store store = storeService.getStoreById(id);
		store.increaseVisit();
		storeService.saveStore(store);
		List<Commodity> commodities = commodityService.ListCommodityByIfgroundAndStorename(true, store.getStorename());
		model.addAttribute("store", store);
		model.addAttribute("commodities", commodities);
		return new ModelAndView("store");
	}

	// 返回商品的界面
	@GetMapping("/commodity/{id}")
	public ModelAndView store(@PathVariable("id") Long id, Model model) {
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !SecurityContextHolder
						.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {

			User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserScan userScan = userScanService.getUserScanByUseridAndCommodityid(loginUser.getId(), id);
			if (userScan != null) {
				userScanService.delete(userScan);
			} 
			userScan = new UserScan(loginUser.getId(), id);
			userScanService.save(userScan);
			model.addAttribute("hasStore", loginUser.getHasStore());
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("ifLoginUser", true);
		} else {
			model.addAttribute("ifLoginUser", false);
		}
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
		
		Commodity commodity = commodityService.getCommodityById(id);
		commodity.increaseBrowse();
		commodityService.save(commodity);
		
		model.addAttribute("hotCommodities", hotCommodities);
		model.addAttribute("hotStores", hotStores);
		model.addAttribute("commodity", commodity);
		return new ModelAndView("commodity");
	}

	// 首页搜索商品
	@RequestMapping("/search")
	public ModelAndView search(
			@RequestParam(value = "commodityname", required = false, defaultValue = "") String commodityname,
			Model model) {
		List<Commodity> commodities = commodityService.getCommodityByCommodityname(commodityname);

		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !SecurityContextHolder
						.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("hasStore", loginUser.getHasStore());
			model.addAttribute("ifLoginUser", true);
			model.addAttribute("loginUser", loginUser);
		} else {
			model.addAttribute("ifLoginUser", false);
		}

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

		model.addAttribute("commodities", commodities);
		model.addAttribute("hotCommodities", hotCommodities);
		model.addAttribute("hotStores", hotStores);
		return new ModelAndView("search");
	}

	@RequestMapping("/reg")
	public ModelAndView register() {
		return new ModelAndView("reg");
	}

	@PostMapping("/toreg")
	public String toreg(HttpServletRequest request, User user, String checkcode) {
		String codeSession = (String) request.getSession().getAttribute("checkcode");
		System.out.println(codeSession);
		System.out.println(checkcode);
		String callback = request.getParameter("callback");
		if (codeSession.toLowerCase().equals(checkcode.toLowerCase())) {

			JsonData jsonData;
			if (userService.findByUsername(user.getUsername()) != null) {
				jsonData = new JsonData("101", "该用户名已经被注册！", "123", callback);
				return jsonData.toString();
			} else {
				System.out.println(user.getName());
				jsonData = new JsonData("200", "Sucuess", "RegisterSucuess", callback);
				user.setEncodePassword(user.getPassword());
				List<Authority> authorities = new ArrayList<>();
				authorities.add(authorityService.getAuthorityById(1L));
				user.setAuthorities(authorities);
				userService.saveUser(user);
				System.out.println("Yes");
				userService.saveUser(user);
				request.getSession().removeAttribute("checkcode");
				System.out.println(request.getSession().getAttribute("checkcode"));
				return jsonData.toString();
			}

		}
		System.out.println("No");
		JsonData jsonData = new JsonData("201", "Error", "CheckCodeError", callback);
		return jsonData.toString();
	}

	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@RequestMapping("/check.jpg")
	public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Expires", "-1");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "-1");
		CaptchaUtil util = CaptchaUtil.Instance();
		String code = util.getString();
		request.getSession().setAttribute("checkcode", code);
		ImageIO.write(util.getImage(), "jpg", response.getOutputStream());
	}

	@RequestMapping("/login-error")
	public ModelAndView loginerror() {
		return new ModelAndView("login");
	}

}
