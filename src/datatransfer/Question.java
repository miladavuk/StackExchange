package datatransfer;

/**
 * 
 */
import java.util.Arrays;
import java.util.Date;

public class Question {
	
	private String[] tags;	
	private Date creation_date;
	private int question_id;
	private String title;
	private String body;

	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	public Date getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	
	@Override
	public String toString() {
		return "Question [tags=" + Arrays.toString(tags) + ", creation_date=" + creation_date + ", question_id="
				+ question_id + ", title=" + title + ", body=" + body + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + question_id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (question_id != other.question_id)
			return false;
		return true;
	}
	
	


	
	
	

}
