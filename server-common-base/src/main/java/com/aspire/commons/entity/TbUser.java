package com.aspire.commons.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2019-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbUser对象", description="")
public class TbUser extends Model<TbUser> {


    private static final long serialVersionUID = 1L;
    @TableId(value = "userId", type = IdType.NONE)
	@Length(min = 0, max = 32,message="不能大于32",groups = {Modify.class,Save.class,CustomModify.class})
	@NotBlank(message = "不能为空",groups = {Modify.class})
    private String userId;
	
    @TableField("userName")
	@Length(min = 0, max = 255,message="不能大于255",groups = {Modify.class,Save.class,CustomModify.class})
    private String userName;
	
	@Length(min = 0, max = 255,message="不能大于255",groups = {Modify.class,Save.class,CustomModify.class})
    private String email;
	
	@Length(min = 0, max = 255,message="不能大于255",groups = {Modify.class,Save.class,CustomModify.class})
    private String password;
	


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }


	public interface Save{};

    public interface Modify{};
	
	public interface CustomModify{};
	
	
}
