# WebChat Application with Spring Boot

## 1. Introducere

### 1.1. Scop

  Scopul acestui proiect este de a dezvola dezvolta o aplicatie de chat web implementand o varietate de tehnologii pentru a asigura o experienta de chat in timp real si un mediu securizat pentru utilizatori. Proiectul urmareste sa creeze o experienta de comunicare eficienta, securizata si usor de utilizat, integrand functionalitati esentiale precum interactiuni personale si de grup.

### 1.2. Prezentare generala

  Aplicația permite utilizatorilor să se alăture, să discute și să părăsească camerele de chat în timp real. Spring Boot oferă o arhitectură robustă și scalabilă pentru aplicație, în timp ce WebSocket permite comunicarea în timp real între server și clienți. Aplicația are funcții precum alăturarea camerelor de chat, trimiterea de mesaje și părăsirea camerelor de chat, oferind o experiență perfectă și interactivă pentru utilizatorii săi.

## 2. Elemente de implementare

### 2.1. Stack tehnologic
- **Spring Boot:** configurare rapida si structura de baza pentru aplicatie.
- **Spring Security:** ofera un sistem de autentificare si autorizare pentru securizarea aplicatiei.
- **Spring Data JPA:** permitere gestionarea si persistenta datelor utilizatorilor. 
- **Web Session:** gestioneaza sesiunile utilizatorilor in mod securizat.
- **WebSockets:** suporta comunicarea bidirectionalea in timp real intre client si server.
- **Thymeleaf:**

### 2.2. MoSCoW List

MUST: 
- conversatie publica 
- conversatii 1 la 1

SHOULD: 
- capabilitate de a trimite atasamente.
- lista utilizatori.
- UI intuitiv. 

COULD:
- reactie la mesaje
- apeluri video
- redenumire conversatie/setare nickname
- setare poza profil in aplicatie

WON'T:
- contacte
- lista utilizatorilor conectati

### 2.3. Configurare
Cerinte preliminare:
- Java 17
- Maven
- Baza de date MySql + Hibernate

## 3. Prezentare generala

### 3.1. Arhitectura aplicatiei
  Aplicatia utilizeaza un design bazat pe arhitectura MVC(Model-View-Controller), fiecare componenta a proiectului fiind responsabila de un aspect specific al aplicatiei asigurand o separare clara a functionalitatilor. Model gestioneaza logica aplicatiei si interactiunea cu baza de date prin Spring JPA. View asigura afisarea datelor si interactiunea utilizatorilor prin templaturi-le Thymeleaf. Controller primeste cererile de la utilizator, interactioneaza cu serviciile si returneaza raspunsuri catre view. Aplicatia este de asemenea construita folosind module pentru securitate, gestionarea sesiunilor si comunicarea in timp real.

![WebAppArchitecture drawio](https://github.com/user-attachments/assets/69940a13-e91b-410a-bb31-60518e381be3)

### 3.2. Structura proiectului
  Proiectul este organizat in urmatoarele pachete principale:
- **controller**: contine clasele care gestioneaza cererile http. Ex: AuthController, ChatController etc.
- **repository**: interactioneaza cu baza de date folosind SpringJPA. Ex: UserRepository etc.
- **model**: contine clasele/entitatile necesare necesare pentru persistenta datelor. Ex: User etc.
- **config**: include configurarile aplicatiei pentru securitate, websockets precum si altele. Ex: SecurityConfig, EncoderConfig, WebSocketConfig etc.
- **service**: aici este implementata logica de business. Ex: UserService etc.
- **filter**: folosit pentru a monitoriza cookie-urile, sessiunile, cache-ul si debugging.

###  3.3. Fluxul de date si utilizator
  Acesta este reprezentat in prima faza de autorizare si autentificare, unde utilizatorul trimide datele de autentificare catre server, iar Spring Security valideaza datele si stocheaza detaliile in cadrul sesiunii active. In cadrul schimbului de mesaje intre utilizatori, acestia trimit mesajele printr-o conexiune WebSocket, iar serverul le proceseaza si trasmite mai departe. Mesajele si informatiile despre utilizatori sunt salvate in baza de date, asigurandu-se persistenta acestora, si istoricul mesajelor.

  ![UserFlowDiagram drawio](https://github.com/user-attachments/assets/83901e33-e588-4a2b-b7e1-6801769f7daa)

## 6. Documente de testare si probleme

###  6.1. Testare si probleme rezolvate

  De precizat ca, in lipsa unor standarde de testare, s-a optat pentru folosirea unei abordari bazata pe teste de verificare manuale a fiecarei functionalitati, validand comportamentul si functionarea acestora in mod repetat.
Cele mai comune probleme au fost legate de relatiile in modele, de fiecare data fiind necesara alegerea caracteristicei EAGER in loc de LAZY pentru incarcarea corecta ( a mesajelor text in prima faza, iar apoi a mesajelor cu atasamente). Introducerea capabilitatii de trimitere a atasamentelor a dat putin peste cap logica generala de trimitere a mesajelor, invocand erori precum "INVALID DATE" si faptul ca atasamentul nu este trimis instant, probleme rezolvate ulterior.

###  6.2. Probleme ce vor fi rezolvate

  Ca si probleme nerezolvate avem redenumirea conversatiilor private, aceasta problema fusese rezolvata initial, insa odata cu implementarea atasamentelor si schimbarea anumitor elemente de conversatie in back-end, codul care facea sa redenumeasca conversatiile private pentru fiecare participant (in numele celuilalt) nu mai functioneaza , fiindca ar fii necesare niste modificari in back-end. Dupa redenumirea conversatiilor ar trebui rezolvate numele din head-urul fiecaruia, fiindca momentan la fiecare apare "General", nu doar la conversatia generala. De altfel ar trebui ca atunci cand apas pe butonul de search mesaj in conversatie, sa nu se selecteze toate mesajele daca este casuta goala ( sa implementam o logica prin care ca sa putem da search unui mesaj sa fie minim 2 caractere introdusa in search-bar). 

## 7. Alte cerinte

  Exista o serie de cerinte/caracteristici care nu sunt acoperite de MoSCoW list, insa acestea ar putea reprezenta un factor important in popularizarea aplicatiei in randul utilizatorilor. In momentul de fata, putem aminti functii:
* **conversatii tip grup**:  difera de conversatiile generale prin faptul ca doar utilizatorii adaugati au acces la conversatie, si de altfel exista un numar maxim de utilizatori care pot fi introdusi intr-un grup.
* **poze/video-uri instant**: se refera la existenta unui buton pentru crearea de materiale foto/video direct din aplicatie si distribuirea lor.
* **schimbarea numelui conversatiei direct in aplicatie**
* **setarea de nickname unui utilizator in cadrul conversatiilor de grup**: poate aduce amuzant in cadrul utilizatorilor.
* **trimitere emoji-uri, stickere, gif-uri**

## 8. Model de analiza

  Diagrama EER este o reprezentare vizuala a structurii datelor in cadrul aplicatiei noastre de Chat Online. Acest model prezinta atat entitatile, cat si relatiile dintre ele, oferind o intelegere clara a modului in care datele sunt organizate in sistem.. Aceasta diagrama indentifica relatiile cheie precum user, messages, conversations, conversation_participants si message_attachments.

  ![Capture](https://github.com/user-attachments/assets/bbdf8ba7-fe06-4345-8b01-d7ba8a21123b)

