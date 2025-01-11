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

## 4. Autentificare si securitate

  Acest capitol detaliaza implementarea securitatii si autentificarii folosind Spring Security, fiind de altfel un modul esential pentru protejarea datelor utilizatorilor, gestionarea accesului si prevenirea accesului neautorizat la resursele aplicatiei.

###  4.1. Prezentarea generala a securitatii

  Securitatea este configurata implementand o politica robusta pentru gestionarea autentificarii, autorizarii si securizarii sesiunilor utilizatorilor.
Principalele caracteristici ale configuratiei:
* dezactivarea protectiei CSRF (pentru simplitatea in dezvoltate, se poate reactiva in productie).
* permisiuni de acces definite in functie de endpoint-uri.
* configurare pagini personalizate de autentificare.
* gestionarea sesiunilor multiple si expirate.
* functionalitati de "remember-me" si logout.
  
###  4.2. Autentificarea utilizatorilor

  Aceasta este gestionata prin incarcarea utilizatorilor dintr-un serviciu personalizat (UserService) si validarea credentialelor acestora utilizand PasswordEncoder bazat pe algoritmul BCrypt.

###  4.3. Fluxul de autentificare

1. Utilizatorul acceseaza pagina de login disponibile /login.
2. Introduce numele de utilizator si parola.
3. Credentialele sunt validate cu ajutorul UserService.
4. In cazul unei autentificari reusite, utilizatorul este redirectionat catre /chat.
5. In caz de eroare, utilizatorul ramane pe pagina de Login si primeste un mesaj corespunzator.

###  4.4. Autorizare si gestionare roluri

  Accesul la resurse nu este controlat pe baza rolurilor utilizatorilor. Momentan, aceasta presupune un singur nivel de acces pentru utilizatorii autentificati. Endpoint-uri precum /api/conversation sunt protejate si necesita autentificare. 

###  4.5. Securizarea cookie-urilor si sesiunilor

  Aplicatia implementeaza protectie suplimentara prin 2 filtre:
* SameSiteCookieFilter: adauga flag-ul SameSite = Strict pentru toate cookie-urile.
* NoCacheFilter: previne stocarea informatiilor sensibile in cache.

## 5. API Endpoints

| Endpoint | Metode | Descriere |
| --------------- | --------------- | --------------- |
| /login | GET | Returneaza pagina de login |
| /register | GET | Creeaza un obiect de tip user; Returneaza pagina de inregistrare |
| /chat | GET | Returneaza pagina de chatting daca utilizatorul este autentificat, altfel retunreaza pagina de login |
| /chat.sendMessage | POST | Va gestiona mesajele primite pe aceasta ruta (conversatia generala) |
| /topic/public | POST | Indica faptul ca mesajele procesate vor fi trimite la toti utilizatorii abonati la acest topic (conversatia generala) |
| /chat.loadMessagesByConversation | POST | Gestioneaza mesajele primite pe aceasta ruta |
| /topic/public | POST | Indica faptul ca mesajele procesate vor fi trimite la toti utilizatorii abonati la acest topic (conversatie privata) |
| /api/conversations | POST | Crearea unei conversatii noi |
| /api/conversations/user/{userId} | GET | Obtine toate conversatiile relevante pentru un utilizator specific |
| /api/exists | GET | Verifica daca exista o conversatie intre 2 utilizatori specifici |
| /api/files/upload | POST | Permite incarcarea unui fisier de catre utilizator |
| /api/files/{fileName:.+} | POST | Permite descarcarea unui fisier pe baza numelui acestuia |
| / | POST | Redirectionare catre /chat daca utilizatorul e autentificat si catre /login in caz contrar |
| /logout | POST | Deconecteaza utilizatorul curent |
| /api/messages | GET | Returneaza toate mesajele disponibile in sistem la nivel global (conversatia generala)|
| /api/messages/{conversationId} | GET | Returneaza toate mesajele disponibile intr-o conversatie specifica |
| /api/current-user | GET | Returneaza toate informatiile despre utilizatorul curent |
| /api/users | GET | Returneaza o lista cu toti utilizatorii, excluzant utilizatorul curent |

## 6. Comunicarea folosind WebSockets

###  6.1. Server

  WebSockets permite comunicarea bidirectionala eficienta intre client si server. In aplicatia noastra, am utilizat biblioteca Spring WebSocket si protocolul STOMP.

