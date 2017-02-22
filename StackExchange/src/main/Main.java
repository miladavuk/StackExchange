package main;

import domain.Question;
import api.QuestionAPI;

public class Main {

	public static void main(String[] args)  throws Exception{


		QuestionAPI api = new QuestionAPI();
		Question question = api.getQuestion();
		
		System.out.println(question.toString());	
		
	}

}
