package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Owner;
import domain.Question;


public class QuestionAPI {
	
	private static final String questionURL = "http://api.stackexchange.com/2.2/questions?pagesize=5&order=desc&sort=activity&site=stackoverflow";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

	
	
	public Question getQuestion() throws ParseException {
		try {
			String result = sendGet(questionURL);
			
			Gson gson = new GsonBuilder().create();
			
			//objekat koji enkapsulira sve na stranici
			JsonObject questionJson = (JsonObject) gson.fromJson(result, JsonObject.class);
			//json niz koji sadrzi sva pitanja
			JsonArray items = questionJson.get("items").getAsJsonArray();
			
			Question question = new Question();
			
			//posto vracamo 1 pitanje, to je prvi el u json nizu
			JsonObject objectInItems = (JsonObject) items.get(0);
			
			//taj element sadrzi atribute pitanja
			JsonArray tagsJson = objectInItems.get("tags").getAsJsonArray();
			String[] tags = new String[tagsJson.size()];
			
			for (int i = 0; i < tags.length; i++) {
				tags[i]=tagsJson.get(i).getAsString();				
			}
			question.setTags(tags);
			
			//objekat unutar objekta
			Owner owner = new Owner();
			JsonObject ownerJson = (JsonObject) objectInItems.get("owner").getAsJsonObject();
			owner.setReputation(ownerJson.get("reputation").getAsInt());
			owner.setUser_id(ownerJson.get("user_id").getAsInt());
			owner.setUser_type(ownerJson.get("user_type").getAsString());
			question.setOwner(owner);
			
			question.setIs_answered( objectInItems.get("is_answered").getAsBoolean());
			
						
			question.setView_count(objectInItems.get("view_count").getAsInt());	
			
			question.setAnswer_count(objectInItems.get("answer_count").getAsInt());
			
			question.setScore(objectInItems.get("score").getAsInt());
			
			Long timeStampNumber = objectInItems.get("last_activity_date").getAsLong();
			Date last_activity_date = new Date(timeStampNumber);
			question.setLast_activity_date(last_activity_date);
			
			Long timeStampNumber2 = objectInItems.get("creation_date").getAsLong();
			Date creation_date = new Date(timeStampNumber2);
			question.setCreation_date(creation_date);
			
			question.setQuestion_id(objectInItems.get("question_id").getAsInt());
			
			question.setTitle(objectInItems.get("title").getAsString());
			
			return question;
			
			
		//handlovanje izuzetaka	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//metoda za slanje GET zahteva za dati URL
	private String sendGet(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
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
