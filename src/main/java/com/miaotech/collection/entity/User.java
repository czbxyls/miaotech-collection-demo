package com.miaotech.collection.entity;

import com.miaotech.collection.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author billchen
 * @since 2021-04-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String username;

    private String realname;

    private Integer age;

    private String email;


}
