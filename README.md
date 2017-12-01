# Building a model for recommending tags for questions posed on stackoverflow.com using Stack Exchange API


## The Problem

The purpose of this project is to generate a model that reccommends a tag for a question on the website [StackOverflow](www.stackoverflow.com). In other words, it should predict whether a question should or should not include a certain tag based on the title and the body of the question. 

## Tools Used

This program was written in Java using [Weka libraries](https://www.cs.waikato.ac.nz/ml/weka/) for machine learning. [Jsoup HTML Parser](https://jsoup.org/) is used for the text cleaning.

## Data Collection

The data needed for this project were questions posted on the StackOverflow website. The questions were obtained from the [StackExchange API](http://api.stackexchange.com/). The data was retrieved in JSON format and only the following attributes of a question were saved: 
* tags
* creation date
* question id
* title
* body.

The questions pulled from the API needed to fullfill the following criteria:
- they should be tagged with the tag *java*
- they should be published between 01/01/2015 and 01/07/2017. 

37 800 questions were collected in total.

## Data Preprocessing

Since all questions were related to programming, besides the textual description of a question, they also included code snippets. Since the code snippets are not relevant for our building our classification model, and could actually introduce noise, the collected questions were edited not to include any code snippets. At the end, only the title and textual description of a question were saved. 

## Classification

In order for this model to be able to recommend tags for a question, what it should essentially do is use text mining techniques to classify each question to a predefined class <b>yes</b> (the tag should be recommended for that question) or <b>no</b> (the tag should not be recommended for that question).

### Choosing tags

The first decision to be made was which tags should the classifier be trained for. Naturally, these had to be somewhat common tags, in order to give the classifier enough data to learn. The common, or the popular tags are those that appear in a significant number of questions. It was decided that popular tags are those that appear in 100 or more questions in our dataset. In our dataset, there are 144 popular tags.

### Creating datasets

Each question either contains a specific tag, or it doesn't. That means that each question can be classified according to a specific tag to a positive class - when it contains the tag, or to a negative class - when it doesn't contain the tag. Therefore, our tag datasets contain two attributes:
1. a text attribute containing the text of the question (title and question as one string)
2. a dichotomous class attribute with classes *yes* and *no*.

Since we have 144 popular tags, we have generated 144 datasets for each tag.

The following method was used to create 144 datasets:

```java
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
  ```
  However, classification wasn't applied to the entire datasets, but rather to a subset taking up 3% of the original dataset in order to shorten the computing time. The subset was stratified, meaning that the class attribute had a roughly uniform distribution.
  
### Building Classifier
  
Naive Bayes was used as the classification algorithm. Also, in order to minimize the bias of the classifier, we have used cross-validation. 
In the end, the final output is based on the subset containing 3% of the original dataset and using 10-fold cross-validation. <br/>**No significant changes in the performance of the classifier were spotted by varying the size of the subset from 2% to 5% or by using 20 folds instead of 10 to reduce the bias**.

### Classification results and performance

The classification metrics (Accuracy, Precision, Recall and F measure) for all datasets (tags) is around 80 or 90%. Cohen's Kappa statistics is a statistic which measures inter-rater agreement for qualitative items. It is considered strong if its value is 0.7 or greater. The results of this classification show a very strong kappa (over 0.8) in most cases, with only 4 classifications with a medium strength kappa of around 0.6. Detailed output with all relevant performance parameters can be found [HERE](output.pdf).

## Conclusion

The parmeters of the classifications display an outstanding performance. This is usually nothing to be too pleased about and raises suspicion. As previously mentioned, cross-validation was used to avoid overfitting. Even the increase in the number of folds (from 10 to 20) which lowers the bias, didn't noticeably change the values of any parameters. So how come this model has such a high success rate at predicting whether a question should contain a tag or not? My personal conviction is that this is due to the fact that nearly every question contains the tag either in its title, or in its body (which essentially means, if we're looking at the datasets, it contains the tag in the text attribute). Therefore, it is easy for the classifier to discover the connection between a question containing a certain word (the tag) and that question having a positive class (for the same tag).

## References

1. [A Discriminative Model Approach for Suggesting Tags Automatically for Stack Overflow Questions](https://www.cs.usask.ca/faculty/kas/Publications_files/msr13-id175-p-16622-preprint.pdf)
2. [Tag Recommender for StackOverflow Questions](https://cseweb.ucsd.edu/~jmcauley/cse255/reports/wi15/Manindra_Moharana.pdf)
