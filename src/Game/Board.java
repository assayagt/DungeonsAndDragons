package Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private List<Tile> tiles;
    private int rows;
    private int cloumns;

    public Board(Tile[][] board){
		tiles = new ArrayList<>();
        rows = board.length;
        cloumns = board[0].length;
        for(Tile[] line : board){
            tiles.addAll(Arrays.asList(line));
        }
    }

    public Tile get(Position p) {
		for(Tile t : tiles){
			if (t.getPosition().compareTo(p) == 0){
				return t;
			}
		}
		throw new RuntimeException("No such tile in this board");
    }

    public void remove(Enemy e) {
        tiles.remove(e);
        Position p = e.getPosition();
        tiles.add(new Empty(p));
    }

    @Override
    public String toString() {
        tiles = tiles.stream().sorted().collect(Collectors.toList());
        String boardAsString = "";
        int rowCounter = 1;
        for(int m=0; m < tiles.size(); m++){
            Tile tile = tiles.get(m);
            if (rowCounter == cloumns) {
                boardAsString = boardAsString + tile.toString() + "\n";
                rowCounter = 1;
            }
            else {
                boardAsString = boardAsString + tile.toString();
                rowCounter++;
            }
        }
        return boardAsString;
    }
}