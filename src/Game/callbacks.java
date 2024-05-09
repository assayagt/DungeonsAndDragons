package Game;

public class callbacks {
    public interface MessageCallback {
        public  void send(String msg);
    }

    public interface PlayerDeathCallback {
        void call();
    }

    public interface EnemyDeathCallback {
        void call();
    }
}
