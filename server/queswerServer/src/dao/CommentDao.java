package dao;

import java.util.List;

import vo.Comment;
import vo.Story;

public interface CommentDao   extends BaseDao<Comment>{
	
	//根据分享内容实体检索对应的评论集
	List<Comment> findByStory(Story story);
}
