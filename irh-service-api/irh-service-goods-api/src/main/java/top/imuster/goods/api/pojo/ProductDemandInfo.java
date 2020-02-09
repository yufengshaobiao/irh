package top.imuster.goods.api.pojo;


import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotBlank;

/**
 * 
 * @author 黄明人
 * @since 2020-01-16 10:19:41
 */
public class ProductDemandInfo extends BaseDomain {

	private static final long serialVersionUID = 7996610101L;

	public ProductDemandInfo() {
		//默认无参构造方法
	}
	// 会员需求表 主键
	@NotBlank(groups = ValidateGroup.editGroup.class, message = "主键不能为空,请刷新后重试")
	private Long id;

	// 标题, max length: 255
	@NotBlank(groups = ValidateGroup.releaseGroup.class, message = "标题不能为空")
	private String topic;

	// 分类id
	@NotBlank(groups = ValidateGroup.releaseGroup.class, message = "分类不能为空")
	private Long categoryId;

	// 内容, max length: 255
	@NotBlank(groups = ValidateGroup.releaseGroup.class, message = "内容不能为空")
	private String content;

	// 发布人
	private Long consumerId;

	// 状态 1-无效 2-有效
//	private Short state;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public String getTopic() {
		return this.topic;
	}
    public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public Long getCategoryId() {
		return this.categoryId;
	}
    public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getContent() {
		return this.content;
	}
    public void setContent(String content) {
		this.content = content;
	}
	
	public Long getConsumerId() {
		return this.consumerId;
	}
    public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}
	
}