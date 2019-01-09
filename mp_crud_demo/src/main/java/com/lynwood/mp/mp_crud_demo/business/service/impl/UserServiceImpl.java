package com.lynwood.mp.mp_crud_demo.business.service.impl;

import com.lynwood.mp.mp_crud_demo.business.entity.User;
import com.lynwood.mp.mp_crud_demo.business.mapper.UserMapper;
import com.lynwood.mp.mp_crud_demo.business.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lynwood
 * @since 2019-01-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
