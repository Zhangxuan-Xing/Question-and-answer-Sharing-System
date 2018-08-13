package dao.impl;

import java.util.List;

import vo.User;
import dao.UserDao;

public class UserDaoImpl extends BaseDaoHibernate<User> implements UserDao{
	
	/**
	 * 根据姓名信息，检索返回符合条件的用户
	 * @author xxz
	 */
	@Override
	public User findByName(String name) {
		List<User> users = find("select e from User e where e.username = ?0"
				, name);
			if (users!= null && users.size() >= 1)
			{
				return users.get(0);
			}
			return null;
	}
	
	/**
	 * 根据是否为名人的标签，检索返回符合条件的用户集
	 * @author xxz
	 */
	@Override
	public List<User> findByIsFamous(int isfamous) {
		List<User> users = find("select e from User e where e.isFamous = ?0"
				, isfamous);
		return users;
	}
	
	/**
	 * 根据用户头像Url，检索返回符合条件的用户集
	 * @author xxz
	 */
	@Override
	public User findByImgUrl(String imgUrl) {
		List<User> users = find("select e from User e where e.avatarUrl = ?0"
				, imgUrl);
			if (users!= null && users.size() >= 1)
			{
				return users.get(0);
			}
			return null;
	}
	
	/**
	 * 根据关联的用户实体ID，检索ta所关注的用户集
	 * @author xxz
	 */
	@Override
	public List<User> findLikedByUserid(int userid) {
		List<User> users = find("select e from User e,Follow d where e.id = d.followed_user_id"
				+ "  and d.user_id = ?0"
				, userid);
		return users;
	}

	/**
	 * 根据姓名，模糊检索，返回符合条件的用户集
	 * @author xxz
	 */
	@Override
	public List<User> findBySearch(String name) {
		List<User> users = find("select e from User e where e.username like ?0"
				, name);
		return users;
	}
	
	/**
	 * 根据用户ID，检索返回用户实体
	 * @author xxz
	 */
	@Override
	public User findByUserid(int userid) {
		List<User> users = find("select e from User e where e.id = ?0"
				, userid);
			if (users!= null && users.size() >= 1)
			{
				return users.get(0);
			}
			return null;
	}


}
