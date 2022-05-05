package com.dexlk.address.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.dexlk.address.model.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class WalletRepository {
    @Autowired
    private DynamoDBMapper mapper;
    public void insertIntoDynamoDB(Wallet wallet) {
        mapper.save(wallet);
    }

//    public Wallet getWallet(String walletId) {
//        return mapper.load(Wallet.class, walletId);
//    }

    public List<String> getWalletAddresses(String userId) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(userId));

        DynamoDBScanExpression queryExpression = new DynamoDBScanExpression ()
                .withFilterExpression("userId = :val1").withExpressionAttributeValues(eav);

        List<Wallet> wallets = mapper.scan(Wallet.class, queryExpression);

        if (!wallets.isEmpty()) {
            List<String> addresses = new ArrayList<String>();
            int i = 0;
            for (Wallet w : wallets) {
                addresses.add(i, w.getWalletAddress());
                i++;
            }

            return addresses;
        }

        return null;
    }

    public Wallet getWalletByAddress(String walletAddress) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(walletAddress));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression ()
                .withFilterExpression("walletAddress = :val1").withExpressionAttributeValues(eav);

        List<Wallet> wallets = mapper.scan(Wallet.class, scanExpression);

        return wallets.get(0);
    }

    public void storeFund (String walletAddress, Wallet walletObj) {
        Wallet wallet = getWalletByAddress(walletAddress);
        if (walletObj.getId() != null) {
            wallet.setId(walletObj.getId());
        }
        if (walletObj.getWalletAddress() != null) {
            wallet.setWalletAddress(walletObj.getWalletAddress());
        }
        if (walletObj.getUsdBalance() != null) {
            wallet.setUsdBalance(walletObj.getUsdBalance());
        }

        if (walletObj.getBitcoinBalance() != null) {
            wallet.setBitcoinBalance(walletObj.getBitcoinBalance());
        }
        mapper.save(wallet);
    }
}
