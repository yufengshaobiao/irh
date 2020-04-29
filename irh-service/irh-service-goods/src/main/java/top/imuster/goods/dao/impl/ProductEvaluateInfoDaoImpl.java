package top.imuster.goods.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.goods.api.pojo.ProductEvaluateInfo;
import top.imuster.goods.dao.ProductEvaluateInfoDao;

/**
 * ProductEvaluateInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@Repository("productEvaluateInfoDao")
public class ProductEvaluateInfoDaoImpl extends BaseDaoImpl<ProductEvaluateInfo, Long> implements ProductEvaluateInfoDao {
	private final static String NAMESPACE = "top.imuster.goods.dao.ProductEvaluateInfoDao.";
	private final static String INSERT_INFO_RETURN_ID = "insertInfoReturnId";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Long insertInfoReturnId(ProductEvaluateInfo evaluateInfo) {
		return (long)this.insert(getNameSpace(INSERT_INFO_RETURN_ID), evaluateInfo);
	}
}