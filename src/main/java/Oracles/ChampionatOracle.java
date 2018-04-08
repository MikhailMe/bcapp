package Oracles;

import Data.Game;
import Data.Team;
import Data.Tour;
import Data.TournamentTable;
import Utils.Pair;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ChampionatOracle {

    private int currentTourNumber;
    @NotNull
    private List<Game> championatRuListOfGames;
    @NotNull
    private TournamentTable championatRuTouranamentTable;

    private static final String FUCK_LETTER = "М";
    private static final String DATE_CLASS = "stat-block__date";
    private static final String TABLE_CLASS = "stat-block-table";
    private static final String TABLE_LINE = "stat-block-table__row";
    private static final String TOUR_CLASS = "stat-block-calendar__item";
    private static final String GAME_CLASS = "stat-block-calendar__event _result";
    private static final String LINK_TO_CHAMPIONAT_RU = "https://www.championat.com/football/_russiapl.html";

    {
        this.currentTourNumber = 25;
        this.championatRuListOfGames = new ArrayList<>();
        this.championatRuTouranamentTable = new TournamentTable();
    }

    private static LocalDate getTourDate(String[] dateParams) {
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

    private static Tour getTour(Element element) {
        Elements dateOfTour = element.getElementsByClass(DATE_CLASS);
        String[] dateParams = dateOfTour.text().split(" ");
        int tourNumber = Integer.parseInt(dateParams[3]);
        LocalDate tourDate = getTourDate(dateParams);
        return new Tour(tourNumber, tourDate);
    }

    private static List<Game> parseGamesLines(Elements gamesElements) {
        List<Game> games = new ArrayList<>();
        gamesElements.forEach(element -> {
            String[] results = element.getElementsByTag("span").text().split(" ");
            String[] time = results[0].split(":");
            // parse time
            LocalTime startTime = LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
            LocalTime endTime = LocalTime.of(startTime.getHour() + 2, startTime.getMinute());
            Pair<LocalTime, LocalTime> times = new Pair<>(startTime, endTime);
            // parse teams
            int counter = 1;
            Team homeTeam = new Team(results[counter]);
            if (results[counter + 1].equals(FUCK_LETTER)) counter += 3;
            else counter += 2;
            Team guestTeam = new Team(results[counter]);
            Pair<Team, Team> teams = new Pair<>(homeTeam, guestTeam);
            counter++;
            // parse score
            if (results[counter].equals(FUCK_LETTER)) counter += 1;
            int homeTeamGoals = Character.getNumericValue(results[counter].charAt(0));
            counter++;
            int guestTeamGoals = Character.getNumericValue(results[counter].charAt(0));
            Pair<Integer, Integer> goals = new Pair<>(homeTeamGoals, guestTeamGoals);
            // build result
            games.add(new Game(teams, goals, times));
        });
        return games;
    }

    private static TournamentTable parseTournamentTable(Elements tableElements) {
        List<Team> teams = new ArrayList<>();
        List<Integer> places = new ArrayList<>();
        List<Integer> points = new ArrayList<>();
        List<Integer> amountOfGames = new ArrayList<>();
        Elements lines = tableElements.get(0).getElementsByClass(TABLE_LINE);
        lines.forEach(line -> {
            String[] results = line.text().split(" ");
            int counter = 0;
            places.add(Integer.parseInt(results[counter]));
            counter++;
            teams.add(new Team(results[counter]));
            counter++;
            if (results[counter].equals(FUCK_LETTER)) {
                counter++;
            }
            amountOfGames.add(Integer.parseInt(results[counter]));
            counter++;
            points.add(Integer.parseInt(results[counter]));

        });
        return new TournamentTable(teams, places, points, amountOfGames);
    }

    public static void main(String[] args) throws IOException {
        ChampionatOracle oracle = new ChampionatOracle();
        Document document = Jsoup.connect(LINK_TO_CHAMPIONAT_RU).get();
        Elements toursElements = document.getElementsByClass(TOUR_CLASS);
        for (Element element : toursElements) {
            Tour tour = getTour(element);
            if (tour.getTourNumber() == oracle.currentTourNumber) {
                Elements gameElemnets = element.getElementsByClass(GAME_CLASS);
                oracle.championatRuListOfGames.addAll(parseGamesLines(gameElemnets));
            }
        }
        Elements tableElements = document.getElementsByClass(TABLE_CLASS);
        oracle.championatRuTouranamentTable = parseTournamentTable(tableElements);
        System.out.println(oracle.toString());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + championatRuListOfGames.hashCode();
        hash = 19 * hash + championatRuTouranamentTable.hashCode();
        return hash;
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
