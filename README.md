# trener-slov
Tréner slovnej zásoba


# Návod na inštaláciu
Stiahnut projekt z master branche
- ### Ak je projekt otvorený v IntelliJ
Stačí pridať JAR artifact a ten buildnut, nasledne spustit vytvorený .jar 
(na vytvorenie JAR artifactu v intelliJ navod [tu](https://blog.jetbrains.com/idea/2010/08/quickly-create-jar-artifact/))

- ### Ak chcete vybuildovať projekt cez cmd
Java verzia 8 a nižsie
v prienčinku src/ :

javac -encoding "UTF-8" -cp ../lib/jaudiotagger-2.0.2.jar -cp
"../lib/*" */*.java */*/*.java */*/*/*.java

**Ak javac hodi exception 'javac is not recognized as an internal or external command...'**
treba si nainstalovat java jdk 1.8 a spravne si nastavit PATH (navod [tu](https://stackoverflow.com/questions/7709041/javac-is-not-recognized-as-an-internal-or-external-command-operable-program-or))

a na spustenie:
java -cp "../lib/jaudiotagger-2.0.2.jar;../lib/jl1.0.1.jar;." application.Main

**Java verzia 9 a vyššie sa da vybuildovať iba stiahnutím projektu z branche s názvom 'Java10' a vytvorením JARka z programovacieho prostredia ako IntelliJ alebo Eclipse**
