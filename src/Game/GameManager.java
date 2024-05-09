package Game;

import java.util.*;

public class GameManager {
    public Board board;
    public Player player;
    public List<Enemy> Enemies;
    public Position playerPosition;
    public Position enemyPosition;
    public Tile tileForPlayer;
    public Tile tileForEnemy;

    public GameManager(Board _board, Player player, List<Enemy> Enemies){
        board = _board;
        this.player = player;
        this.Enemies = Enemies;
        this.playerPosition = player.position;
    }

    public void run(){
        while (player.alive() & !Enemies.isEmpty())
        {
            gametick();
        }
        if (player.alive())
            System.out.println("Level Finished!");
    }

    public void gametick(){
        System.out.println(board.toString());
        playerPosition = player.performAction(Enemies);
        tileForPlayer = board.get(playerPosition);
        player.interact(tileForPlayer);
        for(Enemy e : Enemies){
            enemyPosition = e.performAction(player);
            tileForEnemy = board.get(enemyPosition);
            e.interact(tileForEnemy);
        }
    }

    public void removeEnemy(Enemy enemy){
        board.remove(enemy);
        Enemies.remove(enemy);
    }
}
