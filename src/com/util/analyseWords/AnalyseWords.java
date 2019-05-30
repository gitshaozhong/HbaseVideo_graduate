package com.util.analyseWords;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

public class AnalyseWords {
	private List<String> words = new ArrayList<String>();
	public AnalyseWords(String query){
		Reader reader = new StringReader(query);
		IKSegmentation ik = new IKSegmentation(reader, true);
			try {
						Lexeme lex = null;
						while(true){
							lex = ik.next();
							words.add(lex.getLexemeText());
						}
					} catch (NullPointerException e) {
						//System.out.println("--null--");
					} catch (IOException e) {
						e.printStackTrace();
						
					}
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}
	public static void main(String[] args) {
		AnalyseWords an = new AnalyseWords("基于Hbase的海量视频存储技术的研究");
		for(String word : an.getWords()){
			System.out.println(word);
		}
	}
}
