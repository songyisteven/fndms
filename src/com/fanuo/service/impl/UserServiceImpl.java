/**
 * 
 */
package com.fanuo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fanuo.dao.def.UserDao;
import com.fanuo.model.User;
import com.fanuo.service.def.UserService;

/**
 * @author tao
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public void createUser(User user) {
		userDao.createUser(user);
	}

	@Override
	public User getUser(int userId) {
		return userDao.getUser(userId);
	}

	@Override
	public User getUser(String userName) {
		return userDao.getUser(userName);
	}

}
