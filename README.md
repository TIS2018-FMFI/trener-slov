# trener-slov
Tréner slovnej zásoby


# Návod na inštaláciu
Stiahnut projekt z master branche
- ### Ak je projekt otvorený v IntelliJ
Ak nemáte pridaný Maven v projekte tak pravým tlačitkom myši kliknite na projektový priečinok -> Add Framework Support a v nom vyberte Maven </br>
Pridajte kniznice **jaudiotagger** a **jl1.0.1** do projektových nastavení. </br>
Podla toho na akú verziu Javy chcete buildnut program prepíšte
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.8.0</version>
    <configuration>
        <source>1.8</source>
        <target>1.8</target>
    </configuration>
</plugin>
```
"\<source>" a "\<target>" cislo na napr 1.9 ak chcete buildnut pre Javu 9 a zmenit verziu v Project Structure v IntelliJ. 
(Vždy ked zmenite pom.xml súbor sa vás IntelliJ spýta či chcete Importnut zmeny tak nezabudnite to potrvdiť pokial to nemáte automaticky importované) </br>
Ako posledné pridať JAR artifact a ten buildnut, nasledne spustit vytvorený .jar 
(na vytvorenie JAR artifactu v intelliJ navod [tu](https://blog.jetbrains.com/idea/2010/08/quickly-create-jar-artifact/))

- ### Ak chcete vybuildovať projekt cez cmd
Java verzia 8 a nižsie
v prienčinku src/ :

```
javac -encoding "UTF-8" -cp ../lib/jaudiotagger-2.0.2.jar -cp "../lib/*" */*.java */*/*.java */*/*/*.java
```

**Ak javac hodí exception 'javac is not recognized as an internal or external command...'**
treba si nainstalovat java jdk 1.8 a spravne si nastavit PATH (navod [tu](https://stackoverflow.com/questions/7709041/javac-is-not-recognized-as-an-internal-or-external-command-operable-program-or))

a na spustenie:
```
java -cp "../lib/jaudiotagger-2.0.2.jar;../lib/jl1.0.1.jar;." application.Main
```

**Nakoniec program viete spustit cez .jar(ked to nefunguje) subor alebo spustenim prikazu** ```java -jar (cesta k jar suboru)``` 

Verziu väčšiu ako Java 8 odporúčame buildovať cez programovacie prostredie ako napr IntelliJ
 
 # Popis módov:
 - ### Učenie - interaktívny mód, ktorý podporuje učenie slovnej zásoby
 - ### Skúšanie - interaktívny mód, ktorý skúša naučenú slovnú zásobu
 - ### Stacionárny bicykel - prezentácia obrázkov a odpovedí
 - ### Diktát - interaktívny mód, ktorý trénuje písanie
 
# Návod na ovládanie

**Ovládanie**

Celá aplikácia sa ovláda iba pomocou myši. Klávesnica sa používa iba na písanie textov a v módoch.
Po zapnutí aplikácie sa zobrazia dve tlačidlá v strede a jedno v pravom dolnom rohu.
    - ### Po kliknutí na tlačidlo Štart sa používateľ dostane na prehľad lekcii, z ktorej si jenu vyberie – klikne na ňu a potom klikne na tlačidlo v pravom dolnom rohu - dostane sa na výber módov z ktorých si vyberie kliknutím na jednotlivé tlačidlá
    - ### Po kliknutí na tlačidlo Úprava dát uvidí používateľ prehľad lekcii. Podľa toho, čo chce upravovať sa dokliká cez tlačidlá Upraviť. Napríklad ak by chcel pridať skupinu do druhej lekcie – klikne na druhú lekciu Upraviť a potom Vytvor skupinu – tam len zadá meno skupiny alebo si vyhľadá už existujúcu skupinu a tú pridá do vybranej lekcie. Tak isto sa vytvára aj položka. Len treba vybrať lekciu a skupinu a do tejto sa položka vytvorí.
    - ### Po kliknutí na tlačidlo v pravom dolnom rohu si používateľ môže nastaviť veľkosť zobrazovaného fontu v celej aplikácii

**Ovládanie v módoch**

Tento postup platí pre módy:
- Učenie – pred spustením módu treba zadať počet, koľkokrát sa má jedna otázka položiť, aby bola naučená
- Skúšanie
-  Diktát

Keď začne bežať mód, používateľovi budú prezentované jednotlivé otázky, na ktoré si sám slovne odpovie. Potom klikne na tlačidlo zobraziť odpoveď - na vykonanie tohto úkonu môže použiť aj ľubovoľnú z kláves U, T - a následne na tlačidlo s fajkou - na vykonanie tohto úkonu môže použiť aj ľubovoľnú z kláves J, K, L - ak bola odpoveď správna. Ak bola odpoveď nesprávna klikne na tlačidlo s krížikom - na vykonanie tohto úkonu môže použiť aj ľubovoľnú z kláves A, S, D, F. Takto odpovedá, až kým aplikácia nevypíše, že sa mód skončil. Potom si môže vybrať, či chce mód spustiť znova alebo ísť do menu.
Tento postup platí pre mód stacionárny bicykel
Pred začatím módu si používateľ musí nastaviť:
- Počet prehratí odpovede – koľkokrát sa má odpoveď prehrať za sebou
- Trvanie otázky v sekundách – koľko sekúnd má trvať dokopy prehranie zvuku otázky a odpovede – teda uplné zobrazenie položky
- Trvanie módu v sekundách – po koľkých sekundách sa má mód skončiť

Po začatí módu používateľ už len sleduje prezentáciu jednotlivých položiek. Po zadanom časovom intervale sa mód skončí.
 
