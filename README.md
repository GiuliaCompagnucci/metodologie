# 🏰 RPG Dungeon Crawler - Matricola 123420

Un'applicazione JavaFX che implementa un Gioco di Ruolo (GDR) a turni in stile "Dungeon Crawler". Il giocatore configura la propria avventura scegliendo classe ed eroe, affronta nemici generati proceduralmente in base alla difficoltà, gestisce un inventario dinamico e cerca di sconfiggere il Boss finale. Il progetto è progettato con architettura MVC e principi SOLID per garantire estendibilità e facilità di integrazione di nuove funzionalità.

## 🚀 Come eseguire il progetto

### Prerequisiti
*   Java 21 (LTS)
*   Gradle (incluso nel wrapper del progetto)

### Istruzioni

1.  Clona il repository:
    ```bash
    git clone <url-del-repository> </percorso/desiderato/nome-cartella>
    ```

2.  Spostati nella directory del codice sorgente (dove risiede il file `build.gradle`):
    ```bash
    cd <nome-cartella>/code
    ```

3.  Build del progetto:
    ```bash
    ./gradlew build
    ```

4.  Esecuzione:
    ```bash
    ./gradlew run
    ```

## 🤖 Uso di strumenti di AI

Nel corso dello sviluppo del progetto sono stati utilizzati strumenti di AI (in particolare Qwen) come supporto alla programmazione e al design architetturale. L'AI è stata utilizzata per:

*   **Struttura del codice:** Suggerimenti sull'organizzazione dei package e sull'applicazione dei principi SOLID.
*   **Generazione di codice ripetitivo e metodi getter/setter**
*   **Debug e Risoluzione Errori:** Identificazione e correzione di eccezioni runtime e problemi di layout JavaFX.
*   **Implementazione di funzionalità complesse:** Supporto nella creazione di `TypeAdapter` custom per Gson per gestire la persistenza di classi astratte e interfacce.
*   **Documentazione:** Generazione di bozze per Javadoc e struttura della Wiki.

**Intervento Personale:**
Tutto il codice generato dall'AI è stato attentamente revisionato, compreso nei suoi meccanismi logici e adattato manualmente alle specifiche del progetto. La logica di business principale (gestione del combattimento a turni, generazione procedurale del dungeon basata sulla difficoltà, flusso di gioco e navigazione tra le view) è stata sviluppata e integrata attivamente dallo studente, garantendo la piena comprensione dell'intera codebase.

📌 **Per una descrizione più dettagliata dell’uso dell’AI e delle scelte progettuali, consultare la [Wiki della repository](https://github.com/GiuliaCompagnucci/Metodologie---RPG/wiki).**