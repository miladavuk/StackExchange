package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Question {
	
	private String[] tags;
	private Owner owner;
	private boolean is_answered;
	private int view_count;
	private int answer_count;
	private int score;
	private Date last_activity_date;
	private Date creation_date;
	private int question_id;
	private String title;
	private ArrayList<Answer> answers;
	
	
	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	public boolean isIs_answered() {
		return is_answered;
	}
	public void setIs_answered(boolean is_answered) {
		this.is_answered = is_answered;
	}
	public int getView_count() {
		return view_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	public int getAnswer_count() {
		return answer_count;
	}
	public void setAnswer_count(int answer_count) {
		this.answer_count = answer_count;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Date getLast_activity_date() {
		return last_activity_date;
	}
	public void setLast_activity_date(Date last_activity_date) {
		this.last_activity_date = last_activity_date;
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
	@Override
	public String toString() {
		return "tags=" + Arrays.toString(tags) + ",\n owner=" + owner + ",\n is_answered=" + is_answered
				+ ",\n view_count=" + view_count + ",\n answer_count=" + answer_count + ",\n score=" + score
				+ ",\n last_activity_date=" + last_activity_date + ",\n creation_date=" + creation_date + ",\n question_id="
				+ question_id + ",\n title=" + title + "]";
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
