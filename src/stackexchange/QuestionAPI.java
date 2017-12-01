package stackexchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import datatransfer.Question;

/**
 * 
 * @author Milada
 *
 */
public class QuestionAPI {
	
	/**
	 * 
	 * @param page - must be 1!
	 * @param allQuestions - it's a recursive method so the list to be returned is passed on as a parameter
	 * @return - returns up to 10000 questions from a certain period that meet the criteria in the URL
	 * @throws ParseException
	 */
	public ArrayList<Question> getQuestions(int page, ArrayList<Question> allQuestions) throws ParseException {
		String questionURL = "https://api.stackexchange.com/2.2/questions?page="+page+"&pagesize=100&fromdate=1451606400&todate=1467331200&order=desc&sort=activity&tagged=java&site=stackoverflow&filter=!)re8-BBbvkGyazC*-K9O";

		try {
			String result = sendGet(questionURL);
			
			Gson gson = new GsonBuilder().create();
			
			//object that encapsulates everything on the page
			JsonObject questionJson = (JsonObject) gson.fromJson(result, JsonObject.class);
			//are there more objects/pages
			boolean has_more = questionJson.get("has_more").getAsBoolean();
			
			//json array that contains all questions
			JsonArray items = questionJson.get("items").getAsJsonArray();
			
			
			ArrayList<Question> questions = allQuestions;
			
			for (int i = 0; i < items.size(); i++) {
				
			Question question = new Question();
			
			
			//one object in json array items represents one question
			JsonObject objectInItems = (JsonObject) items.get(i);
			
			//that object contains the attributes of the question
			JsonArray tagsJson = objectInItems.get("tags").getAsJsonArray();
			String[] tags = new String[tagsJson.size()];
			
			for (int j = 0; j < tags.length; j++) {
				tags[j]=tagsJson.get(j).getAsString();				
			}
			question.setTags(tags);	
			
			Long timeStampNumber2 = objectInItems.get("creation_date").getAsLong();
			Date creation_date = new Date(timeStampNumber2);
			question.setCreation_date(creation_date);
			
			question.setQuestion_id(objectInItems.get("question_id").getAsInt());
			
			question.setTitle(objectInItems.get("title").getAsString());
			question.setBody(objectInItems.get("body").getAsString());
			
			if(!questions.contains(question))
			questions.add(question);
			}
			
			//if there are more questions that meet the criteria
			//and the number of the current page is less than 100 (total 100*100=10000 questions),
			//return more questions
			if(has_more==true && page<100){
				return getQuestions(++page,questions);
			}
			else
				return questions;
					
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//method for sending GET requests to the given URL
	/**
	 * 
	 * @param url
	 * @return - returns a String that contains JSON data
	 * @throws IOException
	 */
	private String sendGet(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		
		//the data is encoded using gzip, so the next 2 lines are used to get around that
		con.setRequestProperty("Accept-Encoding", "gzip");

		BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(con.getInputStream())));

		boolean endReading = false;
		String response = "";

		while (!endReading) {
			String s = in.readLine();

			if (s != null) {
				response += s;
			} else {
				endReading = true;
			}
		}
		in.close();

		return response.toString();


	}




}
