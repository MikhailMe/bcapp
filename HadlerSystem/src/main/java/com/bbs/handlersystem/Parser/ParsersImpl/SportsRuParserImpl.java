package com.bbs.handlersystem.Parser.ParsersImpl;

import com.bbs.handlersystem.Data.Game;
import com.bbs.handlersystem.Data.Team;
import com.bbs.handlersystem.Data.TournamentTable;
import com.bbs.handlersystem.Parser.Parsers.SportsRuParser;
import com.bbs.handlersystem.Utils.Pair;
import lombok.NonNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SportsRuParserImpl implements SportsRuParser {

    @NonNull
    private List<Game> sportsRuListOfGames;

    @NonNull
    private TournamentTable sportsRuTouranamentTable;

    {
        sportsRuListOfGames = new ArrayList<>();
        sportsRuTouranamentTable = new TournamentTable();
    }

    public static void main(String[] args) throws IOException {
        SportsRuParserImpl oracle = new SportsRuParserImpl();
        Document gamesDocument = Jsoup.connect(LINK_TO_SPORTS_RU).get();
        Elements gamesElements = gamesDocument.getElementsByClass(GAMES_CLASS);
        Document tableDocument = Jsoup.connect(LINK_TO_SPORTS_RU_TABLE).get();
        Elements tableElements = tableDocument.getElementsByClass(TABLE_CLASS).first().getElementsByTag(TABLE_TAG);
        oracle.parseGamesLines(gamesElements);
        oracle.parseTournamentTable(tableElements);
        System.out.println(oracle.toString());
    }

    @Override
    public void parseGamesLines(@NonNull final Elements gamesElements) {
        List<Game> gamesList = new ArrayList<>();
        Elements games = gamesElements.get(0).getElementsByTag(GAMES_TAG);
        games.forEach(el -> {
            int counter = 0;
            String[] results = el.text().split(SPACE);
            if (results[counter].equals(FINISHED_STATUS)) {
                counter += 3;
                String name;
                if (results[counter].equals(CKA) || results[counter].equals(ARSENAL)) {
                    name = results[counter] + results[++counter];
                } else {
                    name = results[counter];
                }
                Team homeTeam = new Team(name);
                counter++;
                int homeTeamGoals = Integer.parseInt(results[counter++]);
                int guestTeamGoals = Integer.parseInt(results[++counter]);
                //Pair<Integer, Integer> goals = new Pair<>(homeTeamGoals, guestTeamGoals);
                counter++;
                if (results[counter].equals(CKA) || results[counter].equals(ARSENAL)) {
                    name = results[counter] + results[++counter];
                } else {
                    name = results[counter];
                }
                Team guestTeam = new Team(name);
                Pair<Team, Team> teams = new Pair<>(homeTeam, guestTeam);
                // FIXME
                //Pair<LocalTime, LocalTime> times = null;
                gamesList.add(new Game(teams));
            }
        });
        sportsRuListOfGames = gamesList;
    }

    @Override
    public void parseTournamentTable(@NonNull final Elements tableElements) {
        List<Team> listOfTeams = new ArrayList<>();
        List<Integer> listOfPlaces = new ArrayList<>();
        List<Integer> listOfPoints = new ArrayList<>();
        List<Integer> amountOfGames = new ArrayList<>();
        tableElements.stream().skip(1).forEach(element -> {
            String[] tokens = element.text().split(SPACE);
            int counter = 0;
            listOfPlaces.add(Integer.parseInt(tokens[0]));
            String teamName = tokens[1];
            if (teamName.equals(CKA) || teamName.equals(ARSENAL)) {
                counter = 1;
            }
            Team team = new Team(teamName);
            int points = Integer.parseInt(tokens[8 + counter]);
            team.addPoints(points);
            team.setGoals(Integer.parseInt(tokens[6 + counter]), Integer.parseInt(tokens[7 + counter]));
            listOfTeams.add(team);
            listOfPoints.add(points);
            amountOfGames.add(Integer.parseInt(tokens[2 + counter]));
        });
        sportsRuTouranamentTable = new TournamentTable(listOfTeams, listOfPlaces,
                listOfPoints, amountOfGames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sportsRuListOfGames, sportsRuTouranamentTable);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sportsRuListOfGames.forEach(el -> sb.append(el).append("\n**************************\n"));
        sb.append("\n******************************************************************\n");
        sb.append(sportsRuTouranamentTable.toString());
        return sb.toString();
    }

}
