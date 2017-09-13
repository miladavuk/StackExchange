package main;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.QuestionAPI;
import domain.EditedQuestion;
import domain.Question;
import domain.data.DataManagment;
import domain.data.DataSetFactory;
import domain.data.InputOutput;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;


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
//		serializeQuestions4(questions);
		
	
		
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
	
//		serializeQuestionsAll(allQuestions);

		
		
//		InputOutput io = new InputOutput();
//		ArrayList<Question> questionsFromFile= io.deserializeQuestionsAll();
//		DataManagment dm = new DataManagment();
//		ArrayList<EditedQuestion> eqs = dm.editQuestions(questionsFromFile);
//		io.serializeEditedQuestions(eqs);
		
		InputOutput io = new InputOutput();		
		ArrayList<EditedQuestion> eqs = new ArrayList<EditedQuestion>();
		
		try {
			eqs = io.deserializeEditedQuestions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataManagment dm = new DataManagment();
		
		LinkedList<String> popTags = dm.popularTags(100, eqs);
		
		DataSetFactory dsf = new DataSetFactory();		
		
		//create a dataset for every popular tag
		for (String tag : popTags) {
			FastVector attributes = dsf.createAttributes(tag);
			Instances dataSet = dsf.createDatasetForATag(tag, attributes, eqs);
			ArffSaver saver = new ArffSaver();
			 saver.setInstances(dataSet);
			 saver.setFile(new File("data/"+tag+"DataSet.arff"));		 
			 saver.writeBatch();
			
		}
		
		
		
		
	}		
		
	
	
	
	
	
	

}
