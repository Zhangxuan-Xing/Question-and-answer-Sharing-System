package dao.impl;

import java.util.List;

import vo.Story;
import vo.User;
import dao.StoryDao;

public class StoryDaoImpl  extends BaseDaoHibernate<Story> implements StoryDao{

	/**
	 * 根据分享的标题信息检索符合条件的分享实体
	 * @author xxz
	 */
	@Override
	public Story findStory(String title) {
		List<Story> stories = find("select e from Story e where e.title = ?0"
				,title);
			if (stories!= null && stories.size() >= 1)
			{
				return stories.get(0);
			}
			return null;
	}

	/**
	 * 根据标题信息，模糊检索，返回符合条件的分享信息集
	 * @author xxz
	 */
	@Override
	public List<Story> findByTopic(String topic) {
		List<Story> stories = find("select e from Story e where e.title like ?0"
				, topic);
		return stories;
	}
	
	/**
	 * 根据关联的用户实体，检索返回符合条件的分享信息集
	 * @author xxz
	 */
	@Override
	public List<Story> findByUser(User user) {
		List<Story> stories = find("select e from Story e where e.user = ?0"
				, user);
		return stories;
	}

}
