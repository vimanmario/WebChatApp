# WebChat Application with Spring Boot

### Introducere
  Acest proiect dezvolta o aplicatie de chat web implementand o varietate de tehnologii pentru a asigura o experienta de chat in timp real si un mediu securizat pentru utilizatori.
  Aplicația permite utilizatorilor să se alăture, să discute și să părăsească camerele de chat în timp real. Spring Boot oferă o arhitectură robustă și scalabilă pentru aplicație, în timp ce WebSocket permite comunicarea în timp real între server și clienți. Aplicația are funcții precum alăturarea camerelor de chat, trimiterea de mesaje și părăsirea camerelor de chat, oferind o experiență de chat perfectă și interactivă pentru utilizatorii săi.

### Stack Tehnologic
- **Spring Boot:** configurare rapida si structura de baza pentru aplicatie.
- **Spring Security:** ofera un sistem de autentificare si autorizare pentru securizarea aplicatiei.
- **Spring Data JPA:** permitere gestionarea si persistenta datelor utilizatorilor. 
- **Web Session:** gestioneaza sesiunile utilizatorilor in mod securizat.
- **WebSockets:** suporta comunicarea bidirectionalea in timp real intre client si server.
- **Thymeleaf:**

### MoSCoW List

MUST: 
- conversatie publica 
- conversatii 1 la 1
- grupuri

SHOULD: 
- capabilitate de trimitere poze, fisiere, video etc.
- lista utilizatorilor conectati
- setare poza profil in aplicatie

COULD:
- reactie la mesaje
- apeluri video
- poze facute si trimise instant
- redenumire conversatie/setare nickname
- trimitere emoji-uri, stickere

WON'T:
- trimitere audio
- contacte

### Configurare
Cerinte preliminare:
- Java 17
- Maven
- Baza de date MySql + Hibernate

### Arhitectura aplicatiei
  Aplicatia utilizeaza un design bazat pe arhitectura MVC(Model-View-Controller), fiecare componenta a proiectului fiind responsabila de un aspect specific al aplicatiei asigurand o separare clara a functionalitatilor. Model gestioneaza logica aplicatiei si interactiunea cu baza de date prin Spring JPA. View asigura afisarea datelor si interactiunea utilizatorilor prin templaturi-le Thymeleaf. Controller primeste cererile de la utilizator, interactioneaza cu serviciile si returneaza raspunsuri catre view. Aplicatia este de asemenea construita folosind module pentru securitate, gestionarea sesiunilor si comunicarea in timp real.

