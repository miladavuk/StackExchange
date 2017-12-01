package classifier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import data.DataManagment;
import data.DatasetFactory;
import data.InputOutput;
import datatransfer.EditedQuestion;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.supervised.instance.Resample;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 * 
 * @author Milada
 *
 */
public class ClassifierBuilder {
	
	DatasetFactory df = new DatasetFactory();
	DataManagment dm = new DataManagment();
	InputOutput io = new InputOutput();
	
	/**
	 * 
	 * @param percentageOfOriginalDataset - use a number between 1 and 5
	 * @param percentageOfTestSet - for 10 fold cross-validation, use 10
	 */
	public void buildAllClassifiers(int percentageOfOriginalDataset,int percentageOfTestSet){
		int numberOfQuestions = 100;
		
		try {
			ArrayList<EditedQuestion> allQuestions = io.deserializeEditedQuestions();
			LinkedList<String> popularTags = dm.popularTags(numberOfQuestions, allQuestions);
			for (String tag : popularTags) {
				buildClassifier(tag, percentageOfOriginalDataset, percentageOfTestSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 
	 * @param tag 
	 * @param percentageOfOriginalDataset
	 * @param percentageOfTestSet
	 * @throws Exception
	 */

	private void buildClassifier(String tag, int percentageOfOriginalDataset, int percentageOfTestSet) throws Exception{
	
	System.out.println("BEGINNING CROSS-VALIDATION FOR TAG: "+tag+
			" USING "+percentageOfOriginalDataset+"%"+
			" OF THE ORIGINAL DATASET, WITH THE TESTING SET MAKING UP "+percentageOfTestSet+"%");
	Instances data = df.getDataset(tag);
	
	
	int seed = 1;			// the seed for randomizing the data
	int folds = 100/percentageOfTestSet;			// the number of folds to generate, >=2
	 
	
	Random rand = new Random(seed);   			// create seeded number generator
	Instances randData = new Instances(data);   // create copy of original data
	randData.randomize(rand);         			// randomize data with number generator
	
	//Training is taking too long, so we are going to use a subset
	randData=returnStratifiedSubset(randData,percentageOfOriginalDataset);
	
	
	// in case data has a nominal class and want perform stratified cross-validation:
	if (randData.classAttribute().isNominal())
		{		
		randData.stratify(folds);
		}
	double accuracySum = 0;
	double sumPrecision = 0;
	double sumRecall = 0;
	double sumFmeasure = 0;
	double meanAbsoluteError=0;
	double rootMeanSquaredError=0;
	double relativeAbsoluteError=0;
	double rootRelativeSquaredError=0;
	double kappaStatistics=0;
	int n=0;
	try {		
		for (n = 0; n < folds; n++) {		
			Instances train = randData.trainCV(folds, n);
			Instances test = randData.testCV(folds, n);
			
			NaiveBayes classifier = new NaiveBayes();			
			classifier.setUseSupervisedDiscretization(true);
			
			FilteredClassifier filteredClassifier = new FilteredClassifier();
			filteredClassifier.setClassifier(classifier);			
			filteredClassifier.setFilter(returnMultifilter(data));
			
			filteredClassifier.buildClassifier(train);

			/*
			 * Evaluating the classifier on the test set.			 
			 */
			Evaluation eval = new Evaluation(test); 
			eval.evaluateModel(filteredClassifier, test);
			
			/*
			 * Printing the evaluation summary with the confusion matrix 
			 */
			
			//System.out.println(eval.toSummaryString()); 
			//System.out.println(eval.toMatrixString());
			accuracySum += eval.pctCorrect();
			sumPrecision += eval.precision(0);
			sumRecall += eval.recall(0);
			sumFmeasure += eval.fMeasure(0);
			
			meanAbsoluteError+= eval.meanAbsoluteError();
			rootMeanSquaredError+= eval.rootMeanSquaredError();
			rootRelativeSquaredError+= eval.rootRelativeSquaredError();
			relativeAbsoluteError+= eval.relativeAbsoluteError();
			kappaStatistics+= eval.kappa();
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("Currently n= "+n);
		e.printStackTrace();
	}
	System.out.println("FINAL OUTPUT FOR TAG: "+tag);
	System.out.println("Accuracy is: " + accuracySum / folds+"%");
	System.out.println("Precision is: " + 100*sumPrecision / folds+"%");
	System.out.println("Recall is: " + 	100*sumRecall / folds+"%");
	System.out.println("Fmeasure is: " + 100*sumFmeasure / folds+"%");
	System.out.println("Mean absolute error is: "+meanAbsoluteError/folds);
	System.out.println("Root mean squared error is: "+rootMeanSquaredError/folds);
	System.out.println("Relative absolute error is: "+relativeAbsoluteError/folds+"%");
	System.out.println("Root relative squared error is: "+rootRelativeSquaredError/folds+"%");
	System.out.println("Kappa statistics is: "+kappaStatistics/folds);
		}
	private Filter returnMultifilter(Instances data) {
		StringToWordVector textToWordfilter = new StringToWordVector();
		textToWordfilter.setTokenizer(new WordTokenizer());		
		try {
			textToWordfilter.setInputFormat(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		return multiFilter;

	}

	/**
	 * 
	 * @param data
	 * @param percentage
	 * @return - returns a stratified subset of a given dataset at a certain percentage
	 */
	private  Instances returnStratifiedSubset(Instances data, int percentage)
	{
		Resample filter = new Resample();
		Instances filteredIns = null;
		//uniform distribution of positive and negative class
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
