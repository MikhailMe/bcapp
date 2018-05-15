package com.bbs.handlersystem.Parser.Parsers;

public interface ChampionatRuParser extends BaseParser {

    String SPACE = " ";
    String DELIMITER = ":";
    String SPAN_TAG = "span";
    String FUCKING_LETTER = "М";
    String DATE_CLASS = "stat-block__date";
    String TABLE_CLASS = "stat-block-table";
    String TABLE_LINE = "stat-block-table__row";
    String TOUR_CLASS = "stat-block-calendar__item";
    String GAME_CLASS = "stat-block-calendar__event _result";
    String LINK_TO_CHAMPIONAT_RU = "https://www.championat.com/football/_russiapl.html";

}
