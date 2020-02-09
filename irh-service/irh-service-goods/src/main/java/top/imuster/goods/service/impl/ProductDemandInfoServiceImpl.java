package top.imuster.goods.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.dao.ProductDemandInfoDao;
import top.imuster.goods.service.ProductDemandInfoService;

import javax.annotation.Resource;

/**
 * ProductDemandInfoService 实现类
 * @author 黄明人
 * @since 2020-01-16 10:19:41
 */
@Service("productDemandInfoService")
public class ProductDemandInfoServiceImpl extends BaseServiceImpl<ProductDemandInfo, Long> implements ProductDemandInfoService {

    @Resource
    private ProductDemandInfoDao productDemandInfoDao;

    @Override
    public BaseDao<ProductDemandInfo, Long> getDao() {
        return this.productDemandInfoDao;
    }
}