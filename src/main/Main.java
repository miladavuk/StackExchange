package main;

import classifier.ClassifierBuilder;

/**
 * 
 * @author Milada
 *
 */
public class Main {

	public static void main(String[] args)  {

		ClassifierBuilder cb = new ClassifierBuilder();
		cb.buildAllClassifiers(5, 10);
	}
	
	
	
	

}
