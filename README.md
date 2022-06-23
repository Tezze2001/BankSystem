# Progetto Sistemi Distribuiti
Il progetto è stato svolto da Telemaco Terzi matr. 865981.
## Architettura
Il sistema è stato implementato nel seguente modo:
- **frontend**: in HTML, CSS (Bootstrap) e vanilla Js.
- **backend**: in Java con il framework Spring e i dati vengono salvati su file di testo binario serializzando la classe BankDataController.

Sul backend sono stati implementati model e controller, si sfrutta Spring anche come WebServer e non solo come Application server infatti ha endpoint frontend che ritornano html gestito dal suo codice js. Js si occupa di effettuare chiamate al server in modo da ricevere i dati in formato Json preoccupandosi di generare l'interfaccia con i dati ricevuti.
Ogni richiesta che modifica la base di dati viene poi salvata permanentemente su file.
## Aggiunte
Alla traccia base ho aggiunto nuove pagine di frontend per:
- Creazione di un account
- Chiusura di un account
- Prelievo e deposito di soldi
