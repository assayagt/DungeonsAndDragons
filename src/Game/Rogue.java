package Game;

import Game.Player;

import java.util.List;

public class Rogue extends Player {
    int Cost;
    int Current_Energy;

    public Rogue(String name, int healthCapacity, int attack, int defense, int cost) {
        super(name, healthCapacity, attack, defense);
        this.Cost = cost;
        Current_Energy = 100;
    }

    @Override
    public Position performAction(List<Enemy> enemies) {
        onGameTick();
        return super.performAction(enemies);
    }

    public void levelUp() {
        super.levelUp();
        Current_Energy = 100;
        int rogueAttackGained = rogueGainAttack();
        AttackPoints += rogueAttackGained;

    }

    @Override
    public void castSpecialAbility(List<Enemy> Enemies) {
        Current_Energy = Current_Energy - Cost;
        for (int i=0; i<Enemies.size();i++) {
            if (Enemies.get(i).alive() & Enemies.get(i).position.Range(this.position) < 2) {
                abilityDamage(Enemies.get(i), AttackPoints);
            }
        }
    }

    public String describe() {
        return String.format("%s\t\tMana: %s", super.describe(), Current_Energy);
    }

    public void onGameTick() {
        Current_Energy = Math.min(Current_Energy + 10, 100);
    }

    private int rogueGainAttack()
    {
        return 3 * player_Level;
    }
}
