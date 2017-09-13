package domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InputOutput {

	public   void serializeQuestions(ArrayList<Question> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("questions.json", true), lista);
	}

	
	public   ArrayList<Question> deserializeQuestions() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("questions.json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	public   void serializeQuestions1(ArrayList<Question> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("questions1.json", true), lista);
	}

	
	public   ArrayList<Question> deserializeQuestions1() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("questions1.json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	public   void serializeQuestions2(ArrayList<Question> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("questions2.json", true), lista);
	}

	
	public   ArrayList<Question> deserializeQuestions2() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("questions2.json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	
	public   void serializeQuestions3(ArrayList<Question> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("questions3.json", true), lista);
	}

	
	public   ArrayList<Question> deserializeQuestions3() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("questions3.json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	public   void serializeQuestions4(ArrayList<Question> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("questions4.json", true), lista);
	}

	
	public   ArrayList<Question> deserializeQuestions4() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("questions4.json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	
	public   void serializeQuestionsAll(ArrayList<Question> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("questionsAll.json", true), lista);
	}

	
	public   ArrayList<Question> deserializeQuestionsAll() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("questionsAll.json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	
	public   void serializeEditedQuestions(ArrayList<EditedQuestion> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("editedQuestions.json", true), lista);
	}

	
	public   ArrayList<EditedQuestion> deserializeEditedQuestions() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		EditedQuestion [] questions = objectMapper.readValue(new FileInputStream("editedQuestions.json"), EditedQuestion[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<EditedQuestion>) listOfQuestions;
	}
}
