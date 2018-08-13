package dao.impl;

import java.util.List;

import vo.Follow;
import dao.FollowDao;

public class FollowDaoImpl extends BaseDaoHibernate<Follow>  implements  FollowDao{

	/**
	 * 根据用户的ID，进行取关操作
	 * @author xxz
	 */
	@Override
	public void deleteFollow(Integer followed_userid, Integer follow_userid) {
		getSessionFactory().getCurrentSession()
		.createQuery("delete Follow en where en.followed_user_id = ?0  and en.user_id = ?1")
		.setParameter("0" , followed_userid).setParameter("1", follow_userid)
		.executeUpdate();
	}
	
	/**
	 * 根据用户的ID，获取关注实体的相关信息
	 * @author xxz
	 */
	@Override
	public Follow findFollow(Integer followed_userid, Integer follow_userid) {
		List<Follow> follows = find("select e from Follow e where e.followed_user_id = ?0  and  e.user_id = ?1"
				, followed_userid,follow_userid);
			if (follows!= null && follows.size() >= 1)
			{
				return follows.get(0);
			}
			return null;
	}

}
