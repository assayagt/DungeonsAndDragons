package Game;

public class Monster extends Enemy {
    Integer VisionRange;

    public Monster(char tile, String name, int healthCapacity, int attack, int defense, Integer EXP, Integer vision_Range){
        super(tile, name, healthCapacity, attack, defense, EXP);
        VisionRange = vision_Range;
    }
    public Position performAction(Player player){
        Position newPosition = position;
        if(getPosition().Range(player.getPosition()) < VisionRange){
            int dx = this.position.x-player.getPosition().x;
            int dy = this.position.y-player.getPosition().y;
            if(Math.abs(dx)>Math.abs(dy)){
                if(dx>0)
                    newPosition = position.Translate(-1,0);
                else
                    newPosition = position.Translate(1,0);
            }
            else{
                if(dy>0)
                    newPosition = position.Translate(0,-1);
                else
                    newPosition = position.Translate(0,1);
            }
        }
        else{
            int random = (int)(Math.random()*100);
            if(random>=0 & random<20){
                newPosition = position.Translate(-1,0);
            }
            else if(random>=20 & random<40){
                newPosition = position.Translate(1,0);
            }
            else if(random>=40 & random<60){
                newPosition = position.Translate(0,1);
            }
            else if(random>=60 & random<80){
                newPosition = position.Translate(0,-1);
            }
        }
        return newPosition;
    }

    @Override
    public void visit(Enemy e) {

    }
}
