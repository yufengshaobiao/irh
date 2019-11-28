package top.imuster.mall.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.mall.dao.ProductCategoryInfoDao;
import top.imuster.mall.domain.ProductCategoryInfo;
import top.imuster.mall.service.ProductCategoryInfoService;
import top.imuster.service.base.BaseServiceImpl;

import javax.annotation.Resource;

/**
 * ProductCategoryInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("productCategoryInfoService")
public class ProductCategoryInfoServiceImpl extends BaseServiceImpl<ProductCategoryInfo, Long> implements ProductCategoryInfoService {

    @Resource
    private ProductCategoryInfoDao productCategoryInfoDao;

    @Override
    public BaseDao<ProductCategoryInfo, Long> getDao() {
        return this.productCategoryInfoDao;
    }
}