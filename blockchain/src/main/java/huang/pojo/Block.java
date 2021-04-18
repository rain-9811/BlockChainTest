package huang.pojo;

import java.sql.Timestamp;
import java.util.List;

import com.alibaba.fastjson.JSON;
import huang.utils.SHA256;

// Block类实现

public class Block {

    // 交易记录
    List<Transaction> transactions;
    // 上一个区块的hash
    String previousHash;
    // 自己的hash
    String hash;
    // 初始字符串
    String data;
    // 初始nonce
    int nonce = 0;
    // block生成的时间
    Timestamp time;

    // Constructor
    Block(List<Transaction> transactions, String previousHash, String data) {
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.data = data;
    }

    // 计算区块的hash
    // transactions need stringify
    String computeHash() {
        return SHA256.getSHA256(JSON.toJSONString(this.transactions) +
                this.previousHash + this.data + this.nonce);
    }

    // 得到由difficulty个0组成的字符串
    String getAnswer(int difficulty) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < difficulty; i++) {
            ans.append("0");
        }
        return ans.toString();
    }

    // 计算符合区块链设置要求的hash
    void mine(int difficulty) {
        String ans = getAnswer(difficulty);
        this.hash = this.computeHash();
        while (!this.hash.substring(0, difficulty).equals(ans)) {
            this.nonce++;
            this.hash = this.computeHash();
        }
    }

    // 打印区块信息
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("\tTransactions:\n");
        if (transactions != null) {
            for (Transaction t : this.transactions) {
                ret.append(t.toString());
            }
        }
        ret.append("\tpreviousHash:").append(this.previousHash).append("\n");
        ret.append("\thash:").append(this.hash).append("\n");
        ret.append("\tdata:").append(this.data).append("\n");
        ret.append("\tnonce:").append(this.nonce).append("\n");
        ret.append("\ttime:").append(this.time).append("\n");
        return ret.toString();
    }
}
