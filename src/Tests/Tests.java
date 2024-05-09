
package Tests;

import View.*;
import Game.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Tests {

    public TileFactory tileFactory;
    public Player warriorTested;
    public Player mageTested;
    public Player rogueTested;
    public Enemy monsterTested;
    public Enemy trapTested;
    public Enemy farMonsterTested;
    public Empty emptyTested;
    public Wall wallTested;
    public Position playersPosition;
    public Position enemiesPosition;
    public Position simpleTilesPosition;
    List<Enemy> Enemies;

    @BeforeEach
    public void TestInitializer() {
        tileFactory = new TileFactory();
        playersPosition = new Position(0,0);
        enemiesPosition = new Position(1,0);
        simpleTilesPosition = new Position(0,1);
        warriorTested = tileFactory.producePlayer(1,playersPosition);
        mageTested = tileFactory.producePlayer(3,playersPosition);
        rogueTested = tileFactory.producePlayer(5,playersPosition);
        monsterTested = tileFactory.produceEnemy('k',enemiesPosition);
        farMonsterTested = tileFactory.produceEnemy('k',new Position(10,10));
        emptyTested = tileFactory.produceEmpty(simpleTilesPosition);
        wallTested = tileFactory.produceWall(simpleTilesPosition);
        Enemies = new ArrayList<>();
        Enemies.add(monsterTested);
        Enemies.add(farMonsterTested);

        warriorTested.setMessageCallback(((string)-> System.out.println(string)));
        mageTested.setMessageCallback(((string)-> System.out.println(string)));
        rogueTested.setMessageCallback(((string)-> System.out.println(string)));
        monsterTested.setMessageCallback((string)-> System.out.println(string));
        farMonsterTested.setMessageCallback((string)-> System.out.println(string));
    }

    @Test
    public void playerMovementTests() {
        warriorTested.interact(emptyTested);
        Assert.assertTrue("warrior position should be empty position", warriorTested.getPosition().equals(simpleTilesPosition));
        warriorTested.interact(emptyTested);
        Assert.assertTrue("warrior position should be 0,0 again", warriorTested.getPosition().equals(playersPosition));
        warriorTested.interact(wallTested);
        Assert.assertTrue("warrior position shouldn't change", warriorTested.getPosition().equals(playersPosition));
    }

    @Test
    public void playerAttackTests() {
        warriorTested.castSpecialAbility(Enemies);
        Assert.assertTrue("warrior should damage random enemy in range", monsterTested.getHealth().getAmount() <= monsterTested.getHealth().getPool());
        monsterTested.getHealth().restore();
        mageTested.castSpecialAbility(Enemies);
        Assert.assertTrue("mage should damage random enemy in range", monsterTested.getHealth().getAmount() <= monsterTested.getHealth().getPool());
        monsterTested.getHealth().restore();
        rogueTested.castSpecialAbility(Enemies);
        Assert.assertTrue("rogue should damage all enemies in range", monsterTested.getHealth().getAmount() <= monsterTested.getHealth().getPool());
        monsterTested.getHealth().restore();
        warriorTested.castSpecialAbility(Enemies);
        Assert.assertTrue("warrior shouldn't damage random enemy in range", farMonsterTested.getHealth().getAmount() == farMonsterTested.getHealth().getPool());
        monsterTested.getHealth().restore();
        mageTested.castSpecialAbility(Enemies);
        Assert.assertTrue("mage shouldn't damage random enemy in range", farMonsterTested.getHealth().getAmount() == farMonsterTested.getHealth().getPool());
        monsterTested.getHealth().restore();
        rogueTested.castSpecialAbility(Enemies);
        Assert.assertTrue("rogue shouldn't damage random enemy in range", farMonsterTested.getHealth().getAmount() == farMonsterTested.getHealth().getPool());
        monsterTested.getHealth().restore();
    }

    @Test
    public void enemyMovementTests() {
        monsterTested.interact(emptyTested);
        Assert.assertTrue("monster position should be empty position", monsterTested.getPosition().equals(simpleTilesPosition));
        monsterTested.interact(emptyTested);
        Assert.assertTrue("monster position should be 0,0 again", monsterTested.getPosition().equals(enemiesPosition));
        monsterTested.interact(wallTested);
        Assert.assertTrue("warrior position shouldn't change", monsterTested.getPosition().equals(enemiesPosition));
    }

    @Test
    public void enemyAttackTests() {
        monsterTested.interact(mageTested);
        Assert.assertTrue("monster should hit mage", mageTested.getHealth().getAmount() <= mageTested.getHealth().getPool());
        mageTested.getHealth().restore();
        monsterTested.interact(warriorTested);
        Assert.assertTrue("monster should hit warrior", warriorTested.getHealth().getAmount() <= warriorTested.getHealth().getPool());
        warriorTested.getHealth().restore();
        monsterTested.interact(rogueTested);
        Assert.assertTrue("monster should hit rogue", rogueTested.getHealth().getAmount() <= rogueTested.getHealth().getPool());
        rogueTested.getHealth().restore();
    }
}
