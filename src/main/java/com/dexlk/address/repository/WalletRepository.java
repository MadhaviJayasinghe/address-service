package com.dexlk.address.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.dexlk.address.VO.Fund;
import com.dexlk.address.VO.ResponseTemplateVO;
import com.dexlk.address.controller.WalletController;
import com.dexlk.address.model.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class WalletRepository {
    @Autowired
    private DynamoDBMapper mapper;
    @Autowired
    private RestTemplate restTemplate;

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

    public ResponseTemplateVO getFund(String walletAddress, String convertFrom, String covertTo, Number amount) {
        log.info("Inside getWalletBalance of ExchangeWalletService");
        ResponseTemplateVO vo = new ResponseTemplateVO();

        Fund fund =
                restTemplate.getForObject("http://localhost:9002/exchangeWallet/" + walletAddress + "/" + convertFrom + "/" + covertTo + "/" + amount, Fund.class);

        vo.setFund(fund);

        return vo;
    }

    public void storeFund (String walletAddress, String convertFrom, String covertTo, Number amount) {
        Wallet wallet = getWalletByAddress(walletAddress);
        ResponseTemplateVO fundObj = getFund(walletAddress, convertFrom, covertTo, amount);

        Fund fund = fundObj.getFund();
        if (fund.getFundAmount() != null) {
            Number usdValue = wallet.getUsdBalance().intValue() - amount.intValue();
            Number bitcoinVale = wallet.getBitcoinBalance().intValue() + fund.getFundAmount().intValue();
            wallet.setUsdBalance(usdValue);
            wallet.setBitcoinBalance(bitcoinVale);
        }
        mapper.save(wallet);
    }

}
