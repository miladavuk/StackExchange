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
//		serializeQuestions(questions);
//		
		
		
		
		
		
		ArrayList<Question> questionsFromFile = deserializeQuestions();

		System.out.println(questionsFromFile.size());
		System.out.println(questionsFromFile.get(1));
		
		

		
		

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
	

}
