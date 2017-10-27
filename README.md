# Building a model for suggesting tags for questions posed on stackoverflow.com using Stack Exchange API


## The purpose of the model

The purpose of the model is to suggest a tag for a question on stackoverflow.com. In other words, it should predict whether a question should or should not include a certain tag based on the title and the body of the question itself. Evidently, this is a text mining task of categorizing each question for each tag.

## Tools used

This program was written in Java using weka libraries for machine learning.

## Collecting data

The data needed for this project were questions posed on stackoverflow.com. To achieve that, [this API](http://api.stackexchange.com/) was used. Data was collected using a method that sends HTTP GET requests to a specific URL as long as there are questions that meet the criteria. The data was retrieved in JSON format and only the following attributes of the questions were saved: 
* tags
* creation date
* question id
* title
* body.
<br/>The only criteria was that the questions contained the tag java and that they were published between 01/01/2015 and 01/07/2017. 37 800 questions were collected in total.
### Method for collecting data:

```java
	public ArrayList<Question> getQuestions(int page, ArrayList<Question> allQuestions) throws ParseException {
		String questionURL = "https://api.stackexchange.com/2.2/questions?page="+page+"&pagesize=100&fromdate=1451606400&todate=1467331200&order=desc&sort=activity&tagged=java&site=stackoverflow&filter=!)re8-BBbvkGyazC*-K9O";

		try {
			String result = sendGet(questionURL);
			
			Gson gson = new GsonBuilder().create();
			
			//object that encapsulates everything on the page
			JsonObject questionJson = (JsonObject) gson.fromJson(result, JsonObject.class);
			//are there more objects/pages
			boolean has_more = questionJson.get("has_more").getAsBoolean();
			
			//json array that contains all questions
			JsonArray items = questionJson.get("items").getAsJsonArray();
			
			
			ArrayList<Question> questions = allQuestions;
			
			for (int i = 0; i < items.size(); i++) {
				
			Question question = new Question();
			
			
			//one object in json array items represents one question
			JsonObject objectInItems = (JsonObject) items.get(i);
			
			//that object contains the attributes of the question
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
			
			//if there are more questions that meet the criteria
			//and the number of the current page is less than 100 (total 100*100=10000 questions),
			//return more questions
			if(has_more==true && page<100){
				return getQuestions(++page,questions);
			}
			else
				return questions;
					
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
```
## Preparing data

Considering that the data that was collected contained code that would temper with the process of text mining, the collected questions were edited to be code-free and consist of one block of text containing both the title and the body of the original question. This was achieved by using an HTML pareser from the jsoup library.

### Method for editing the questions:

```java
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
```
## Classification

### Choosing tags

The first decision to be made was which tags should the classifier be trained for. Naturally, these had to be somewhat common tags, in order to give the classifier enough data to learn. The common, or the popular tags are those that appear in a significant number of questions. It was decided that popular tags are those that appear in 100 or more questions out of the 37 800 questions collected. In this dataset, that leaves us with 144 popular tags.

### Creating datasets

Each question either contains a specific tag, or it doesn't. That means that each question can be classified according to a specific tag to a positive class - when it contains the tag, or to a negative class- when it doesn't contain the tag. Therefore, our training and test datasets need only contain 2 attributes:
1. a text attribute containing the text of the question
2. a dichotomous class attribute with classes *yes* and *no*.
<br/>This also means that since each tag is a classification criterium, the number of datasets needed is equal to the number of popular tags. The following method was used to create 144 datasets:

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
  ### Building a classifier
  
  In order to minimize the bias of the classifier, it was decided it would be best to use cross-validation. The classifier used was Naive Bayes with weka filters: AttributeSelection and StringToWordVector. The classifier wasn't applied to the entire datasets, since computing time was incredibly long. Instead, a stratified subset of the dataset was used.
In the end, the final output is based on the subset containing 3% of the original dataset and using 10-fold cross-validation. <br/>**No significant changes in the performance of the classifier were spotted by varying the size of the subset from 2% to 5% or by using 20 folds instead of 10 to reduce the bias**.

### Classification results and performance

The accuracy, precision, recall and fmeasuers for all datasets (tags) is around 80 or 90%. Kappa statistics is a statistic which measures inter-rater agreement for qualitative items. It is considered strong if its value is 0.7 or greater. The results of this classification show a very strong kappa (over 0.8) in most cases, with only 4 classifications with a medium strength kappa of around 0.6. Detailed output with all relevant performance parameters can be found [HERE](output.pdf).

## Conclusion

The parmeters of the classifications display an outstanding performance. This is usually nothing to be too pleased about and raises suspicion. As previously mentioned, cross-validation was used to avoid overfitting. Even the increase in the number of folds (from 10 to 20) which lowers the bias, didn't noticeably change the values of any parameters. So how come this model has such a high success rate at predicting whether a question should contain a tag or not? My personal conviction is that this is due to the fact that nearly every question contains the tag either in its title, or in its body (which essentially means, if we're looking at the datasets, it contains the tag in the text attribute). Therefore, it is easy for the classifier to discover the connection between a question containing a certain word (the tag) and that question having a positive class (for the same tag).
