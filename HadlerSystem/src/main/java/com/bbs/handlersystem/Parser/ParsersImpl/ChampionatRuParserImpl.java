package com.bbs.handlersystem.Parser.ParsersImpl;

import com.bbs.handlersystem.Data.Game;
import com.bbs.handlersystem.Data.Team;
import com.bbs.handlersystem.Data.Tour;
import com.bbs.handlersystem.Data.TournamentTable;
import com.bbs.handlersystem.Parser.Parsers.ChampionatRuParser;
import com.bbs.handlersystem.Utils.Pair;
import lombok.NonNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ChampionatRuParserImpl implements ChampionatRuParser {

    private int currentTourNumber;
    @NonNull
    private List<Game> championatRuListOfGames;
    @NonNull
    private TournamentTable championatRuTouranamentTable;

    {
        this.currentTourNumber = 26;
        this.championatRuListOfGames = new ArrayList<>();
        this.championatRuTouranamentTable = new TournamentTable();
    }

    private static LocalDate getTourDate(@NonNull final String[] dateParams) {
        int day = Integer.parseInt(dateParams[0]);
        int year = Integer.parseInt(dateParams[2].substring(0, dateParams[2].length() - 1));
        int month = 0;
        switch (dateParams[1]) {
            case "января":
                month = 1;
                break;
            case "февраля":
                month = 2;
                break;
            case "марта":
                month = 3;
                break;
            case "апреля":
                month = 4;
                break;
            case "мая":
                month = 5;
                break;
            case "июня":
                month = 6;
                break;
            case "июля":
                month = 7;
                break;
            case "августа":
                month = 8;
                break;
            case "сентября":
                month = 9;
                break;
            case "октября":
                month = 10;
                break;
            case "ноября":
                month = 11;
                break;
            case "декабря":
                month = 12;
                break;
        }
        return LocalDate.of(year, month, day);

    }

    private static Tour getTour(@NonNull final Element element) {
        Elements dateOfTour = element.getElementsByClass(DATE_CLASS);
        String[] dateParams = dateOfTour.text().split(SPACE);
        int tourNumber = Integer.parseInt(dateParams[3]);
        LocalDate tourDate = getTourDate(dateParams);
        return new Tour(tourNumber, tourDate);
    }

    public static void main(String[] args) throws IOException {
        ChampionatRuParserImpl oracle = new ChampionatRuParserImpl();
        Document document = Jsoup.connect(LINK_TO_CHAMPIONAT_RU).get();
        Elements toursElements = document.getElementsByClass(TOUR_CLASS);
        for (Element element : toursElements) {
            Tour tour = getTour(element);
            if (tour.getTourNumber() == oracle.currentTourNumber) {
                Elements gameElemnets = element.getElementsByClass(GAME_CLASS);
                oracle.parseGamesLines(gameElemnets);
            }
        }
        Elements tableElements = document.getElementsByClass(TABLE_CLASS);
        oracle.parseTournamentTable(tableElements);
        System.out.println(oracle.toString());
    }

    @Override
    public void parseGamesLines(@NonNull final Elements gamesElements) {
        List<Game> games = new ArrayList<>();
        gamesElements.forEach(element -> {
            String[] results = element.getElementsByTag(SPAN_TAG).text().split(SPACE);
            // parse time
            String[] times = results[0].split(DELIMITER);
            LocalTime time = LocalTime.of(Integer.parseInt(times[0]), Integer.parseInt(times[1]));
            // parse teams
            int counter = 1;
            Team homeTeam = new Team(results[counter]);
            if (results[counter + 1].equals(FUCKING_LETTER)) counter += 3;
            else counter += 2;
            Team guestTeam = new Team(results[counter]);
            Pair<Team, Team> teams = new Pair<>(homeTeam, guestTeam);
            counter++;
            // parse score
            if (results[counter].equals(FUCKING_LETTER)) counter += 1;
            int homeTeamGoals = Character.getNumericValue(results[counter].charAt(0));
            counter++;
            int guestTeamGoals = Character.getNumericValue(results[counter].charAt(0));
            Pair<Integer, Integer> goals = new Pair<>(homeTeamGoals, guestTeamGoals);
            // build result
            games.add(new Game(teams, goals, time));
        });
        championatRuListOfGames.addAll(games);
    }

    @Override
    public void parseTournamentTable(@NonNull final Elements tableElements) {
        List<Team> teams = new ArrayList<>();
        List<Integer> places = new ArrayList<>();
        List<Integer> points = new ArrayList<>();
        List<Integer> amountOfGames = new ArrayList<>();
        Elements lines = tableElements.get(0).getElementsByClass(TABLE_LINE);
        lines.forEach(line -> {
            String[] results = line.text().split(SPACE);
            int counter = 0;
            places.add(Integer.parseInt(results[counter]));
            counter++;
            teams.add(new Team(results[counter]));
            counter++;
            if (results[counter].equals(FUCKING_LETTER)) {
                counter++;
            }
            amountOfGames.add(Integer.parseInt(results[counter]));
            counter++;
            points.add(Integer.parseInt(results[counter]));

        });
        championatRuTouranamentTable = new TournamentTable(teams, places, points, amountOfGames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), currentTourNumber, championatRuListOfGames, championatRuTouranamentTable);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        championatRuListOfGames.forEach(el -> sb.append(el).append("\n**************************\n"));
        sb.append("\n******************************************************************\n");
        sb.append(championatRuTouranamentTable.toString());
        return sb.toString();
    }

}
