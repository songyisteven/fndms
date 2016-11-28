/**
 * 
 */
package com.fanuo.dao.def;

import com.fanuo.model.User;

/**
 * @author tao
 *
 */
public interface UserDao {
	
	public void createUser(User user);
	
	public User getUser(int userId);
	
	public User getUser(String userName);

}
