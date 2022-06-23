# Istruzioni di caricamento
Il progetto è stato interamente realizzato con Visual Studio Code utilizzando le estensioni per gestire progetti Java e Spring:
- Spring Boot Extension Pack v0.1.0
- Extension Pack for Java v0.23.0

Istruzioni per installare JDK 17 LTS:
- Scaricare ed eseguire l'installer per **JDK 17 LTS** al seguente [link](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)

Istruzioni per installare VS Code e preparazione all'esecuzione:
- Scaricare ed eseguire l'installer di **VS Code** al seguente [link](https://code.visualstudio.com/download)
- Andare nella sezione estensioni (Ctrl+Shift+X) e installare le estensioni:
  - **Spring Boot Extension Pack v0.1.0** (Pivotal)
  - **Extension Pack for Java v0.23.0** (Microsoft)

Per compilare ed eseguire il progetto da VS Code:
- Posizionarsi su una delle classi **controller** (SistemaBancarioController, SistemabancarioApplication o AccountController)
- Cliccare il triangolo **play** in alto a destra nella barra orizzontale dei file aperti di VS Code
- Selezionare la classe **SistemabancarioApplication** contenente il main, dal menù a tendina in mezzo alla finestra di VS Code
- L'applicazione sarà in ascolto sulla macchina locale alla porta 8080. Da questo momento si possono effettuare le richieste a tutti gli endpoint parziali della traccia attraverso la **WEB UI** al seguente [link](http://localhost:8080) o con **Postman**

## Architettura PC
L'architettura su cui il progetto è stato sviluppato e testato è:
- Windows 10
- architettura x86-64bit