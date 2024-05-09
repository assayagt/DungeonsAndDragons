package Game;

public class Wall extends Tile {
    public static final char wallTile = '#';
    public Wall(Position p){
        super(wallTile);
        this.position = p;
    }

    public void accept(Unit u){
        u.visit(this);
    }
}
