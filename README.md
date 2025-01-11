# WebChat Application with Spring Boot

## 1. Introducere
  Acest proiect dezvolta o aplicatie de chat web implementand o varietate de tehnologii pentru a asigura o experienta de chat in timp real si un mediu securizat pentru utilizatori.
  Aplicația permite utilizatorilor să se alăture, să discute și să părăsească camerele de chat în timp real. Spring Boot oferă o arhitectură robustă și scalabilă pentru aplicație, în timp ce WebSocket permite comunicarea în timp real între server și clienți. Aplicația are funcții precum alăturarea camerelor de chat, trimiterea de mesaje și părăsirea camerelor de chat, oferind o experiență de chat perfectă și interactivă pentru utilizatorii săi.

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
- capabilitate de trimitere poze, fisiere, video etc.

COULD:
- reactie la mesaje
- apeluri video
- poze facute si trimise instant
- redenumire conversatie/setare nickname
- trimitere emoji-uri, stickere
- setare poza profil in aplicatie

WON'T:
- trimitere audio
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

