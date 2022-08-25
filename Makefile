#JAVAC=C:\Java\jdk-16\bin\javac.exe
JAVAC = javac
.SUFFIXES: .java .class
SRCDIR=src\typingTutor
BINDIR=bin\typingTutor

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d bin/ -cp bin/ $<
	
CLASSES=WordDictionary.class \
		FallingWord.class \
		HungryWord.class \
		Score.class \
		CatchWord.class \
		GamePanel.class \
		ScoreUpdater.class \
		WordMover.class \
		HungryWordMover.class \
		TypingTutorApp.class \
		
		

                 
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

jar: $(CLASS_FILES)
	cd bin
	jar -cvfe typingTutor.jar typingTutor.TypingTutorApp $(BINDIR)/*.class

clean:
	del $(BINDIR)\*.class
	