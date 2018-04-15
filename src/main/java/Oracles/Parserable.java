package Oracles;

import org.jsoup.select.Elements;

public interface Parserable {

    void parseGamesLines(Elements gamesElements);
    void parseTournamentTable(Elements tableElements);
}
