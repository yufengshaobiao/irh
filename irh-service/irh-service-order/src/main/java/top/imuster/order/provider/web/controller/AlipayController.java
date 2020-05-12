package top.imuster.order.provider.web.controller;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.AlipayService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @ClassName: AlipayController
 * @Description: 支付宝控制器
 * @author: hmr
 * @date: 2019/12/28 9:43
 */
@RestController
@RequestMapping("/alipay")
@Api("支付宝控制器")
public class AlipayController extends BaseController {

    @Resource
    AlipayService alipayService;

    /**
     * @Description: 提交订单准备预下单,返回一个支付宝网站,需要解析里面的地址生成二维码
     * @Author: hmr
     * @Date: 2019/12/23 12:05
     * @param orderInfo
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @NeedLogin
    @ApiOperation("提交订单准备预下单,返回一个支付宝网站,需要解析里面的地址生成二维码")
    @PostMapping("/perPayment")
    public Message prePayment(@RequestBody /*@Validated(ValidateGroup.prePayment.class)*/ OrderInfo orderInfo, BindingResult bindingResult) throws OrderException {
      //  validData(bindingResult);
        try{
            AlipayTradePrecreateResponse alipayResponse = alipayService.alipayF2F(orderInfo);
            return Message.createBySuccess(alipayResponse.getQrCode());
        }catch (Exception e){
            throw new OrderException(e.getMessage());
        }
    }

    /**
     * @Description: 支付宝回调地址
     * @Author: hmr
     * @Date: 2019/12/23 20:11
     * @param
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping("/alipayNotify")
    public Message<String> payResult(HttpServletRequest request) throws ParseException {
        alipayService.aliCallBack(request);
        return Message.createBySuccess("支付成功,已提醒卖家");
    }


}
