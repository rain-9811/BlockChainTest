package huang.pojo;

import java.util.Arrays;

import com.alibaba.fastjson.annotation.JSONField;
import huang.utils.RSAUtils;
import huang.utils.SHA256;

// Transaction类实现

public class Transaction {

    // 交易发送方
    @JSONField(ordinal = 0)
    User from;

    // 交易接收方
    @JSONField(ordinal = 1)
    User to;

    // 交易数额
    @JSONField(ordinal = 2)
    int amount;

    // 数字签名
    byte[] signature;

    // Constructor
    Transaction(User from, User to, int amount) throws Exception {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.sign();
    }

    public String getFrom() {
        return this.from.name;
    }

    public String getTo() {
        return this.to.name;
    }

    public int getAmount() {
        return this.amount;
    }

    public String computeHash() {
        return SHA256.getSHA256(this.from.name + this.to.name + this.amount);
    }

    // 签名  调用自己的私钥进行加密
    public void sign() throws Exception {
      //  this.signature = RSAUtils.privateEncrypt(this.computeHash().getBytes(), this.to.getPrivateKey());
        this.signature = RSAUtils.privateEncrypt(this.computeHash().getBytes(), this.from.getPrivateKey());
    }

    // 验证交易是否合法
    public boolean isValid() throws Exception {
    //       return Arrays.equals(RSAUtils.publicDecrypt(this.signature, this.to.getPublicKey()), this.computeHash().getBytes());
        return Arrays.equals(RSAUtils.publicDecrypt(this.signature, this.from.getPublicKey()), this.computeHash().getBytes());
    }

    @Override
    public String toString() {
        return "\t\tfrom:" + this.from +
                "\t\tto:" + this.to +
                "\t\tamount:" + this.amount + "\n";
    }
}
