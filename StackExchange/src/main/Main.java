package main;

import domain.Answer;
import domain.Question;

import java.util.ArrayList;

import api.AnswerAPI;
import api.QuestionAPI;

public class Main {

	public static void main(String[] args)  throws Exception{


		QuestionAPI questionApi = new QuestionAPI();
		ArrayList<Question> allQuestions = new ArrayList<Question>();
		
		//metoda zahteva da se prosledi redni br strane i prazna lista za cuvanje pitanja
		ArrayList<Question> questions = questionApi.getQuestions(1, allQuestions);
		
		ArrayList<Question> answeredQuestions = new ArrayList<Question>();
		
		for (Question question : questions) {
			if(question.isIs_answered())
				answeredQuestions.add(question);			
		}
		
		System.out.println("Ukupan broj skinutih pitanja: "+questions.size());	
		System.out.println("Ukupan broj pitanja sa odgovorom: "+answeredQuestions.size());	
		
		AnswerAPI answerApi = new AnswerAPI();
		
		ArrayList<Answer> allAnswersForAQuestion = new ArrayList<>();
		int i=1;
		//lista pitanja se popunjava na osnovu id pitanja
		for (Question question : answeredQuestions) {
			allAnswersForAQuestion = answerApi.getAnswers(question.getQuestion_id());
			question.setAnswers(allAnswersForAQuestion);
			System.out.println("------------------------------------------");
			System.out.println("Pitanje br "+(i++) + "ima sledece odgovore");
			for (Answer answer : allAnswersForAQuestion) {
				
				System.out.println(answer.toString());
				
			}
			
		}

		
		
	}

}
