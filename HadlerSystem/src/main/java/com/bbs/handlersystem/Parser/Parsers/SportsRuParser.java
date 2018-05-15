package com.bbs.handlersystem.Parser.Parsers;

public interface SportsRuParser extends BaseParser {

    String SPACE = " ";
    String CKA = "СКА";
    String GAMES_TAG = "li";
    String TABLE_TAG = "tr";
    String ARSENAL = "Арсенал";
    String FINISHED_STATUS = "завершен";
    String GAMES_CLASS = "accordion__body";
    String TABLE_CLASS = "stat-table table sortable-table";
    String LINK_TO_SPORTS_RU = "https://www.sports.ru/rfpl/";
    String LINK_TO_SPORTS_RU_TABLE = "https://www.sports.ru/rfpl/table/";
}