###  6.2. Broker

* /topic : Utilizat pentru mesaje publice.
* /app : prefix pentru mesajele din aplicatie (mesajele trimise de utilizatori).

###  6.3. Endpoint-uri

* /chat-websocket : punctul de conectare al utilizatorilor
* Interceptori : adauga sesiunea HTTP la atributele WebSocket, facilitand asocierea cu utilizatorul conectat.
* SockJS : asigura compatibilitatea cu browsere care nu suporta WebSocket nativ.

###  6.4. Client

  Client-ul utilizeaza SockJS si STOMP pentru conectarea la server si schimb-ul de mesaje.
* SockJS: creeaza o conexiune WebSocket sau un fallback compatibil.
* Stomp: gestioneaza trimiterea mesajelor si "abonarea la topicuri", mesajele primite fiind afisate in interfata utilizatorului.


## 7. Documente de testare si probleme

###  7.1. Testare si probleme rezolvate

  De precizat ca, in lipsa unor standarde de testare, s-a optat pentru folosirea unei abordari bazata pe teste de verificare manuale a fiecarei functionalitati, validand comportamentul si functionarea acestora in mod repetat.
Cele mai comune probleme au fost legate de relatiile in modele, de fiecare data fiind necesara alegerea caracteristicei EAGER in loc de LAZY pentru incarcarea corecta ( a mesajelor text in prima faza, iar apoi a mesajelor cu atasamente). Introducerea capabilitatii de trimitere a atasamentelor a dat putin peste cap logica generala de trimitere a mesajelor, invocand erori precum "INVALID DATE" si faptul ca atasamentul nu este trimis instant, probleme rezolvate ulterior.

###  7.2. Probleme ce vor fi rezolvate

  Ca si probleme nerezolvate avem redenumirea conversatiilor private, aceasta problema fusese rezolvata initial, insa odata cu implementarea atasamentelor si schimbarea anumitor elemente de conversatie in back-end, codul care facea sa redenumeasca conversatiile private pentru fiecare participant (in numele celuilalt) nu mai functioneaza , fiindca ar fii necesare niste modificari in back-end. Dupa redenumirea conversatiilor ar trebui rezolvate numele din head-urul fiecaruia, fiindca momentan la fiecare apare "General", nu doar la conversatia generala. De altfel ar trebui ca atunci cand apas pe butonul de search mesaj in conversatie, sa nu se selecteze toate mesajele daca este casuta goala ( sa implementam o logica prin care ca sa putem da search unui mesaj sa fie minim 2 caractere introdusa in search-bar). De altfel o problema a carei cauze nu a fost gasite tine de log-out, fiindca in momentul in care dam log-out si avem o sesiune activa ( de ex pt 2 ore ), aceasta nu este invalidata, practic cookie-ul nu este sters din browser. Ca si posibile cauze ar fi ca defapt cookie-ul este sters doar se creeaza mai multe cookie-uri la logare.

## 8. Alte cerinte

  Exista o serie de cerinte/caracteristici care nu sunt acoperite de MoSCoW list, insa acestea ar putea reprezenta un factor important in popularizarea aplicatiei in randul utilizatorilor. In momentul de fata, putem aminti functii:
* **conversatii tip grup**:  difera de conversatiile generale prin faptul ca doar utilizatorii adaugati au acces la conversatie, si de altfel exista un numar maxim de utilizatori care pot fi introdusi intr-un grup.
* **poze/video-uri instant**: se refera la existenta unui buton pentru crearea de materiale foto/video direct din aplicatie si distribuirea lor.
* **schimbarea numelui conversatiei direct in aplicatie**.
* **setarea de nickname unui utilizator in cadrul conversatiilor de grup**: poate aduce amuzant in cadrul utilizatorilor.
* **trimitere emoji-uri, stickere, gif-uri**.

## 9. Model de analiza

  Diagrama EER este o reprezentare vizuala a structurii datelor in cadrul aplicatiei noastre de Chat Online. Acest model prezinta atat entitatile, cat si relatiile dintre ele, oferind o intelegere clara a modului in care datele sunt organizate in sistem.. Aceasta diagrama indentifica relatiile cheie precum user, messages, conversations, conversation_participants si message_attachments.

  ![Capture](https://github.com/user-attachments/assets/bbdf8ba7-fe06-4345-8b01-d7ba8a21123b)

