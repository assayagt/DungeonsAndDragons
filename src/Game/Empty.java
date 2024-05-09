package Game;

public class Empty extends Tile{
    public static final char emptyTile = '.';
    public Empty(Position p){
        super(emptyTile);
        this.position = p;
    }

    public void accept(Unit u){
        u.visit(this);
    }
}
