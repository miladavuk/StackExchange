package main;

import domain.Question;

import java.util.ArrayList;

import api.QuestionAPI;

public class Main {

	public static void main(String[] args)  throws Exception{


		QuestionAPI api = new QuestionAPI();
		ArrayList<Question> allQuestions = new ArrayList<Question>();
		
		//metoda zahteva da se prosledi redni br strane i prazna lista za cuvanje pitanja
		ArrayList<Question> questions = api.getQuestions(1, allQuestions);
		
		ArrayList<Question> answeredQuestions = new ArrayList<Question>();
		
		for (Question question : questions) {
			if(question.isIs_answered())
				answeredQuestions.add(question);			
		}
		
		System.out.println(questions.size());	
		
	}

}
