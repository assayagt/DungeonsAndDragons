package Game;

import View.InputQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Warrior extends Player {
    public static final String specialAbility = "Avenger's Shield";
    private int AbilityCooldown;
    private int RemainingCooldown;
    Random randomEnemy;


    public Warrior(String name, int healthCapacity, int attack, int defense, int AbilityCooldown) {
        super(name, healthCapacity, attack, defense);
        this.AbilityCooldown = AbilityCooldown;
        this.RemainingCooldown = 0;
    }

    @Override
    public Position performAction(List<Enemy> enemies) {
        onGameTick();
        return super.performAction(enemies);
    }

    public void levelUp()
    {
        super.levelUp();
        int warriorHealthGained = warriorGainHealth();
        int warriorAttackGained = warriortGainAttack();
        int warriorDefenseGained = warriorGainDefense();
        health.addCapacity(warriorHealthGained);
        health.restore();
        AttackPoints += warriorAttackGained;
        DefensePoints += warriorDefenseGained;
    }

    @Override
    public void castSpecialAbility(List<Enemy> Enemies) {
        if (RemainingCooldown > 0)
            messageCallback.send(String.format("%s tried to cast %s, but there is a cooldown: %d", getName(), specialAbility, RemainingCooldown));
        else{
            List<Enemy> PossibleEnemy = new ArrayList<>();
            RemainingCooldown = AbilityCooldown;
            health.healthAmount = Math.min(health.getAmount() + (10 * DefensePoints), health.getPool());
            for (Enemy e : Enemies) {
                if (this.position.Range(e.position) < 3)
                    PossibleEnemy.add(e);
            }
            if (!PossibleEnemy.isEmpty()) {
                randomEnemy = new Random();
                int random = (int) (Math.random() * PossibleEnemy.size());
                abilityDamage(PossibleEnemy.get(random), warriorTenpHealthPool());
            }
        }
    }

    public int warriorTenpHealthPool() {
        return ((int) (health.getPool() * 0.1));
    }

    public void onGameTick() {
        if (RemainingCooldown > 0)
            RemainingCooldown--;
        else
            RemainingCooldown = AbilityCooldown;
    }

    public String describe() {
        return String.format("%s\t\tCooldown: %s", super.describe(), AbilityCooldown);
    }

    private int warriorGainHealth()
    {
        return 5 * player_Level;
    }

    private int warriortGainAttack()
    {
        return 2 * player_Level;
    }

    private int warriorGainDefense() { return player_Level; }
}
