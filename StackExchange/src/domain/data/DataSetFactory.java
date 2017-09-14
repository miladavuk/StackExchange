package domain.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import domain.EditedQuestion;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.supervised.instance.Resample;
import weka.filters.supervised.instance.StratifiedRemoveFolds;
import weka.filters.unsupervised.attribute.StringToWordVector;

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
	//CELA METODA ISPOD NE RADI KAKO TREB. RADJENA JE PO JEDINOM PRIMERU KOJI SAM NASLA NA INTERNETU
	public void stratifiedCrossValidation(String tag) throws Exception{
		
		String filePath= "data/"+tag+"DataSet.arff";
		
		// Load data  
		DataSource source = new DataSource(filePath);
		Instances data = source.getDataSet();

		// Set class index
		if (data.classIndex() == -1)
		    data.setClassIndex(0);

		// use StratifiedRemoveFolds to randomly split the data  
		StratifiedRemoveFolds stratifyFilter = new StratifiedRemoveFolds();
		
		StringToWordVector textToWordfilter = new StringToWordVector();
		textToWordfilter.setTokenizer(new WordTokenizer());		
		textToWordfilter.setInputFormat(data);
		textToWordfilter.setWordsToKeep(10000);
		textToWordfilter.setDoNotOperateOnPerClassBasis(true);
		textToWordfilter.setLowerCaseTokens(true);
		
		Ranker ranker = new Ranker();
		ranker.setThreshold(0.0);

		AttributeSelection asFilter = new AttributeSelection();
		
		asFilter.setEvaluator(new InfoGainAttributeEval());
	
		asFilter.setSearch(ranker);
		
		Filter[] filters = new Filter[2];		
		filters[0] = textToWordfilter;
		filters[1] = asFilter;
		MultiFilter multiFilter = new MultiFilter();
		multiFilter.setFilters(filters);

		int k = 10;
		double accuracySum = 0;
		for (int i = 1; i <= k; i++) {
			// set options for creating the subset of data
			String[] options = new String[6];
			options[0] = "-N"; // indicate we want to set the number of folds                        
			options[1] = Integer.toString(k); // split the data into k random folds
			options[2] = "-F"; // indicate we want to select a specific fold
			options[3] = Integer.toString(1); // select fold
			options[4] = "-S"; // indicate we want to set the random seed
			options[5] = Integer.toString(i); // set the random seed to 1
			stratifyFilter.setOptions(options); // set the filter options
			stratifyFilter.setInputFormat(data); // prepare the filter for the data format    
			stratifyFilter.setInvertSelection(false); // do not invert the selection
			
			
			// apply filter for test data here
			Instances test = Filter.useFilter(data, stratifyFilter);
			//  prepare and apply filter for training data here
			stratifyFilter.setInvertSelection(true); // invert the selection to get other data 
			Instances train = Filter.useFilter(data, stratifyFilter);
			train.setClassIndex(0);
			test.setClassIndex(0);
			
			System.out.println("Velicina trening seta "+train.numInstances());//ispisuje 37800, sto je ceo set?
			System.out.println("Velicina test seta "+test.numInstances());//ispisuje 3780, sto je 10% pa je ok
			
			NaiveBayes classifier = new NaiveBayes();			
			classifier.setUseSupervisedDiscretization(true);
			
			FilteredClassifier filteredClassifier = new FilteredClassifier();
			filteredClassifier.setClassifier(classifier);			
			filteredClassifier.setFilter(multiFilter);
			
			System.out.println("Pocinje treniranje");
			filteredClassifier.buildClassifier(train);

			
			/*
			 * Evaluating the classifier on the test set.			 
			 */
			Evaluation eval = new Evaluation(test); 
			eval.evaluateModel(filteredClassifier, test);
			
			/*
			 * Printing the evaluation summary with the confusion matrix 
			 */
			
			System.out.println(eval.toSummaryString()); 
			System.out.println(eval.toMatrixString());
			accuracySum += eval.pctCorrect();
		}
		System.out.println("Accuracy is: " + accuracySum / k);
		
		
		
	}
	
	/*
	*MOZE I OVAKO, ZBOG METODE STRATIFY KOJA RADI, JER JE CLASS ATRIBUT NOMINALNI
	*/
	public void stratifiedCV(String tag) throws Exception{String filePath= "data/"+tag+"DataSet.arff";
	
	// Load data  
	DataSource source = new DataSource(filePath);
	Instances data = source.getDataSet();

	// Set class index
	if (data.classIndex() == -1)
	    data.setClassIndex(0);

	StringToWordVector textToWordfilter = new StringToWordVector();
	textToWordfilter.setTokenizer(new WordTokenizer());		
	textToWordfilter.setInputFormat(data);
	textToWordfilter.setWordsToKeep(10000);
	textToWordfilter.setDoNotOperateOnPerClassBasis(true);
	textToWordfilter.setLowerCaseTokens(true);
	
	Ranker ranker = new Ranker();
	ranker.setThreshold(0.0);

	AttributeSelection asFilter = new AttributeSelection();
	
	asFilter.setEvaluator(new InfoGainAttributeEval());

	asFilter.setSearch(ranker);
	
	Filter[] filters = new Filter[2];		
	filters[0] = textToWordfilter;
	filters[1] = asFilter;
	MultiFilter multiFilter = new MultiFilter();
	multiFilter.setFilters(filters);

	int seed = 1;			// the seed for randomizing the data
	int folds = 10;			// the number of folds to generate, >=2
	 
	
	Random rand = new Random(seed);   			// create seeded number generator
	Instances randData = new Instances(data);   // create copy of original data
	randData.randomize(rand);         			// randomize data with number generator
	
	//TRENIRANJE ODUZIMA PREVISE VREMENA, PA PRAVIMO SUBSET KOJI CINI 2 POSTO POLAZNOG DATASETA
	randData=returnSubset(randData,2);
	
	System.out.println("Velicina subseta "+randData.numInstances());//
	
	// in case data has a nominal class and want perform stratified cross-validation:
	if (randData.classAttribute().isNominal())
		{		
		randData.stratify(folds);
		}
	double accuracySum = 0;
	double sumPrecision = 0;
	double sumRecall = 0;
	double sumFmeasure = 0;
	int n=0;
	try {		
		for (n = 0; n < folds; n++) {		
			Instances train = randData.trainCV(folds, n);
			Instances test = randData.testCV(folds, n);
			System.out.println("****************************************");
			System.out.println("Size of train data set: "+train.numInstances());//ISPISUJE SE 680/681 STO JE DOBRO
			System.out.println("Size of test data set:  "+test.numInstances());//ISPISUJE SE 75/76 STO JE DOBRO
			
			NaiveBayes classifier = new NaiveBayes();			
			classifier.setUseSupervisedDiscretization(true);
			
			FilteredClassifier filteredClassifier = new FilteredClassifier();
			filteredClassifier.setClassifier(classifier);			
			filteredClassifier.setFilter(multiFilter);
			
			System.out.println("Training begins");//TRENIRANJE I DALJE JAKO DUGO TRAJE
			filteredClassifier.buildClassifier(train);

			System.out.println("Testing begins");
			/*
			 * Evaluating the classifier on the test set.			 
			 */
			Evaluation eval = new Evaluation(test); 
			eval.evaluateModel(filteredClassifier, test);
			
			/*
			 * Printing the evaluation summary with the confusion matrix 
			 */
			
			System.out.println(eval.toSummaryString()); 
			System.out.println(eval.toMatrixString());
			System.out.println("****************************************");
			accuracySum += eval.pctCorrect();
			sumPrecision += eval.precision(0);
			sumRecall += eval.recall(0);
			sumFmeasure += eval.fMeasure(0);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("TRENUTNO JE n= "+n);
		e.printStackTrace();
	}
	System.out.println("Accuracy is: " + accuracySum / folds);
	System.out.println("Precision is: " + sumPrecision / folds);
	System.out.println("Recall is: " + 	sumRecall / folds);
	System.out.println("Fmeasure is: " + 	sumRecall / folds);
	}
	
	public  Instances returnSubset(Instances data, int percentage)
	{
		Resample filter = new Resample();
		Instances filteredIns = null;
		//odnos pozitivne i negativne klase da ima uniformnu raspodelu
		filter.setBiasToUniformClass(1.0);
		try {
			filter.setInputFormat(data);
			filter.setNoReplacement(false);
			filter.setSampleSizePercent(percentage);
			filteredIns = Filter.useFilter(data, filter);
		} catch (Exception e) {
			System.err.println("Error when resampling input data!");
			e.printStackTrace();
		}
		return filteredIns;

	}
	

}
