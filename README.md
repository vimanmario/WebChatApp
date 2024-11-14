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