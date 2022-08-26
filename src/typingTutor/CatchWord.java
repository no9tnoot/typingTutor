package typingTutor;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Arrays;
import java.util.Collections;

//Thread to monitor the word that has been typed.
public class CatchWord extends Thread {
	String target;
	static AtomicBoolean done ; //REMOVE
	static AtomicBoolean pause; //REMOVE
	
	private static FallingWord[] words; //list of words
	private static HungryWord[] hWord;
	private static int noWords; //how many
	private static Score score; //user score
	
	CatchWord(String typedWord) {
		target=typedWord;
	}
	
	public static void setWords(FallingWord[] wordList, HungryWord[] hw) {
		words=wordList;	
		hWord = hw;
		noWords = words.length;
	}
	
	public static void setScore(Score sharedScore) {
		score=sharedScore;
	}
	
	public static void setFlags(AtomicBoolean d, AtomicBoolean p) {
		done=d;
		pause=p;
	}
	
	public void run() {
		int i=0;
      	int ymax = 0;
      	int maxIndex = 0;
      	//int lowTarget = 0;
      	//boolean found = false;
      	FallingWord[] targets = new FallingWord[noWords];
      	Integer[] tarY = new Integer[noWords];
      
		while (i<noWords) {		
			while(pause.get()) {};
			if (words[i].matchWord(target)) {
            for (int j = 0; j < noWords; j++)//check all words and create array with all matches
               {
               if (words[j].matchWord(target))
                  {
                  targets[j] = words[j];
                  tarY[j] = words[j].getY();
                  }
				else tarY[j] =0;
               }
            ymax = Collections.max(Arrays.asList(tarY));
            maxIndex = Arrays.asList(tarY).indexOf(ymax);//find highest y value corresponding to lowest word
            targets[maxIndex].resetWord();

			System.out.println( " score! '" + target); //for checking
			score.caughtWord(target.length());
				//FallingWord.increaseSpeed();
				break;
			}
         i++;
		}
	}	
}
