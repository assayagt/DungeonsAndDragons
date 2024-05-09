package Game;
import Game.callbacks.MessageCallback;

import java.util.List;
import java.util.Random;

public abstract class Unit extends Tile {
	protected String Name;
    protected Health health;
    protected int AttackPoints;
    protected int DefensePoints;
    protected MessageCallback messageCallback;
    protected static Random r = new Random(123);


    protected Unit(char tile, String name,int healthCapacity, int attack, int defense) {
        super(tile);
        Name = name;
        health = new Health(healthCapacity,healthCapacity);
        AttackPoints = attack;
        DefensePoints = defense;
    }

    protected void initialize(Position position, MessageCallback messageCallback){

    }

    protected int attack(){
        int result = r.nextInt(AttackPoints);
        messageCallback.send(String.format("%s rolled %d attack points,", getName(), result));
        return result;
    }

    public int defend(){
        int result = r.nextInt(DefensePoints);
        messageCallback.send(String.format("%s rolled %d defense points,", getName(), result));
        return result;
    }
	
	// What happens when the unit dies
    public abstract void onDeath();

	// This unit attempts to interact with another tile.
    public void interact(Tile tile){
        tile.accept(this);
    }

    public void visit(Empty e){
        swapPosition(e);
    }

    public void visit(Wall w){
    }

    public abstract void visit(Player p);
    public abstract void visit(Enemy e);

	// Combat against another unit.
    protected void battle(Unit u){
        messageCallback.send(String.format("%s engaged in combat with %s.\n%s\n%s", getName(), u.getName(), describe(), u.describe()));
        int damageDone = Math.max(attack()-u.defend(),0);
        u.health.reduceAmount(damageDone);
        messageCallback.send(String.format("%s dealt %d damage to %s.", getName(), damageDone, u.getName()));
    }

    protected String getName(){
        return Name;
    }

    protected Health getHealth(){
        return health;
    }
    protected int getHealthPool(){
        return health.healthPool;
    }
    protected int getHealthAmount(){
        return health.healthAmount;
    }
    protected int getAttack(){
        return AttackPoints;
    }
    protected int getDefense(){
        return DefensePoints;
    }

    public String describe() {
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", getName(), getHealthAmount(), getAttack(), getDefense());
    }
    public boolean alive(){
        return getHealth().getAmount() > 0;
    }

    protected void swapPosition(Tile tile){
            Position p = tile.getPosition();
            tile.setPosition(this.getPosition());
            this.setPosition(p);
    }
}
