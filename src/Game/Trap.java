package Game;

public class Trap extends Enemy {
    public int VisibilityTime;
    public int InvisibilityTime;
    public int TicksCount;
    public boolean Visible;

    public Trap(char tile, String name, int healthCapacity, int attack, int defense, int EXP, int VT, int IT){
        super(tile, name, healthCapacity, attack, defense, EXP);
        VisibilityTime = VT;
        InvisibilityTime = IT;
        TicksCount = 0;
        Visible = true;
    }

    public Position performAction(Player player){
        Visible = TicksCount < VisibilityTime;
        if(TicksCount == (VisibilityTime+InvisibilityTime))
            TicksCount = 0;
        else
            TicksCount = TicksCount + 1;
        if(this.getPosition().Range(player.getPosition()) < 2)
            attack();
        return position;
    }

    public String toString(){
        if (this.Visible)
            return super.toString();
        else
            return ".";
    }
}
