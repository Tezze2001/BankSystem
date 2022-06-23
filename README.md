# Progetto Sistemi Distribuiti
Il progetto è stato svolto da Telemaco Terzi matr. 865981.
## Architettura
Il sistema è stato implementato nel seguente modo:
- **frontend**: in HTML, CSS (Bootstrap) e Js vanilla.
- **backend**: in Java con il framework Spring e i dati vengono salvati su file di testo binario serializzando la classe BankDataController.

Sul backend sono stati implementati model e controller. Spring viene sfruttato anche come WebServer e non solo come Application server infatti ha endpoint frontend che ritornano html gestito dal suo codice js. Js si occupa di effettuare chiamate al server in modo da ricevere i dati in formato Json preoccupandosi di generare l'interfaccia con i dati ricevuti.
Ogni richiesta che modifica la base di dati viene poi salvata permanentemente su file: src\main\resources\DB.data.
## Aggiunte
Alla traccia base ho aggiunto nuove pagine di frontend per:
- Creazione di un account
- Chiusura di un account
- Prelievo e deposito di soldi

Queste pagine sono accessibili direttamente dalla navbar della **WEB UI** per completezza ecco gli endpoint:
- /html/newAccount.html => creazione di un account
- /html/delAccount.html => chiusura di un account
- /html/depositWithdraw.html => prelievo/deposito di soldi