package domain.data;

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

import domain.EditedQuestion;
import domain.Question;

public class InputOutput {

	public   void serializeQuestions(ArrayList<Question> lista, String stringOrNumber) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("jsonData/questions"+stringOrNumber+".json", true), lista);
	}

	
	public   ArrayList<Question> deserializeQuestions(String stringOrNumber) throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("jsonData/questions"+stringOrNumber+".json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	
	public   void serializeEditedQuestions(ArrayList<EditedQuestion> lista) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("jsonData/editedQuestions.json", true), lista);
	}

	
	public   ArrayList<EditedQuestion> deserializeEditedQuestions() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		EditedQuestion [] questions = objectMapper.readValue(new FileInputStream("jsonData/editedQuestions.json"), EditedQuestion[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<EditedQuestion>) listOfQuestions;
	}
}
