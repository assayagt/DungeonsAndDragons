package Game;


import Game.callbacks.*;
import View.InputQuery;

import java.util.List;

public abstract class Player extends Unit {
    public static final char playerTile = '@';

    protected int experience = 0;
    protected int player_Level = 1;

    protected PlayerDeathCallback playerDeathCallback;
    protected InputQuery inputProvider;
    private char key;

    protected Player(String name, int healthCapacity, int attack, int defense)
    {
        super(playerTile, name, healthCapacity, attack, defense);
        this.player_Level = 1;
        this.experience = 0;
    }

    public void setInputQuery(InputQuery inputProvider)
    {
        this.inputProvider = inputProvider;
    }

    public Position performAction(List<Enemy> enemies) {
        key = inputProvider.getInput();
        Position newp = position;
        if (key == 'e')
            castSpecialAbility(enemies);
        else if (key == 'd')
            newp = newp.Translate(1,0);
        else if (key == 'a')
            newp = newp.Translate(-1,0);
        else if (key == 'w')
            newp = newp.Translate(0,-1);
        else if (key == 's')
            newp = newp.Translate(0,1);
        return newp;
    }

    protected void levelUp()
    {
        player_Level++;
        int healGained = gainHealth();
        int attackGained = gainAttack();
        int defenseGained = gainDefense();
        health.addCapacity(healGained);
        health.restore();
        AttackPoints += attackGained;
        DefensePoints += defenseGained;
        messageCallback.send(String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defense", getName(), getLevel(), healGained, attackGained, defenseGained));
    }

    protected void addExperience(int experienceGained)
    {
        this.experience += experienceGained;
        int nextLevelReq = levelUpRequirement();
        while(experience >= nextLevelReq)
        {
            levelUp();
            experience -= nextLevelReq;
            nextLevelReq = levelUpRequirement();
        }
    }

    public abstract void castSpecialAbility(List<Enemy> Enemies);

    public void accept(Unit u)
    {
        u.visit(this);
    }

    @Override
    public void visit(Enemy e) {
        super.battle(e);
        if(!e.alive()){
            swapPosition(e);
            onKill(e);
        }
    }

    public void setMessageCallback(MessageCallback emc)
    {
        this.messageCallback = emc;
    }

    public void setDeathCallback(PlayerDeathCallback pdc)
    {
        this.playerDeathCallback = pdc;
    }

    protected void abilityDamage(Enemy e, int abilityDamage) {
        int damageDone = Math.max(abilityDamage - e.defend(), 0);
        e.getHealth().reduceAmount(damageDone);
        messageCallback.send(String.format("%s hit %s for %d ability damage.", getName(), e.getName(), damageDone));
        if (!e.alive())
            onKill(e);
    }

    protected void onKill(Enemy e)
    {
        int experinceGained = e.getExperience();
        messageCallback.send((String.format("%s died. %s gained %d experience", e.getName(), getName(), experinceGained)));
        addExperience(experinceGained);
        e.onDeath();
    }

    @Override
    public void onDeath(){
        messageCallback.send("You lost");
        playerDeathCallback.call();
    }

    @Override
    public String toString()
    {
        return alive() ? super.toString() : "X";
    }

    private int gainHealth()
    {
        return 10 * player_Level;
    }

    private int gainAttack()
    {
        return 4 * player_Level;
    }

    private int gainDefense() { return player_Level; }

    private int levelUpRequirement()
    {
        return 50 * player_Level;
    }

    public int getLevel()
    {
        return player_Level;
    }

    public int getExperience()
    {
        return experience;
    }

    public void visit(Player p) {}

    public String describe() {
        return String.format("%s\t\tLevel: %d\t\tExperience: %d/%d", super.describe(), getLevel(), getExperience(), levelUpRequirement());
    }

    public Health getHealth(){
        return health;
    }

}
