package Game;

public class Health {
    protected int healthPool;
    protected int healthAmount;
    public Health(int HealthPool, int HealthAmount){
        healthPool = HealthPool;
        healthAmount = HealthAmount;
    }
    public void reduceAmount(int dmgDone){
        healthAmount = healthAmount-dmgDone;
    }
    public int getPool() { return healthPool; }
    public int getAmount(){
        return healthAmount;
    }
    protected void addCapacity(int healthToAdd) { healthPool += healthToAdd; }
    public void restore() { healthAmount = healthPool; }
}
