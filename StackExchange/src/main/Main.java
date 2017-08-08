package main;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.QuestionAPI;
import domain.Question;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Main {

	/**
	 * @param args
	 * @throws Exception
	 */
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
//		serializeQuestions2(questions);
		
			
//		ArrayList<Question> questionsFromFile = deserializeQuestions2();
//
//		//Provera prikupljenih podataka
//		System.out.println("Broj pitanja: "+questionsFromFile.size());
//		System.out.println("*****PRVO PITANJE*****");
//		System.out.println(questionsFromFile.get(0));
//		System.out.println("*****KRAJ PRVOG PITANJA*****");
//		
//		//provera tagova, ukupno ih je 35920, a jedinstvenih skoro 10 puta manje tj 4202
//		LinkedList<String> allTags=new LinkedList<String>();
//		LinkedList<String> uniqueTags=new LinkedList<String>();
//		
//		for (Question q : questionsFromFile) {
//			for (int i = 0; i < q.getTags().length; i++) {
//				allTags.add(q.getTags()[i]);
//				if(!uniqueTags.contains(q.getTags()[i]))
//					uniqueTags.add(q.getTags()[i]);					
//			}			
//		}
//		
//		System.out.println("Broj tagova: "+allTags.size());
//		System.out.println("Broj jedinstvenih tagova: "+uniqueTags.size());
		
//		ArrayList<Question> questions0 = deserializeQuestions();
//		ArrayList<Question> questions1 = deserializeQuestions1();
//		ArrayList<Question> questions2 = deserializeQuestions2();
//		ArrayList<Question> questions3 = deserializeQuestions3();
//		ArrayList<Question> questions4 = deserializeQuestions4();
//		
//		
//		ArrayList<Question> allQuestions = questions0;
//		allQuestions.addAll(questions1);
//		allQuestions.addAll(questions2);
//		allQuestions.addAll(questions3);
//		allQuestions.addAll(questions4);
//		
//		ArrayList<Integer> ids = new ArrayList<Integer>();
//		
//		for (Question question : allQuestions) {
//			if(!ids.contains(question.getQuestion_id()))
//				ids.add(question.getQuestion_id());
//		}
//
//		System.out.println("Broj sacuvanih pitanja: "+allQuestions.size());
//		System.out.println("Broj jedinstvenih id-jeva: "+ids.size());
//		
//		//Brojevi se poklapaju (37 800), pa mozemo da sacuvamo sva pitanja
//		
//		serializeQuestionsAll(allQuestions);

		//provera tagova, ukupno ih je 35920, a jedinstvenih skoro 10 puta manje tj 4202
		
//		ArrayList<Question> questionsFromFile= deserializeQuestionsAll();
//		LinkedList<String> allTags=new LinkedList<String>();
//		LinkedList<String> uniqueTags=new LinkedList<String>();
//		
//		for (Question q : questionsFromFile) {
//			for (int i = 0; i < q.getTags().length; i++) {
//				allTags.add(q.getTags()[i]);
//				if(!uniqueTags.contains(q.getTags()[i]))
//					uniqueTags.add(q.getTags()[i]);					
//			}			
//		}
//		
//		System.out.println("Broj tagova: "+allTags.size());
//		System.out.println("Broj jedinstvenih tagova: "+uniqueTags.size());
		
		ArrayList<Question> questionsFromFile= deserializeQuestionsAll();
		
		numberOfPopularTags(20,questionsFromFile);
		numberOfPopularTags(30,questionsFromFile);
		numberOfPopularTags(40,questionsFromFile);
		numberOfPopularTags(50,questionsFromFile);
		numberOfPopularTags(58,questionsFromFile);
		numberOfPopularTags(60,questionsFromFile);
		numberOfPopularTags(70,questionsFromFile);
		numberOfPopularTags(80,questionsFromFile);
		numberOfPopularTags(90,questionsFromFile);
		numberOfPopularTags(100,questionsFromFile);

	}		
		
	
	
	private static void serializeQuestions(ArrayList<Question> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("questions.json", true), lista);
	}

	
	private static ArrayList<Question> deserializeQuestions() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("questions.json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	private static void serializeQuestions1(ArrayList<Question> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("questions1.json", true), lista);
	}

	
	private static ArrayList<Question> deserializeQuestions1() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("questions1.json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	private static void serializeQuestions2(ArrayList<Question> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("questions2.json", true), lista);
	}

	
	private static ArrayList<Question> deserializeQuestions2() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("questions2.json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	
	private static void serializeQuestions3(ArrayList<Question> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("questions3.json", true), lista);
	}

	
	private static ArrayList<Question> deserializeQuestions3() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("questions3.json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	private static void serializeQuestions4(ArrayList<Question> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("questions4.json", true), lista);
	}

	
	private static ArrayList<Question> deserializeQuestions4() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("questions4.json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	
	private static void serializeQuestionsAll(ArrayList<Question> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("questionsAll.json", true), lista);
	}

	
	private static ArrayList<Question> deserializeQuestionsAll() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("questionsAll.json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	
	private static void numberOfPopularTags(int numberOfQuestions, ArrayList<Question> questionsFromFile) throws Exception{
		
				
		LinkedList<String> uniqueTags=new LinkedList<String>();
		LinkedList<String> popularTags=new LinkedList<String>();
		
		for (Question q : questionsFromFile) {
			for (int i = 0; i < q.getTags().length; i++) {				
				if(!uniqueTags.contains(q.getTags()[i]))
					uniqueTags.add(q.getTags()[i]);					
			}			
		}
		for(String tag : uniqueTags){
			int numberOfOccurances=0;
			for (Question q : questionsFromFile) {
				for (int i = 0; i < q.getTags().length; i++) {				
					if(tag.equals(q.getTags()[i]))
						numberOfOccurances++;					
				}			
			}			
			if(numberOfOccurances>=numberOfQuestions && !popularTags.contains(tag))
				popularTags.add(tag);				
		}
		
		System.out.println(popularTags.size()+" tagova se javlja u "+numberOfQuestions+" ili vise pitanja");
		
		
	}
	

}
