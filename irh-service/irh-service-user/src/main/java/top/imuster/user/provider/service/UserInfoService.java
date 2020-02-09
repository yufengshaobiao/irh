package top.imuster.user.provider.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.user.api.dto.CheckValidDto;
import top.imuster.user.api.pojo.UserInfo;

/**
 * ConsumerInfoService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface UserInfoService extends BaseService<UserInfo, Long> {

    /**
     * @Description:
     * @Author: hmr
     * @Date: 2019/12/26 15:54
     * @param userName
     * @reture: top.imuster.security.api.bo.ConsumerDetails
     **/
    UserInfo loadUserDetailsByEmail(String email);

    /**
     * @Description: 根据用户信息校验用户是否存在
     * @Author: hmr
     * @Date: 2020/1/13 10:28
     * @param checkValidDto
     * @reture: boolean 如果存在，返回false；如果不存在，则返回true
     **/
    boolean checkValid(CheckValidDto checkValidDto) throws Exception;

    /**
     * @Author hmr
     * @Description 用户注册的一系列逻辑
     * @Date: 2020/1/14 13:00
     * @param userInfo
     * @reture: void
     **/
    Message<String> register(UserInfo userInfo, String code);

    /**
     * @Author hmr
     * @Description 根据id获得邮箱
     * @Date: 2020/1/17 10:21
     * @param id
     * @reture: java.lang.String
     **/
    String getEmailById(Long id);

    /**
     * @Author hmr
     * @Description 根据用户id修改用户的角色
     * @Date: 2020/1/31 14:49
     * @param userId 用户id
     * @param roleIds 角色id集合，用逗号分隔
     * @reture: void
     **/
    void editUserRoleById(Long userId, String roleIds);

    /**
     * @Author hmr
     * @Description 根据用户id获得用户昵称
     * @Date: 2020/2/6 16:55
     * @param id
     * @reture: java.lang.String
     **/
    String getUserNameById(Long id);
}