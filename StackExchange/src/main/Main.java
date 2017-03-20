package main;

import domain.Question;

import java.util.ArrayList;

import api.QuestionAPI;

public class Main {

	public static void main(String[] args)  throws Exception{


		QuestionAPI api = new QuestionAPI();
		ArrayList<Question> questions1 = new ArrayList<Question>();
		ArrayList<Question> questions = api.getQuestions(1,questions1);
		
		System.out.println(questions.size());	
		
	}

}
