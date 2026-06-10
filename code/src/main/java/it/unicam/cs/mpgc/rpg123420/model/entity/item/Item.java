package it.unicam.cs.mpgc.rpg123420.model.entity.item;

import it.unicam.cs.mpgc.rpg123420.model.entity.character.Player;

/**
 * Interfaccia che definisce il contratto per tutti gli oggetti collezionabili e utilizzabili dal giocatore.
 * Implementata da pozioni, equipaggiamenti o consumabili, permette di gestire polimorficamente
 * l'inventario e gli effetti degli oggetti sull'eroe.
 */
public interface Item {

    /**
     * Restituisce il nome identificativo dell'oggetto.
     * @return Il nome dell'item (es. "Pozione di Cura").
     */
    String getName();

    /**
     * Restituisce una breve descrizione dell'effetto o delle caratteristiche dell'oggetto.
     * Utile per l'interfaccia utente per informare il giocatore.
     * @return La stringa descrittiva dell'item.
     */
    String getDescription();

    /**
     * Applica l'effetto specifico dell'oggetto sul giocatore fornito.
     * Questo metodo incapsula la logica di funzionamento dell'item (es. cura, potenziamento).
     * @param player L'istanza del giocatore su cui applicare l'effetto.
     */
    void use(Player player);
}
