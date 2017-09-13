package domain.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.EditedQuestion;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class DataSetFactory {
	
	public Instances createDatasetForATag(String tag,FastVector attributes, ArrayList<EditedQuestion> eqs){
		
		String filePath= "data/"+tag+"DataSet.arff";
		//create empty dataset
		
		Instances dataSet = new Instances(tag+"DataSet",attributes, eqs.size());
		for (EditedQuestion editedQuestion : eqs) {
			boolean containsTag=false;
			for(String t : editedQuestion.getTags()){
				if(t.equals(tag)){
					containsTag=true;
					break;
				}			
			}
			dataSet.add( createInstance(dataSet, attributes, editedQuestion, containsTag));			
		}
		dataSet.setClassIndex(dataSet.numAttributes()-1);		
		return dataSet;	
	}
	
	public FastVector createAttributes(String tag) {
		FastVector attributes = new FastVector(2);	
		
		//tag: yes, no
		FastVector tagValues = new FastVector(2);
		tagValues.addElement("yes");
		tagValues.addElement("no");
		
		attributes.addElement(new Attribute(tag+"Attr", tagValues));
		attributes.addElement(new Attribute("text", (FastVector) null));
		
		return attributes;
	}
	
	private Instance createInstance(Instances dataSet, FastVector attributes,  EditedQuestion dataItem, boolean hasTag) {
		
		String classAtribute="no";
		if(hasTag)
			classAtribute="yes";
			
		Instance i = new Instance(2);
		i.setDataset(dataSet);
		i.setValue(0, classAtribute);
		i.setValue(1, dataItem.getText());
		
		
		return i;
	}

}
