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
import domain.Answer;

public class AnswerAPI {

		
		
		private String answerURL;

		//ova metoda nece biti rekurzivna, jer nijedno pitanje nema vise od 100 odgovora
		
		public ArrayList<Answer> getAnswers(int question_id) throws ParseException {
					
			try {
				this.answerURL = "http://api.stackexchange.com/2.2/questions/"+question_id+"/answers?pagesize=100&order=desc&sort=activity&site=stackoverflow";

				String result = sendGet(answerURL);
				
				Gson gson = new GsonBuilder().create();
				
				//objekat koji enkapsulira sve na stranici
				JsonObject answerJson = (JsonObject) gson.fromJson(result, JsonObject.class);


				
				//json niz koji sadrzi sve odgovore
				JsonArray items = answerJson.get("items").getAsJsonArray();
												
				ArrayList<Answer> answers = new ArrayList<Answer>();
				
				
				for (int i = 0; i < items.size(); i++) {
					
				Answer answer = new Answer();				
				
				JsonObject objectInItems = (JsonObject) items.get(i);
				
				Long timeStampNumber = objectInItems.get("creation_date").getAsLong();
				Date creation_date = new Date(timeStampNumber);
				answer.setCreation_date(creation_date);
				
				answer.setQuestion_id(objectInItems.get("question_id").getAsInt());
				
				answer.setAnswer_id(objectInItems.get("answer_id").getAsInt());		
				
				if(!answers.contains(answer))
				answers.add(answer);
				}
				
				
					return answers;
				
				
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
