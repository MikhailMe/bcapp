package com.bbs.handlersystem.Parser;

import com.bbs.handlersystem.Data.Game;
import com.bbs.handlersystem.Data.Team;
import com.bbs.handlersystem.Data.TournamentTable;
import com.bbs.handlersystem.Utils.Pair;
import lombok.NonNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SportBoxRuOracle implements Parserable {

    @NonNull
    private List<Game> sportBoxListOfLastGames;
    @NonNull
    private TournamentTable sportBoxTouranamentTable;

    private static final int TIME = 4;
    private static final String SPACE = " ";
    private static final String DELIMITER = "-";
    private static final String TABLE_TAG = "tr";
    private static final String GAMES_CLASS = "games";
    private static final String LIVE_TRANSLATION = "LIVE";
    private static final String TABLE_CLASS = "global-table show-t";
    private static final String LINK_TO_SPORTBOX = "https://news.sportbox.ru/Vidy_sporta/Futbol/Russia/premier_league";

    {
        this.sportBoxListOfLastGames = new ArrayList<>();
        this.sportBoxTouranamentTable = new TournamentTable();
    }

    private static Game parseGameLine(String text) {
        String[] tokens = text.split(SPACE);
        if (tokens[0].equals(LIVE_TRANSLATION) || tokens[1].length() == TIME) {
            return null;
        }
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
        LocalTime time = LocalTime.of(19, 0);;
        return new Game(teams, goals, time);
    }

    public static void main(String[] args) throws IOException {
        SportBoxRuOracle oracle = new SportBoxRuOracle();
        Document document = Jsoup.connect(LINK_TO_SPORTBOX).get();
        Elements gamesElements = document.getElementsByClass(GAMES_CLASS);
        Elements tableElements = document.getElementsByClass(TABLE_CLASS).first().getElementsByTag(TABLE_TAG);
        oracle.parseGamesLines(gamesElements);
        oracle.parseTournamentTable(tableElements);
        System.out.println(oracle.toString());
    }

    @Override
    public void parseGamesLines(Elements gamesElements) {
        List<Game> games = new ArrayList<>();
        gamesElements.forEach(game -> games.add(parseGameLine(game.text())));
        games.removeIf(Objects::isNull);
        sportBoxListOfLastGames = games;
    }

    @Override
    public void parseTournamentTable(Elements tableElements) {
        List<Team> listOfTeams = new ArrayList<>();
        List<Integer> listOfPlaces = new ArrayList<>();
        List<Integer> listOfPoints = new ArrayList<>();
        List<Integer> amountOfGames = new ArrayList<>();
        tableElements.stream().skip(1).forEach((element) -> {
            String[] tokens = element.text().split(" ");
            listOfPlaces.add(Integer.parseInt(tokens[0]));
            Team team = new Team(tokens[1]);
            int points = Integer.parseInt(tokens[5]);
            team.addPoints(points);
            String[] goals = tokens[4].split(DELIMITER);
            team.setGoals(Integer.parseInt(goals[0]), Integer.parseInt(goals[1]));
            listOfTeams.add(team);
            amountOfGames.add(Integer.parseInt(tokens[2]));
            listOfPoints.add(points);
        });
        sportBoxTouranamentTable = new TournamentTable(listOfTeams, listOfPlaces,
                listOfPoints, amountOfGames);
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
