package com.tg.OnlineShoppingMall.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tg.OnlineShoppingMall.domin.Commodity;
import com.tg.OnlineShoppingMall.domin.Trolley;
import com.tg.OnlineShoppingMall.domin.User;
import com.tg.OnlineShoppingMall.service.CommodityService;
import com.tg.OnlineShoppingMall.service.TrolleyService;
import com.tg.OnlineShoppingMall.service.UserScanService;
import com.tg.OnlineShoppingMall.tools.JsonData;

/**
 * 用户操作管理自己的数据 控制层,需要用户登录才能进行一下操作
 * 
 * @author follow_tg
 *
 */
@RestController
@PreAuthorize("hasAuthority('ROLE_TG')") // 需要用户登录才能访问
public class UserController {

	@Autowired
	private TrolleyService trolleyService;

	@Autowired
	private CommodityService commodityService;

	@Autowired
	private UserScanService userScanService;

	// 登录的用户名必须和访问的用户购物车一致
	@GetMapping("/history")
	public ModelAndView history(Model model) {
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Commodity> commodities = userScanService.getCommodityByUserid(loginUser.getId());
		model.addAttribute("commodities", commodities);
		return new ModelAndView("user/history");
	}

	// 登录的用户名必须和访问的用户购物车一致
	@GetMapping("/trolley")
	public ModelAndView trolley( Model model) {
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Trolley> trolleyList = trolleyService.geTrolleysByUsername(loginUser.getUsername());
		model.addAttribute("trolleyList", trolleyList);
		model.addAttribute("hasStore", loginUser.getHasStore());
		return new ModelAndView("user/trolley");
	}

	// 根据id从购物车中移除
	@PostMapping("/removeShoppingTrolley/{id}")
	public String removeShoppingTrolley(HttpServletRequest request, @PathVariable("id") Long id) {
		String callback = request.getParameter("callback");
		JsonData jsonData;
		trolleyService.delete(id);
		jsonData = new JsonData("200", "Success", "操作成功", callback);
		return jsonData.toString();
	}

	// 加入购物车
	@PostMapping("/joinShoppingTrolley/{commodityId}")
	public String joinShoppingTrolley(HttpServletRequest request, @PathVariable("commodityId") Long commodityId) {
		String callback = request.getParameter("callback");
		JsonData jsonData;
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (trolleyService.geTrolleyByCommodityIdAndUsername(commodityId, loginUser.getUsername()) != null) {
			jsonData = new JsonData("201", "Fialed", "该商品已经加入您的购物车啦！", callback);
			return jsonData.toString();
		}
		Commodity commodity = commodityService.getCommodityById(commodityId);
		Trolley trolley = new Trolley(commodityId, commodity.getCommodityname(), loginUser.getUsername(),
				commodity.getPrice(), commodity.getInfo(), commodity.getStorename());
		commodity.increaseJoinShoppingCart();
		commodityService.save(commodity);
		trolleyService.Save(trolley);
		jsonData = new JsonData("200", "Success", "加入购物车成功", callback);
		return jsonData.toString();
	}
}
