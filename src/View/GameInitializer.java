package View;


import Game.*;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class GameInitializer {
    public GameManager manager;
    public Player player;
    public List<Enemy> Enemies;
    public TileFactory tileFactory = new TileFactory();
    public int indexRecieved;
    Scanner scanner;

    public GameInitializer(String path) throws IOException {
        Enemies = new ArrayList<>();
        final File [] levels = finder(path);
        scanner = new Scanner(System.in);
        for (int i=0; i < levels.length; i++){
            if(i == 0){
                System.out.println("Select player:");
                printPlayersList();
                indexRecieved = scanner.nextInt();
                Tile [][] _boardTiles = TileArrayBuilder(charArrayMaker(levels[i]), indexRecieved);
                System.out.println("You have selected:");
                System.out.println(player.describe());
                Board _board = new Board(_boardTiles);
                manager = new GameManager(_board, player, Enemies);
                manager.run();
            }
            else{
                Tile [][] _boardTiles = TileArrayBuilder(charArrayMaker(levels[i]), indexRecieved);
                Board _board = new Board(_boardTiles);
                manager = new GameManager(_board, player, Enemies);
                manager.run();
            }
        }
        System.out.println("Game Over.");
    }

    public void printPlayersList(){
        for (int i=1;i<=tileFactory.listPlayers().size(); i++){
            System.out.println(i + ". " + tileFactory.listPlayers().get(i-1).describe());
        }
    }

    public static File[] finder(String dirName) {
        File folder = new File(dirName);
        File[] listOfFiles = folder.listFiles();
        return listOfFiles;
    }

    public char[][] charArrayMaker(File file) throws IOException {
            BufferedReader fileInput = new BufferedReader(new FileReader(file));
            String s;
            int numofRowsCounter = 0;
            while ((s = fileInput.readLine()) != null) {
                numofRowsCounter++;
            }
            char [][] array = new char[numofRowsCounter][];
            BufferedReader fileInput1 = new BufferedReader(new FileReader(file));
            String s1;
            int rowCounter = 0;
            while ((s1 = fileInput1.readLine()) != null) {
                array[rowCounter] = s1.toCharArray();
                rowCounter++;
            }
        return array;
    }

    public Tile[][] TileArrayBuilder(char[][] chars, int indexRecieved) {
        Tile[][] tiles = new Tile[chars.length][chars[0].length];
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[0].length; j++) {
                Position p = new Position(j, i);
                if (chars[i][j] == '#') {
                    tiles[i][j] = tileFactory.produceWall(p);
                } else if (chars[i][j] == '.') {
                    tiles[i][j] = tileFactory.produceEmpty(p);
                } else if (chars[i][j] == '@') {
                    if (player == null) {
                        player = tileFactory.producePlayer(indexRecieved, p);
                        player.setDeathCallback(() -> player.toString());
                        player.setMessageCallback((msg) -> System.out.println(msg));
                        player.setInputQuery(() -> scanner.next().charAt(0));
                    }
                    else{
                        player.setPosition(p);
                    }
                    tiles[i][j] = player;
                }
                else {
                    Enemy enemy = tileFactory.produceEnemy(chars[i][j], p);
                    Enemies.add(enemy);
                    tiles[i][j] = enemy;
                    enemy.setDeathCallback(()->manager.removeEnemy(enemy));
                    enemy.setMessageCallback((msg)-> System.out.println(msg));
                }
            }
        }
        return tiles;
    }
}
