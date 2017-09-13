package domain;

import java.io.IOException;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.FastVector;

public class DataSetFactory {
	
	public void createDatasetForATag(String tag){
		
		String filePath= "data/"+tag+"DataSet.arff";
		InputOutput io = new InputOutput();		
		ArrayList<EditedQuestion> eqs = new ArrayList<EditedQuestion>();
		try {
			eqs = io.deserializeEditedQuestions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (EditedQuestion editedQuestion : eqs) {
			boolean containsTag=false;
			for(String t : editedQuestion.getTags()){
				if(t.equals(tag)){
					containsTag=true;
					break;
				}			
			}
			
		}
		
		
		
	}
	
	private static ArrayList<Attribute> createAttributes(String tag) {
		ArrayList<Attribute> attributes = new ArrayList<>();	
		
		//tag: yes, no
		FastVector tagValues = new FastVector();
		tagValues.addElement("yes");
		tagValues.addElement("no");
		attributes.add(new Attribute(tag+"Attr", tagValues));
		attributes.add(new Attribute("text", (FastVector) null));
		
		return attributes;
	}

}
