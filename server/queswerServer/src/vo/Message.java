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
 * 通知类 
 * 采用注解方式进行创建数据库
 * @author xzx
 */
@Entity
@Table(name = "message")
public class Message {
	@Id
	@Column(name = "message_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//通知类型
	@Column(name = "type")
	private Integer type;
	
	//通知信息产生者
	@Column(name = "from_userid")
	private Integer from_userid;
	
	//通知接受者
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	//关联的回答ID
	@Column(name = "answer_id")
	private Integer answer;
	
	//关联的提问ID
	@Column(name = "question_id")
	private Integer question;
	
	//通知是否已处理
	@Column(name = "isread")
	private Integer isread = 1;

	public Message(Integer id, Integer type, Integer from_userid, User user) {
		super();
		this.id = id;
		this.type = type;
		this.from_userid = from_userid;
		this.user = user;
	}

	public Integer getIsread() {
		return isread;
	}

	public void setIsread(Integer isread) {
		this.isread = isread;
	}

	public Message() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getFrom_userid() {
		return from_userid;
	}

	public void setFrom_userid(Integer from_userid) {
		this.from_userid = from_userid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getAnswer() {
		return answer;
	}

	public void setAnswer(Integer answer) {
		this.answer = answer;
	}

	public Integer getQuestion() {
		return question;
	}

	public void setQuestion(Integer question) {
		this.question = question;
	}

}
