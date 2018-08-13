package vo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 提问类
 * 采用注解方式进行创建数据库
 * @author xzx
 */
@Entity
@Table(name = "question")
public class Question {
	@Id @Column(name="question_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	//提问内容
	@Column(name="content")
	private String content;
	
	//提问是否免费
	@Column(name="is_free")
	private Integer is_free;
	
	//提问创建时间
	@Column(name="createTime")
	private String createTime;
	
	//提问用户的姓名
	@Column(name="quesd_username")
	private String quesd_username;

	//关联的提问用户
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	//关联的回答信息
	@OneToMany(targetEntity=Answer.class, mappedBy="question")
	private Set<Answer> answers = new HashSet<>();

	public Question(Integer id, String content, Integer is_free,
			String createTime, User user) {
		super();
		this.id = id;
		this.content = content;
		this.is_free = is_free;
		this.createTime = createTime;
		this.user = user;
	}

	public Question() {
		super();
	}

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

	public Integer getIs_free() {
		return is_free;
	}

	public void setIs_free(Integer is_free) {
		this.is_free = is_free;
	}

	public String getQuesd_username() {
		return quesd_username;
	}

	public void setQuesd_username(String quesd_username) {
		this.quesd_username = quesd_username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	
}
