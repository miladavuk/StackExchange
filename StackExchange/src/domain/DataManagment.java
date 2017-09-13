package domain;

import java.util.ArrayList;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class DataManagment {
	
	
	
	
	public ArrayList<EditedQuestion> editQuestions(ArrayList<Question> rawQuestions){
		
		DataManagment dm = new DataManagment();
		ArrayList<EditedQuestion> editedQuestions = new ArrayList<EditedQuestion>();
		
		
		for (int i=0; i<rawQuestions.size();i++) {
			String title = rawQuestions.get(i).getTitle();
			String oldBody = rawQuestions.get(i).getBody();
			
			//parsing the body as a HTML document			
			Document doc = Jsoup.parse(oldBody);
			//removing the entire pre element-tag and content
			doc.select("pre").remove();
			String clean = Jsoup.clean(doc.body().html(), Whitelist.basic());
			Document doc2 = Jsoup.parse(clean);
			//the new body contains no tags, but keeps contents of every tag except for pre
			String newBody= title.concat("\n"+doc2.text());
			
			EditedQuestion eq = new EditedQuestion();
			eq.setTags(rawQuestions.get(i).getTags());
			eq.setText(newBody);
			editedQuestions.add(eq);
		}	
		
		return editedQuestions;
	}
	
	public void numberOfPopularTags(int numberOfQuestions, ArrayList<Question> questionsFromFile) throws Exception{
		
		
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
			if(numberOfOccurances>=numberOfQuestions && !popularTags.contains(tag)&& !tag.equals("java")){
				popularTags.add(tag);	
				System.out.println("Tag "+tag+" se javlja "+numberOfOccurances+" puta");
				}	
		}
		
		System.out.println(popularTags.size()+" tagova se javlja u "+numberOfQuestions+" ili vise pitanja");
		
		
	}

}
