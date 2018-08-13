package vo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "user")
public class User {
	@Id @Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="username",nullable=false)
	private String username;
	
	@Column(name="simpledesc")
	private String simpledesc ;
	
	@Column(name="avatarUrl")
	private String avatarUrl;
	
	@Column(name="account")
	private Integer account=0;
	
	@Column(name="isFamous")
	private Integer isFamous = 0;
	
	@Column(name="fans")
	private Integer fans=0;
	
	@OneToMany(targetEntity=Question.class, mappedBy="user")
	private Set<Question> questions = new HashSet<>();
	
	@OneToMany(targetEntity=Answer.class, mappedBy="user")
	private Set<Answer> answers = new HashSet<>();
	
	@OneToMany(targetEntity=Message.class, mappedBy="user")
	private Set<Message> messages = new HashSet<>();
	
	@OneToMany(targetEntity=Story.class, mappedBy="user")
	private Set<Story> stories = new HashSet<>();
	
	@OneToMany(targetEntity=Comment.class, mappedBy="user")
	private Set<Comment> comments = new HashSet<>();
	
	public User() {
		super();
	}

	public User(Integer id, String username, String simpledesc,
			String avatarUrl, Integer isFamous, Set<Question> questions,
			Set<Answer> answers, Set<Message> messages) {
		super();
		this.id = id;
		this.username = username;
		this.simpledesc = simpledesc;
		this.avatarUrl = avatarUrl;
		this.isFamous = isFamous;
		this.questions = questions;
		this.answers = answers;
		this.messages = messages;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public String getSimpledesc() {
		return simpledesc;
	}

	public void setSimpledesc(String simpledesc) {
		this.simpledesc = simpledesc;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Integer getIsFamous() {
		return isFamous;
	}

	public void setIsFamous(Integer isFamous) {
		this.isFamous = isFamous;
	}

	public Integer getAccount() {
		return account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public Integer getFans() {
		return fans;
	}

	public void setFans(Integer fans) {
		this.fans = fans;
	}

	public Set<Story> getStories() {
		return stories;
	}

	public void setStories(Set<Story> stories) {
		this.stories = stories;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
}
