package com.bbs.handlersystem.Parser.Parsers;

import org.jsoup.select.Elements;

public interface BaseParser {

    void parseGamesLines(Elements gamesElements);

    void parseTournamentTable(Elements tableElements);
}
