package dao.impl;

import java.util.List;

import vo.Comment;
import vo.Story;
import dao.CommentDao;

public class CommentDaoImpl  extends BaseDaoHibernate<Comment>  implements  CommentDao{

	/**
	 * 根据分享内容实体检索对应的评论集
	 * @author xxz
	 */
	@Override
	public List<Comment> findByStory(Story story) {
		List<Comment> comments = find("select e from Comment e where e.story = ?0"
				, story);
		return comments;
	}

}
