package typingTutor;

import java.awt.geom.Point2D;

public class HungryWord {
	private String word; // the word
	private int len;
	private int x; //position - width
	private int y; // postion - height
	private int maxX; //maximum height
	private boolean dropped; //flag for if user does not manage to catch word in time
	
	private int moveSpeed; //how fast this word is
	private static int maxWait=1000;
	private static int minWait=100;

	private int eaten;
	private int score;

	public static WordDictionary dict;
	
	HungryWord() { //constructor with defaults
		word="computer"; // a default - not used
		x=0;
		y=150;	
		maxX=990;
		dropped=false;
		moveSpeed=(int)(Math.random() * (maxWait-minWait)+minWait);
		len = word.length(); 
	}
	
	HungryWord(String text) { 
		this();
		this.word=text;
	}
	
	HungryWord(String text,int y, int maxX) { //most commonly used constructor - sets it all.
		this(text);
		this.y=y; //only need to set y, word is at left of screen at start
		this.maxX=maxX;
	}
	
	public static void increaseSpeed( ) {
		minWait+=50;
		maxWait+=50;
	}
	
	public static void resetSpeed( ) {
		maxWait=1000;
		minWait=100;
	}
	

// all getters and setters must be synchronized
	public synchronized  void setY(int y) {
		this.y=y;
	}
	
	public synchronized  void setX(int x) {
        if (x>maxX) {
			x=maxX;
			dropped=true; //user did not manage to catch this word
		}
		this.x=x;
	}
	
	public synchronized  void setWord(String text) {
		this.word=text;
	}

	public synchronized  String getWord() {
		return word;
	}
	
	public synchronized  int getX() {
		return x;
	}	
	
	public synchronized  int getY() {
		return y;
	}
	
	public synchronized  int getSpeed() {
		return moveSpeed;
	}

	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}

	public synchronized int getEaten()
		{
		return eaten;
		}

	public synchronized int getScore()
		{
		return score;
		}

	public synchronized void eat(FallingWord f)
		{
		//System.out.println("trying to eat " + f.getWord());
		int ydist = Math.abs(y-f.getY());
		int xdist = Math.abs(x-f.getX());

		//if (Point2D.distance(x, y, f.getX(), f.getY()) < 20)
		if ((ydist < 25) && (xdist < 12 * len))
			{
			//score.missedWord();
			score += f.getWord().length();
			eaten++;
			f.resetWord();
			}
		}
	public synchronized void resetPos() {
		setX(0);
	}

	public synchronized void resetWord() {
		resetPos();
		eaten = 0;
		word=dict.getNewWord();
		dropped=false;
		moveSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		//System.out.println(getWord() + " move speed = " + getSpeed());
	}
	
	public synchronized boolean matchWord(String typedText) {
		//System.out.println("Matching against: "+text);
		if (typedText.equals(this.word)) {
			//resetWord();
			return true;
		}
		else
			return false;
	}

	public synchronized  void drop(int inc) {
		setX(x+inc);
	}
	
	public synchronized  boolean dropped() {//change this!!!
		return dropped;
	}

}
