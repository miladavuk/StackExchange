package data;

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
/**
 * 
 * @author Milada
 *
 */
public class InputOutput {

	/**
	 * 
	 * @param listOfQuestions 
	 * @param stringOrNumber - empty String (""), number 1 to 4 or "All"
	 * @throws IOException
	 */
	public   void serializeQuestions(ArrayList<Question> listOfQuestions, String stringOrNumber) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("jsonData/questions"+stringOrNumber+".json", true), listOfQuestions);
	}

	/**
	 * 
	 * @param stringOrNumber - empty String (""), number 1 to 4 or "All"
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public   ArrayList<Question> deserializeQuestions(String stringOrNumber) throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		Question [] questions = objectMapper.readValue(new FileInputStream("jsonData/questions"+stringOrNumber+".json"), Question[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<Question>) listOfQuestions;
	}
	
	/**
	 * 
	 * @param listOfEditedQuestions
	 * @throws IOException
	 */
	public   void serializeEditedQuestions(ArrayList<EditedQuestion> listOfEditedQuestions) throws IOException{ 
		ObjectMapper  mapper = new ObjectMapper();
		mapper.writeValue(new FileOutputStream("jsonData/editedQuestions.json", true), listOfEditedQuestions);
	}

	/**
	 * 
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public   ArrayList<EditedQuestion> deserializeEditedQuestions() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();

		EditedQuestion [] questions = objectMapper.readValue(new FileInputStream("jsonData/editedQuestions.json"), EditedQuestion[].class);
		List listOfQuestions = Arrays.asList(questions);
        listOfQuestions = new ArrayList<>(listOfQuestions);
		return (ArrayList<EditedQuestion>) listOfQuestions;
	}
}
