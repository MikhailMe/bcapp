package UtilsTest;

import com.bbs.handlersystem.Data.Game;
import com.bbs.handlersystem.Data.Team;
import com.bbs.handlersystem.Utils.Helper;
import com.bbs.handlersystem.Utils.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class HelperTest {

    @Test
    public void testCreateTeam() {
        Team team1 = Helper.createTeam();
        Team team2 = Helper.createTeam();

        Assert.assertNotNull(team1);
        Assert.assertNotNull(team2);

        Assert.assertNotEquals(team1, team2);
    }

    @Test
    public void testCreatePairTeam() {
        Pair<Team, Team> pair1 = Helper.createPairTeam();
        Pair<Team, Team> pair2 = Helper.createPairTeam();

        Assert.assertNotNull(pair1);
        Assert.assertNotNull(pair2);

        Assert.assertNotEquals(pair1, pair2);
    }

    @Test
    public void testCreateGame() {
        Game game1 = Helper.createGame();
        Game game2 = Helper.createGame();

        Assert.assertNotNull(game1);
        Assert.assertNotNull(game2);

        Assert.assertNotEquals(game1, game2);
    }

    @Test
    public void testCreateListOfGames() {
        final int CAPACITY = 10;

        List<Game> list1 = Helper.createListOfGames(CAPACITY);
        List<Game> list2 = Helper.createListOfGames(CAPACITY);

        Assert.assertNotNull(list1);
        Assert.assertNotNull(list2);

        Assert.assertEquals(list1.size(), CAPACITY);
        Assert.assertEquals(list2.size(), CAPACITY);

        Assert.assertNotEquals(list1, list2);

        list1.forEach(Assert::assertNotNull);
        list2.forEach(Assert::assertNotNull);
    }

}
