package Game;

import Game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mage extends Player {
    protected int Mana_Pool;
    protected int Current_Mana;
    protected int Mana_Cost;
    protected int Spell_Power;
    protected int Hits_Count;
    protected int Ability_Range;
    private Random randomEnemy;

    public Mage (String name, int healthCapacity, int attack, int defense, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(name, healthCapacity, attack, defense);
        this.Mana_Pool = manaPool;
        this.Current_Mana = manaPool / 4;
        this.Mana_Cost = manaCost;
        this.Spell_Power = spellPower;
        this.Hits_Count = hitsCount;
        this.Ability_Range = abilityRange;
    }

    @Override
    public Position performAction(List<Enemy> enemies) {
        onGameTick();
        return super.performAction(enemies);
    }

    @Override
    public void levelUp()
    {
        super.levelUp();
        Mana_Pool += mageGainManaPool();
        Current_Mana = Math.min(Current_Mana + Mana_Pool / 4, Mana_Pool);
        Spell_Power += mageGainSpellPower();
        messageCallback.send(String.format("+%d Maximum mana, +%d Spell Power", Mana_Pool, Spell_Power));
    }

    @Override
    public void castSpecialAbility(List<Enemy> Enemies) {
        if (Current_Mana >= Mana_Cost) {
            Current_Mana = Current_Mana - Mana_Cost;
            int hits = 0;
            List<Enemy> PossibleEnemy = new ArrayList<>();
            for (Enemy e : Enemies) {
                if (this.position.Range(e.position) < 3)
                    PossibleEnemy.add(e);
            }
            if (!PossibleEnemy.isEmpty()) {
                randomEnemy = new Random();
                int random = (int) (Math.random() * PossibleEnemy.size());
                Enemy enemyToHit = PossibleEnemy.get(random);
                if (hits < Hits_Count & enemyToHit.alive()){
                    abilityDamage(enemyToHit, Spell_Power);
                    hits++;
                }
            }
        }
    }

    public void onGameTick() {
        Current_Mana = Math.min(Mana_Pool, Current_Mana + player_Level);
    }

    private int mageGainManaPool()
    {
        return 25 * player_Level;
    }

    private int mageGainSpellPower()
    {
        return 10 * player_Level;
    }

    public String describe() {
        return String.format("%s\t\tMana: %s", super.describe(), Mana_Pool);
    }


}
