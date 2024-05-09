package Game;

public class Position implements Comparable<Position>{
    int x;
    int y;
    public Position(int _x, int _y){
        x = _x;
        y = _y;
    }
    public double Range(Position q){
        return Math.sqrt(Math.pow((x-q.x),2)+Math.pow((y-q.y),2));
    }

    @Override
    public int compareTo(Position p) {
        if(p.y < this.y)
            return 1;
        else if (p.y > y)
            return -1;
        else {
            if (p.x < this.x)
                return 1;
            else if (p.x > this.x)
                return -1;
        }
        return 0;
    }


    public Position Translate(int _x,int _y){
        return new Position(x+_x, y+_y);
    }

}
