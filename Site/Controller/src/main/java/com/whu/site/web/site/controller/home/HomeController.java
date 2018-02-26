package com.whu.site.web.site.controller.home;

import com.whu.framework.base.entity.BaseEntity;
import com.whu.groupon.cart.entity.Cart;
import com.whu.groupon.cart.service.CartService;
import com.whu.groupon.deal.entity.Deal;
import com.whu.groupon.deal.service.DealService;
import com.whu.groupon.order.entity.Order;
import com.whu.groupon.order.service.OrderService;
import com.whu.site.web.base.objects.WebUser;
import com.whu.site.web.site.controller.BaseSiteController;
import com.whu.site.web.site.dto.CartDTO;
import com.whu.site.web.site.dto.FavoriteDTO;
import com.whu.support.address.entity.Address;
import com.whu.support.address.service.AddressService;
import com.whu.support.favorite.entity.Favorite;
import com.whu.support.favorite.service.FavoriteService;
import com.whu.support.message.entity.Message;
import com.whu.support.message.service.MessageService;
import com.whu.support.remind.entity.StartRemind;
import com.whu.support.remind.service.StartRemindService;
import com.whu.user.entity.User;
import com.whu.user.entity.UserBasicInfo;
import com.whu.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 个人中心
 */
@Controller
@RequestMapping(value="/home")
public class HomeController extends BaseSiteController {

	@Autowired private OrderService orderService;
	@Autowired private UserService userService;
	@Autowired private CartService cartService;
	@Autowired private DealService dealService;
	@Autowired private AddressService addressService;
	@Autowired private FavoriteService favoriteService;
	@Autowired private MessageService messageService;
	@Autowired private StartRemindService startRemindService;

	@RequestMapping(value="/index")
	public String index(HttpServletRequest request, Model model) {
		WebUser webUser = getCurrentLoginUser(request);
		UserBasicInfo info = userService.getBasicInfoByUserId(webUser.getUserId());
		model.addAttribute("info", info);
		return "/user/info";
	}

	/**
	 * 加入收藏
	 * @param request
	 * @param skuId
     * @return
     */
	@RequestMapping(value = "/favorite/{skuId}")
	@ResponseBody
	public String favorite(HttpServletRequest request, @PathVariable Long skuId) {
		Deal deal = this.dealService.getBySkuId(skuId);
		Favorite favorite = new Favorite();
		favorite.setDealId(deal.getId());
		favorite.setDealSkuId(skuId);
		WebUser user = getCurrentLoginUser(request);
		favorite.setUserId(user.getUserId());
		favoriteService.save(favorite);
		return "1";
	}

	/**
	 * 加入开团提醒
	 * @param request
	 * @param skuId
     * @return
     */
	@RequestMapping(value="/remind/{skuId}")
	@ResponseBody
	public String remind(HttpServletRequest request, @PathVariable Long skuId) {
		WebUser user = getCurrentLoginUser(request);
		Deal deal = dealService.getBySkuId(skuId);
		StartRemind remind = new StartRemind();
		remind.setDealId(deal.getId());
		remind.setDealSkuId(skuId);
		remind.setDealTitle(deal.getDealTitle());
		remind.setUserId(user.getUserId());
		remind.setStartTime(deal.getStartTime());
		startRemindService.save(remind);
		return "1";
	}






























	@RequestMapping(value="/order")
	public String order(HttpServletRequest request, Model model) {
		WebUser webUser = getCurrentLoginUser(request);

		List<Order> orders = orderService.getOrderByUserId(webUser.getUserId());
		model.addAttribute("orders", orders);

		return "/user/order";
	}

	@RequestMapping(value="/favorite")
	public String favorite(HttpServletRequest request, Model model) {
		WebUser user = getCurrentLoginUser(request);
		if (null != user) {
			Long userId = user.getUserId();
			List<Favorite> favorites = favoriteService.getByUserId(userId);
			if (null != favorites && !favorites.isEmpty()) {
				List<Long> dealIds = favorites.stream().map(cart -> cart.getDealId()).collect(Collectors.toList());
				List<Deal> deals = dealService.getDealsForCart(dealIds);
				Map<Long, Deal> map = BaseEntity.idEntityMap(deals);
				List<FavoriteDTO> dtoList = new ArrayList<>(favorites.size());
				favorites.stream().forEach(favorite -> dtoList.add(new FavoriteDTO(favorite, map.get(favorite.getDealId()))));
				model.addAttribute("favorites", dtoList);
			}
		}
		return "/user/favorite";
	}

	@RequestMapping(value="/info")
	public String info(HttpServletRequest request, Model model) {
		WebUser webUser = getCurrentLoginUser(request);
		UserBasicInfo info = userService.getBasicInfoByUserId(webUser.getUserId());
		model.addAttribute("info", info);
		return "/user/info";
	}

	@RequestMapping(value="/info/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateInfo(HttpServletRequest request, UserBasicInfo info) {
		if (null == info.getId()) {
			WebUser webUser = getCurrentLoginUser(request);
			userService.saveBasicInfo(info, webUser.getUserId());
		} else {
			userService.updateBasicInfo(info);
		}
		return "1";
	}

	@RequestMapping(value="/address")
	public String address(HttpServletRequest request, Model model) {
		WebUser webUser = getCurrentLoginUser(request);
		List<Address> addresses = addressService.getByUserId(webUser.getUserId());
		model.addAttribute("addresses", addresses);
		return "/user/address";
	}

	@RequestMapping(value="/address/new")
	public String createAddressPrompt(HttpServletRequest request, Model model) {
		return "/user/create_address";
	}

	@RequestMapping(value="/address/create", method = RequestMethod.POST)
	public String createAddress(HttpServletRequest request, Model model, Address address) {
		WebUser webUser = getCurrentLoginUser(request);
		address.setUserId(webUser.getUserId());
		addressService.save(address);
		return "redirect:/home/address";
	}


	@RequestMapping(value="/message")
	public String message(HttpServletRequest request, Model model) {
		WebUser webUser = getCurrentLoginUser(request);
		List<Message> messages = messageService.getByUserId(webUser.getUserId());
		model.addAttribute("messages", messages);
		return "/user/message";
	}

	
}