package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import domain.EditedQuestion;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * 
 * @author Milada
 *
 */
public class DatasetFactory {
	
	
	private DataManagment dm = new DataManagment();
	private InputOutput io = new InputOutput();
	/**
	 * 
	 * @param tag - tag that is going to be class attribute in the dataset
	 * @param attributes - attributes that the dataset will contain
	 * @param eqs - all the questions that have been scraped
	 * @return - returns a dataset for a given tag
	 */
	private Instances createDatasetForATag(String tag,FastVector attributes, ArrayList<EditedQuestion> eqs){
		
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
	/**
	 * 
	 * @param tag
	 * @return - attributes for a certain dataset
	 */
	private FastVector createAttributes(String tag) {
		FastVector attributes = new FastVector(2);	
		
		//tag: yes, no
		FastVector tagValues = new FastVector(2);
		tagValues.addElement("yes");
		tagValues.addElement("no");
		
		attributes.addElement(new Attribute(tag+"Attr", tagValues));
		attributes.addElement(new Attribute("text", (FastVector) null));
		
		return attributes;
	}
	/**
	 * 
	 * @param dataSet - dataset for which the instances are to be created
	 * @param attributes - attributes in the given dataset
	 * @param dataItem - the question
	 * @param hasTag - shows if the question has a given tag
	 * @return - returns an instance of a given dataset
	 */
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
	/**
	 * Saves all datasets to an .arff file using methods from this class
	 */
	public void saveAllDatasets(){
		ArrayList<EditedQuestion> eqs = new ArrayList<>();
		LinkedList<String> popTags = new LinkedList<>();
		
		try {
			eqs = io.deserializeEditedQuestions();
			popTags = dm.popularTags(100, eqs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (String tag : popTags) {
			FastVector attributes = createAttributes(tag);
			Instances dataSet = createDatasetForATag(tag, attributes, eqs);
			ArffSaver saver = new ArffSaver();
			 saver.setInstances(dataSet);
			 try {
				saver.setFile(new File("data/"+tag+"DataSet.arff"));		 
				 saver.writeBatch();
			} catch (IOException e) {				
				e.printStackTrace();
			}
			
		}
	}
		
	/**
	 * 
	 * @param tag - the tag for which we want the dataset
	 * @return - returns a dataset for a given tag
	 * @throws Exception
	 */
	public Instances getDataset(String tag) throws Exception{
		
		String filePath= "data/"+tag+"DataSet.arff";
		
		// Load data  
		DataSource source = new DataSource(filePath);
		Instances data = source.getDataSet();

		// Set class index
		if (data.classIndex() == -1)
		    data.setClassIndex(0);

		return data;
		
	}
}
