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

public class SportsRuOracle implements Parserable {

    @NotNull
    private List<Game> sportsRuListOfGames;
    @NotNull
    private TournamentTable sportsRuTouranamentTable;

    private static final String SPACE = " ";
    private static final String CKA = "СКА";
    private static final String GAMES_TAG = "li";
    private static final String TABLE_TAG = "tr";
    private static final String ARSENAL = "Арсенал";
    private static final String FINISHED_STATUS = "завершен";
    private static final String GAMES_CLASS = "accordion__body";
    private static final String TABLE_CLASS = "stat-table table sortable-table";
    private static final String LINK_TO_SPORTS_RU = "https://www.sports.ru/rfpl/";
    private static final String LINK_TO_SPORTS_RU_TABLE = "https://www.sports.ru/rfpl/table/";

    {
        sportsRuListOfGames = new ArrayList<>();
        sportsRuTouranamentTable = new TournamentTable();
    }

    public static void main(String[] args) throws IOException {
        SportsRuOracle oracle = new SportsRuOracle();
        Document gamesDocument = Jsoup.connect(LINK_TO_SPORTS_RU).get();
        Elements gamesElements = gamesDocument.getElementsByClass(GAMES_CLASS);
        Document tableDocument = Jsoup.connect(LINK_TO_SPORTS_RU_TABLE).get();
        Elements tableElements = tableDocument.getElementsByClass(TABLE_CLASS).first().getElementsByTag(TABLE_TAG);
        oracle.parseGamesLines(gamesElements);
        oracle.parseTournamentTable(tableElements);
        System.out.println(oracle.toString());
    }

    @Override
    public void parseGamesLines(Elements gamesElements) {
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
                Pair<Integer, Integer> goals = new Pair<>(homeTeamGoals, guestTeamGoals);
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
                gamesList.add(new Game(teams, goals));
            }
        });
        sportsRuListOfGames = gamesList;
    }

    @Override
    public void parseTournamentTable(Elements tableElements) {
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
                counter = 1 ;
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
        int hash = 3;
        hash = 19 * hash + sportsRuListOfGames.hashCode();
        hash = 19 * hash + sportsRuTouranamentTable.hashCode();
        return hash;
    }

    @NotNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sportsRuListOfGames.forEach(el -> sb.append(el).append("\n**************************\n"));
        sb.append("\n******************************************************************\n");
        sb.append(sportsRuTouranamentTable.toString());
        return sb.toString();
    }

}
