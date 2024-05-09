package Game;

import Game.callbacks.*;

public abstract class Enemy extends Unit{
    private int ExperienceValue;
    protected EnemyDeathCallback enemyDeathCallback;

    public Enemy(char tile, String name, int healthCapacity, int attack, int defense, int EXP){
        super(tile, name, healthCapacity,attack,defense);
        ExperienceValue = EXP;
    }
    public abstract Position performAction(Player player);

    public void setDeathCallback(EnemyDeathCallback edc)
    {
        this.enemyDeathCallback = edc;
    }

    public void setMessageCallback(MessageCallback emc)
    {
        this.messageCallback = emc;
    }

    public Position getPosition(){
        return position;
    }

    public void accept(Unit u){
        u.visit(this);
    }

    public void visit(Player p){
        super.battle(p);
        if(!p.alive()){
            p.onDeath();
        }
    }
    public void onDeath(){
        enemyDeathCallback.call();
    }

    public void visit(Enemy e) {
    }

    public int getExperience() { return ExperienceValue; }

    public Health getHealth(){
        return health;
    }

}
