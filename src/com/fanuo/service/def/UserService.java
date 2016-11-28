/**
 * 
 */
package com.fanuo.service.def;

import com.fanuo.model.User;

/**
 * @author tao
 *
 */
public interface UserService {
	
	public void createUser(User user);
	
	public User getUser(int userId);
	
	public User getUser(String userName);
}
