package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 评论类
 * 采用注解方式进行创建数据库
 * @author xzx
 */
@Entity
@Table(name = "comment")
public class Comment {
	@Id @Column(name="comment_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	//评论内容
	@Column(name="content")
	private String content;
	
	//评论创建时间
	@Column(name="createTime")
	private String createTime;
	
	//评论点赞数
	@Column(name="likeNum")
	private Integer likeNum=0;
	
	//关联用户
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	//关联的分享内容
	@ManyToOne(targetEntity=Story.class)
	@JoinColumn(name="story_id",nullable=false)
	private Story story;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	public Comment(Integer id, String content, String createTime,
			Integer likeNum, User user, Story story) {
		super();
		this.id = id;
		this.content = content;
		this.createTime = createTime;
		this.likeNum = likeNum;
		this.user = user;
		this.story = story;
	}

	public Comment() {
		super();
	}
	
}
