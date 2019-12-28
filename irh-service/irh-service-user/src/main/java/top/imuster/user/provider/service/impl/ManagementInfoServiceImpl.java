package top.imuster.user.provider.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.utils.JwtTokenUtil;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.user.api.bo.ManagementDetails;
import top.imuster.user.api.pojo.ManagementInfo;
import top.imuster.user.api.pojo.ManagementRoleRel;
import top.imuster.user.provider.dao.ManagementInfoDao;
import top.imuster.user.provider.service.ManagementInfoService;
import top.imuster.user.provider.service.ManagementRoleRelService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * ManagementInfoService 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Service("managementInfoService")
@Slf4j
public class ManagementInfoServiceImpl extends BaseServiceImpl<ManagementInfo, Long> implements ManagementInfoService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private ManagementInfoDao managementInfoDao;

    @Resource
    private ManagementRoleRelService managementRoleRelService;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public BaseDao<ManagementInfo, Long> getDao() {
        return this.managementInfoDao;
    }

    @Override
    public ManagementInfo getManagementRoleByCondition(ManagementInfo condition){
        return managementInfoDao.selectManagementRoleByCondition(condition);
    }

    @Override
    public ManagementDetails loadManagementByName(String name){
        ManagementInfo managementInfo = new ManagementInfo();
        managementInfo.setName(name);
        managementInfo = managementInfoDao.selectManagementRoleByCondition(managementInfo);
        if(managementInfo == null) {
            throw new UsernameNotFoundException("用户名或者密码错误");
        }
        if(managementInfo.getType() == null || managementInfo.getType() <= 20){
            throw new RuntimeException("该账号已被冻结,请联系管理员");
        }
        return new ManagementDetails(managementInfo, managementInfo.getRoleList());
    }

    @Override
    public String login(String name, String password) {
        try{
            String token;
            ManagementDetails managementDetails = loadManagementByName(name);
            if(!passwordEncoder.matches(password, managementDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(managementDetails, null, managementDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = JwtTokenUtil.generateToken(managementDetails.getUsername(), managementDetails.getManagementInfo().getId());
            ManagementInfo managementInfo = managementDetails.getManagementInfo();

            //将用户的基本信息存入redis中，并设置过期时间
            redisTemplate.opsForValue()
                    .set(RedisUtil.getAccessToken(token),
                            new UserDto(managementInfo.getId(),
                                        managementInfo.getName(),
                                        GlobalConstant.userType.MANAGEMENT.getName(),
                                        GlobalConstant.userType.MANAGEMENT.getType()),
                            GlobalConstant.REDIS_JWT_EXPIRATION, TimeUnit.SECONDS);
            return token;
        }catch (AuthenticationException a){
            this.LOGGER.error("管理员登录异常:{}" , a.getMessage(), a);
            throw new RuntimeException();
        }catch (Exception e){
            this.LOGGER.error("管理员登录异常,服务器内部错误:{}" , e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    @Override
    public void editManagementRoleById(Long managementId, String roleIds) throws Exception {
        ManagementDetails currentManagement = (ManagementDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = currentManagement.getManagementInfo().getName();
        String[] roles = roleIds.split(",");
        for (String role : roles) {
            ManagementRoleRel managementRoleRel = new ManagementRoleRel();
            managementRoleRel.setRoleId(Long.parseLong(role));
            managementRoleRel.setStaffId(managementId);
            Integer count = managementRoleRelService.getCountByCondition(managementRoleRel);
            // todo 删除操作


            //新增操作
            if (count == 0) {
                managementRoleRel.setCreateManagemen(name);
                managementRoleRelService.insertEntry(managementRoleRel);
            }
        }
    }
}