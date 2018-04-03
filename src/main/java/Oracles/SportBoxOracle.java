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
        sportBoxListOfLastGames = new ArrayList<>();
        sportBoxTouranamentTable = new TournamentTable();
    }

    private static List<Game> getAllGames(Elements gamesElements) {
        List<Game> games = new ArrayList<>();
        gamesElements.forEach(game -> games.add(parseGameLine(game.text())));
        return games;
    }

    private static Game parseGameLine(String text) {
        String[] tokens = text.split(" ");
        Team homeTeam = new Team(tokens[0]);
        Team guestTeam = new Team(tokens[2]);
        Pair<Team, Team> teams = new Pair<>(homeTeam, guestTeam);
        int homeTeamGoals = Character.getNumericValue(tokens[1].charAt(0));
        int guestTeamGoals = Character.getNumericValue(tokens[1].charAt(2));
        Pair<Integer, Integer> goals = new Pair<>(homeTeamGoals, guestTeamGoals);
        return new Game(teams, goals);
    }

    private static TournamentTable parseTournamentTable(Elements tableElements) {
        List<Team> teams = new ArrayList<>();
        List<Integer> places = new ArrayList<>();
        List<Integer> wins = new ArrayList<>();
        List<Integer> draws = new ArrayList<>();
        List<Integer> looses = new ArrayList<>();
        List<Integer> points = new ArrayList<>();
        List<Integer> amountOfGames = new ArrayList<>();

        tableElements.stream().skip(1).forEach((element) -> {
            String text = element.text();
            String[] tokens = text.split(" ");
            places.add(Integer.parseInt(tokens[0]));
            Team team = new Team(tokens[1]);
            team.addPoints(Integer.parseInt(tokens[5]));
            String[] goals = tokens[4].split("-");
            team.setGoals(Integer.parseInt(goals[0]), Integer.parseInt(goals[1]));
            teams.add(team);
            amountOfGames.add(Integer.parseInt(tokens[2]));
            String[] winsDrawsLooses = tokens[3].split("/");
            wins.add(Integer.parseInt(winsDrawsLooses[0]));
            draws.add(Integer.parseInt(winsDrawsLooses[1]));
            looses.add(Integer.parseInt(winsDrawsLooses[2]));
            points.add(Integer.parseInt(tokens[5]));
        });
        return new TournamentTable(teams, places, wins, draws, looses, points, amountOfGames);
    }

    public static void main(String[] args) throws IOException {
        SportBoxOracle oracle = new SportBoxOracle();
        Document document = Jsoup.connect(LINK_TO_SPORTBOX).get();
        Elements gamesElements = document.getElementsByClass(GAMES_CLASS);
        Elements tableElements = document.getElementsByClass(TABLE_CLASS).first().getElementsByTag(TABLE_TAG);

        oracle.sportBoxListOfLastGames = getAllGames(gamesElements);
        oracle.sportBoxTouranamentTable = parseTournamentTable(tableElements);

        oracle.sportBoxListOfLastGames.forEach(System.out::println);
        System.out.println("\n******************************************************************\n");
        System.out.println(oracle.sportBoxTouranamentTable.toString());

    }
}
