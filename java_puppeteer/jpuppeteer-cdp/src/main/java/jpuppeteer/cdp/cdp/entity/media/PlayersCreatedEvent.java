package jpuppeteer.cdp.cdp.entity.media;

/**
* Called whenever a player is created, or when a new agent joins and recieves a list of active players. If an agent is restored, it will recieve the full list of player ids and all events again.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PlayersCreatedEvent {

    /**
    */
    private java.util.List<String> players;



}