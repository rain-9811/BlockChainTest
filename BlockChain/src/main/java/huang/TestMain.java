package huang;
import huang.pojo.*;


public class TestMain {
    public static void main(String[] args) throws Exception {
        // register
        Chain chain = new Chain();
        User u1 = new User("a");
        User u2 = new User("b");
        User u3 = new User("c");
        // transactions
        chain.mineTransaction(u1, "miner1");     //u1 挖矿
        chain.mineTransaction(u2, "miner2");
        chain.transaction2Pool(u1, u2, 5);    //u1 向 u2 转账 5 个币
        chain.mineTransaction(u3, "miner3");
        System.out.println(chain);

    }
}