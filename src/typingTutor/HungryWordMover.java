package typingTutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class HungryWordMover extends Thread {
	private HungryWord myWord;
	private AtomicBoolean done;
	private AtomicBoolean pause; 
	private Score score;
	CountDownLatch startLatch; //so all can start at once
	
	HungryWordMover( HungryWord word) {
		myWord = word;
	}
	
	HungryWordMover( HungryWord word,WordDictionary dict, Score score,
			CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
		this(word);
		this.startLatch = startLatch;
		this.score=score;
		this.done=d;
		this.pause=p;
	}
	
	
	
	public void run() {

		//System.out.println(myWord.getWord() + " falling speed = " + myWord.getSpeed());
		try {
			System.out.println("Hungry word " + myWord.getWord() + " waiting to start " );
			startLatch.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //wait for other threads to start
		System.out.println("Hungry word " + myWord.getWord() + " started" );
		while (!done.get()) {				
			//animate the word
			while (!myWord.dropped() && !done.get()) {

					if (myWord.getHidden()) //if hidden, hungry word will not move or eat or be drawn for 1-10 seconds
						{
						int timer = ThreadLocalRandom.current().nextInt(1000, 10001);
						//System.out.println("sleeping for " + timer/1000 + " seconds");
						try {
							sleep(timer);
							} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							};
						myWord.setHidden(false);
						}

				    myWord.drop(10);
					try {
						sleep(myWord.getSpeed());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};		
					while(pause.get()&&!done.get()) {};
			}
			if (!done.get() && myWord.dropped()) {
				score.missedWord(myWord.getEaten());
				myWord.setHidden(true);
				myWord.resetWord();
			}
		}
	}
	
}
