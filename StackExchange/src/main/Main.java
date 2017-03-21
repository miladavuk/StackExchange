package main;

import domain.Answer;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Question;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import api.AnswerAPI;
import api.QuestionAPI;

public class Main {

	public static void main(String[] args)  throws Exception{

/*Redovi koji slede treba da ostanu pod komentarom kako program ne bi vrsio bespotrebno slanje GET zahteva
* jer su pitanja vec sacuvana u questions.json fajlu*/
	
//		QuestionAPI questionApi = new QuestionAPI();
//		ArrayList<Question> allQuestions = new ArrayList<Question>();
//		
//		//metoda zahteva da se prosledi redni br strane(na pocetku to je 1) i prazna lista za cuvanje pitanja
//		ArrayList<Question> questions = questionApi.getQuestions(1, allQuestions);
//		
//		//cuvamo skinuta pitanja
//		serializeQuestions(questions);
//		
		
		
		//prethodno sacuvano 1000 pitanja od kojih 591 ima odgovor
		
		ArrayList<Question> answeredQuestions = new ArrayList<Question>();
		ArrayList<Question> unansweredQuestions = new ArrayList<Question>();
		
		
		ArrayList<Question> questionsFromFile = deserializeQuestions();

		
		for (Question question : questionsFromFile) {
			if(question.isIs_answered())
				answeredQuestions.add(question);
			else
				unansweredQuestions.add(question);
		}
		
		System.out.println("Ukupan br pitanja: "+questionsFromFile.size());
		System.out.println("Br pitanja sa odgovorom: "+answeredQuestions.size());
		System.out.println("Br pitanja bez odgovora: "+unansweredQuestions.size());

		
		
		
		
		//lista odgovora se popunjava na osnovu id pitanja
		
		/*6 for petlji za pribavljanje odgovora preko razlicitih IP adresa kako server 
		 * ne bi odbio previse zahteva sa iste IP adrese. Sve se cuva u answers.json fajlu,
		 *  a za svako pokretanje programa 1 petlja je van komentara. Kad se poslednja 
		 *  petlja izvrsi, svih 6 petlji ostaje pod komentarom*/
		
//		for (int i=0; i<answeredQuestions.size()/6;i++) {
//			Question question = answeredQuestions.get(i);
//			AnswerAPI answerApi = new AnswerAPI();		
//			ArrayList<Answer> allAnswersForAQuestion = new ArrayList<Answer>();
//			allAnswersForAQuestion = answerApi.getAnswers(question.getQuestion_id());
//			serializeAnswers(allAnswersForAQuestion);		
//			question.setAnswers(allAnswersForAQuestion);
//			//za svako odgovoreno pitanje ispisujemo br prikupljrnih odgovora kao test
//			System.out.println("br prikupljenih odgovora za pitanje br "+question.getQuestion_id()+": "+allAnswersForAQuestion.size());
//		}
//		
//		for (int i=answeredQuestions.size()/6;i<2*answeredQuestions.size()/6;i++) {
//			Question question = answeredQuestions.get(i);
//			AnswerAPI answerApi = new AnswerAPI();		
//			ArrayList<Answer> allAnswersForAQuestion = new ArrayList<Answer>();
//			allAnswersForAQuestion = answerApi.getAnswers(question.getQuestion_id());
//			serializeAnswers(allAnswersForAQuestion);		
//			question.setAnswers(allAnswersForAQuestion);
//			//za svako odgovoreno pitanje ispisujemo br prikupljrnih odgovora kao test
//			System.out.println("br prikupljenih odgovora za pitanje br "+question.getQuestion_id()+": "+allAnswersForAQuestion.size());
//	}
//		for (int i=2*answeredQuestions.size()/6;i<3*answeredQuestions.size()/6;i++) {
//			Question question = answeredQuestions.get(i);
//			AnswerAPI answerApi = new AnswerAPI();		
//			ArrayList<Answer> allAnswersForAQuestion = new ArrayList<Answer>();
//			allAnswersForAQuestion = answerApi.getAnswers(question.getQuestion_id());
//			serializeAnswers(allAnswersForAQuestion);		
//			question.setAnswers(allAnswersForAQuestion);
//			//za svako odgovoreno pitanje ispisujemo br prikupljrnih odgovora kao test
//			System.out.println("br prikupljenih odgovora za pitanje br "+question.getQuestion_id()+": "+allAnswersForAQuestion.size());
//	}
//		for (int i=3*answeredQuestions.size()/6;i<4*answeredQuestions.size()/6;i++) {
//			Question question = answeredQuestions.get(i);
//			AnswerAPI answerApi = new AnswerAPI();		
//			ArrayList<Answer> allAnswersForAQuestion = new ArrayList<Answer>();
//			allAnswersForAQuestion = answerApi.getAnswers(question.getQuestion_id());
//			serializeAnswers(allAnswersForAQuestion);		
//			question.setAnswers(allAnswersForAQuestion);
//			//za svako odgovoreno pitanje ispisujemo br prikupljrnih odgovora kao test
//			System.out.println("br prikupljenih odgovora za pitanje br "+question.getQuestion_id()+": "+allAnswersForAQuestion.size());
//	}
//		for (int i=4*answeredQuestions.size()/6;i<5*answeredQuestions.size()/6;i++) {
//			Question question = answeredQuestions.get(i);
//			AnswerAPI answerApi = new AnswerAPI();		
//			ArrayList<Answer> allAnswersForAQuestion = new ArrayList<Answer>();
//			allAnswersForAQuestion = answerApi.getAnswers(question.getQuestion_id());
//			serializeAnswers(allAnswersForAQuestion);		
//			question.setAnswers(allAnswersForAQuestion);
//			//za svako odgovoreno pitanje ispisujemo br prikupljrnih odgovora kao test
//		System.out.println("br prikupljenih odgovora za pitanje br "+question.getQuestion_id()+": "+allAnswersForAQuestion.size());
//	}
//		for (int i=5*answeredQuestions.size()/6;i<answeredQuestions.size();i++) {
//			Question question = answeredQuestions.get(i);
//			AnswerAPI answerApi = new AnswerAPI();		
//			ArrayList<Answer> allAnswersForAQuestion = new ArrayList<Answer>();
//			allAnswersForAQuestion = answerApi.getAnswers(question.getQuestion_id());
//			serializeAnswers(allAnswersForAQuestion);		
//			question.setAnswers(allAnswersForAQuestion);
//			//za svako odgovoreno pitanje ispisujemo br prikupljrnih odgovora kao test
//			System.out.println("br prikupljenih odgovora za pitanje br "+question.getQuestion_id()+": "+allAnswersForAQuestion.size());
//}
//		
		
		/*Provera da su podaci ispravno preuzeti-poredi se sacuvan broj odgovora sa o
		 * cekivanim brojem odgovora i dobija isti broj - 1458. Takodje se pojedinacno
		 *  za svako pitanje koje ima odgovor poredi ocekivani sa sacuvanim brojem 
		 *  odgovora i dobijaju podudarni rezultati*/
		ArrayList<Answer> allAnswersFromFile = deserializeAnswers();
		System.out.println("Ukupan broj sacuvanih odgovora: "+allAnswersFromFile.size());
		int suma=0;
		
		for (Question question : answeredQuestions) {
			suma+=question.getAnswer_count();		
			System.out.println("Pitanje "+question.getQuestion_id()+" treba da ima "+question.getAnswer_count()+" odgovora");
			
		}
		System.out.println("\nUkupan broj ocekivanih odgovora: "+suma);
	
		for (Question question : answeredQuestions) {
			
			ArrayList<Answer> answers = new ArrayList<Answer>();
			int i=1;
			for(Answer answer : allAnswersFromFile){
			if(question.getQuestion_id()==answer.getQuestion_id())
				{
				answers.add(answer);
				}
			}
			question.setAnswers(answers);
			
		}
		
		for (Question question : answeredQuestions) {
			
			System.out.println("Pitanje "+question.getQuestion_id()+" zaista ima "+question.getAnswers().size()+" odgovora");
			
		}
		
		/*Prolazimo kroz listu odgovorenih pitanja i za svako pitanje trazimo odgovore
		 * Medju odgovorima pronalazimo najraniji i cuvamo kao odgovarajuci atribut u datom pitanju*/
		for (Question question : answeredQuestions) {
			Answer earliestAnswer=new Answer();
			earliestAnswer.setCreation_date(new Date());
			for (int i=0;i< allAnswersFromFile.size();i++) {
				if(question.getQuestion_id()==allAnswersFromFile.get(i).getQuestion_id()){
					if(allAnswersFromFile.get(i).getCreation_date().before(earliestAnswer.getCreation_date()))
						earliestAnswer=allAnswersFromFile.get(i);
				}
				
			}
			question.setEarliestAnswer(earliestAnswer);
			
		}
		//Trazimo pitanja na koja je odgovoreno posle vise od 24h
		ArrayList<Question> questionsAnsweredAfter24Hours = new ArrayList<Question>();
		
		for (Question question : answeredQuestions) {
			
			long duration = question.getEarliestAnswer().getCreation_date().getTime()-question.getCreation_date().getTime();
			
			long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
			System.out.println("Trajanje "+diffInHours);
			
			if(diffInHours>24)
				questionsAnsweredAfter24Hours.add(question);
		}
		/*napomena: dobice se da nema pitanja koja zadovoljavaju kriterijum.
		Cak i kad je kriterijum >=1 dobija se da je br pitanja samo 58 sto je manje od 10% odgovorenih pitanja*/
		System.out.println("Br pitanja na koje je odg posle vise od 24h: "+questionsAnsweredAfter24Hours.size());
		

	}		
		
	
	
	private static void serializeQuestions(ArrayList<Question> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("questions.json", true), lista);
	}

	private static void serializeAnswers(ArrayList<Answer> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("answers.json", true), lista);
	}
	private static ArrayList<Question> deserializeQuestions() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("questions.json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	private static ArrayList<Answer> deserializeAnswers() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			Answer [] answers = objectMapper.readValue(new FileInputStream("answers.json"), Answer[].class);
			List listOfAnswers =  Arrays.asList(answers);
			listOfAnswers = new ArrayList<>(listOfAnswers);
			return (ArrayList<Answer>) listOfAnswers;
		} catch (Exception e) {
			return null;
		}
	}

}
