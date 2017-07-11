package api;

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
import domain.Question;


public class QuestionAPI {
	


	

	public ArrayList<Question> getQuestions(int page, ArrayList<Question> allQuestions) throws ParseException {
		String questionURL = "https://api.stackexchange.com/2.2/questions?page="+page+"&pagesize=100&fromdate=1435795200&todate=1451520000&order=desc&sort=activity&tagged=java&site=stackoverflow&filter=!)re8-BBbvkGyazC*-K9O";

		try {
			String result = sendGet(questionURL);
			
			Gson gson = new GsonBuilder().create();
			
			//objekat koji enkapsulira sve na stranici
			JsonObject questionJson = (JsonObject) gson.fromJson(result, JsonObject.class);
			//da li postoji jos odgovora na zahtev
			boolean has_more = questionJson.get("has_more").getAsBoolean();
			
			//json niz koji sadrzi sva pitanja
			JsonArray items = questionJson.get("items").getAsJsonArray();
			
			
			ArrayList<Question> questions = allQuestions;
			
			for (int i = 0; i < items.size(); i++) {
				
			Question question = new Question();
			
			
			//jedan objekat u json nizu items predstavlja 1 pitanje
			JsonObject objectInItems = (JsonObject) items.get(i);
			
			//taj element sadrzi atribute pitanja
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
			
			//ako postoji jos pitanja koja odgovaraju upitu, 
			//a broj strana je manji od 10 (ukupno 100*100=10000 pitanja),vracaj jos pitanja
			if(has_more==true && page<100){
				return getQuestions(++page,questions);
			}
			else
				return questions;
			
			
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
		
		//podaci su enkodirani uz pomoc gzipa, pa se bez 2 linije ispod vraca besmislen sadrzaj, a ne json
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
