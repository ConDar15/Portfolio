#Produces an executable jar file for the Predictive Text app

all:
	javac *.java
	jar cfm predictive.jar manifest.mf words *.class
	rm *.class

clean:
	rm *.class predictive.jar
