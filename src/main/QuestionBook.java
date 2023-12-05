package main;

import java.util.*;

public class QuestionBook {

	Map<String, String> questions;
	
	public QuestionBook(){
		questions = new HashMap<String, String>();
	}
	
	public Map<String, String> getQuestions() {
		return questions;
	}
	
	public void addQuestion(String q, String a){
		questions.put(q, a);
	}
}
