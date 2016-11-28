/**
 * 
 */
package com.fanuo.dao.impl;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.fanuo.dao.def.UserDao;
import com.fanuo.model.User;

/**
 * @author tao
 *
 */
@Repository("userDao")
public class UserDaoImpl extends EnhancedHibernateDaoSupport<User> implements UserDao {
	@Override
	protected String getEntityName() {
		return User.class.getName();
	}
	/* (non-Javadoc)
	 * @see com.fanuo.dao.def.UserDao#createUser(com.base.bo.User)
	 */
	@Override
	public void createUser(User user) {
		super.saveEntity(user);

	}

	/* (non-Javadoc)
	 * @see com.fanuo.dao.def.UserDao#getUser(int)
	 */
	@Override
	public User getUser(int userId) {
		return super.getEntityById(Long.valueOf(userId));
	}

	/* (non-Javadoc)
	 * @see com.fanuo.dao.def.UserDao#getUser(java.lang.String)
	 */
	@Override
	public User getUser(String userName) {
		return super.getUniqueEntityByOneProperty("username", userName);
	}



}
