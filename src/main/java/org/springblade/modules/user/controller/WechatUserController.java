package org.springblade.modules.user.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/user/wechat")
@ApiIgnore
@Api(value = "微信", tags = "接口")
public class WechatUserController {


	@Autowired
	private Environment env;


	@RequestMapping("/login")
	public String openWeChatLogin(HttpServletRequest httpServletRequest) {
		// 防止csrf攻击（跨站请求伪造攻击）
		String state = UUID.randomUUID().toString().replaceAll("-", "");
		// 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
//		RedisPoolUtil.setEx("wechat-open-state-" + httpServletRequest.getSession().getId(), state, Integer.parseInt(env.getProperty("wechat.open.exTime", "1800")));
		String url = "https://open.weixin.qq.com/connect/qrconnect?" +
			"appid=" +
			env.getProperty("wechat.open.pc.appid").trim() +
			"&redirect_uri=" +
			env.getProperty("application.url") +
			env.getProperty("wechat.open.pc.redirect_uri").trim() +
			"&response_type=code" +
			"&scope=snsapi_login" +
			"&state=" +
			state + // 由后台自动生成
			"#wechat_redirect";
		return "redirect:" + url;
	}

}
