package Oracles;

import Data.Game;
import Data.Team;
import Data.TournamentTable;
import Utils.Pair;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SportBoxOracle {

    @NotNull
    private List<Game> sportBoxListOfLastGames;
    @NotNull
    private TournamentTable sportBoxTouranamentTable;

    private static final String TABLE_TAG = "tr";
    private static final String GAMES_CLASS = "games";
    private static final String TABLE_CLASS = "global-table show-t";
    private static final String LINK_TO_SPORTBOX = "https://news.sportbox.ru/Vidy_sporta/Futbol/Russia/premier_league";

    {
        this.sportBoxListOfLastGames = new ArrayList<>();
        this.sportBoxTouranamentTable = new TournamentTable();
    }

    private static List<Game> getAllGames(Elements gamesElements) {
        List<Game> games = new ArrayList<>();
        gamesElements.forEach(game -> games.add(parseGameLine(game.text())));
        return games;
    }

    private static Game parseGameLine(String text) {
        String[] tokens = text.split(" ");
        // parse teams
        Team homeTeam = new Team(tokens[0]);
        Team guestTeam = new Team(tokens[2]);
        Pair<Team, Team> teams = new Pair<>(homeTeam, guestTeam);
        // parse goals
        int homeTeamGoals = Character.getNumericValue(tokens[1].charAt(0));
        int guestTeamGoals = Character.getNumericValue(tokens[1].charAt(2));
        Pair<Integer, Integer> goals = new Pair<>(homeTeamGoals, guestTeamGoals);
        // FIXME parse time !!!
        // parse times
        LocalTime startTime = LocalTime.of(19, 0);
        LocalTime endTime = LocalTime.of(21, 0);
        Pair<LocalTime, LocalTime> times = new Pair<>(startTime, endTime);
        return new Game(teams, goals, times);
    }

    private static TournamentTable parseTournamentTable(Elements tableElements) {
        List<Team> teams = new ArrayList<>();
        List<Integer> places = new ArrayList<>();
        List<Integer> points = new ArrayList<>();
        List<Integer> amountOfGames = new ArrayList<>();

        tableElements.stream().skip(1).forEach((element) -> {
            String[] tokens = element.text().split(" ");
            places.add(Integer.parseInt(tokens[0]));
            Team team = new Team(tokens[1]);
            team.addPoints(Integer.parseInt(tokens[5]));
            String[] goals = tokens[4].split("-");
            team.setGoals(Integer.parseInt(goals[0]), Integer.parseInt(goals[1]));
            teams.add(team);
            amountOfGames.add(Integer.parseInt(tokens[2]));
            points.add(Integer.parseInt(tokens[5]));
        });
        return new TournamentTable(teams, places, points, amountOfGames);
    }

    public static void main(String[] args) throws IOException {
        SportBoxOracle oracle = new SportBoxOracle();
        Document document = Jsoup.connect(LINK_TO_SPORTBOX).get();
        Elements gamesElements = document.getElementsByClass(GAMES_CLASS);
        Elements tableElements = document.getElementsByClass(TABLE_CLASS).first().getElementsByTag(TABLE_TAG);
        oracle.sportBoxListOfLastGames = getAllGames(gamesElements);
        oracle.sportBoxTouranamentTable = parseTournamentTable(tableElements);
        System.out.println(oracle.toString());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + sportBoxListOfLastGames.hashCode();
        hash = 19 * hash + sportBoxTouranamentTable.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sportBoxListOfLastGames.forEach(el -> sb.append(el).append("\n**************************\n"));
        sb.append("\n******************************************************************\n");
        sb.append(sportBoxTouranamentTable.toString());
        return sb.toString();
    }

}
