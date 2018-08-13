package service;

import java.util.List;

import vo.Comment;
import vo.Story;
import dao.CommentDao;

public class CommentService {
	
	private CommentDao commentDao;

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
	public void addComment(Comment comment){
		commentDao.save(comment);
	}
	
	public List<Comment> listComments(Story story){
		return commentDao.findByStory(story);
	}
}
