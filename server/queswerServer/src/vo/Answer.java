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
 * 回答类
 * 采用注解方式进行创建数据库
 * @author xzx
 */
@Entity
@Table(name = "answer")
public class Answer {
	
	@Id @Column(name="answer_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	//回答内容
	@Column(name="content")
	private String content;
	
	//回答的点赞数
	@Column(name="likedCount")
	private Integer likedCount;
	
	//回答创建时间
	@Column(name="createTime")
	private String createTime;
	
	//关联用户
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	//关联问题
	@ManyToOne(targetEntity=Question.class)
	@JoinColumn(name="ques_id",nullable=false)
	private Question question;

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

	public Integer getLikedCount() {
		return likedCount;
	}

	public void setLikedCount(Integer likedCount) {
		this.likedCount = likedCount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer(Integer id, String content, Integer likedCount,
			String createTime, User user, Question ques) {
		super();
		this.id = id;
		this.content = content;
		this.likedCount = likedCount;
		this.createTime = createTime;
		this.user = user;
		this.question = ques;
	}

	public Answer() {
		super();
	}
	
}
