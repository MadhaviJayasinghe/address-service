package com.dexlk.address.service;

import com.dexlk.address.model.Wallet;
import com.dexlk.address.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@Slf4j
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @PostMapping
    public String saveWallet(@RequestBody Wallet wallet) {
        walletRepository.insertIntoDynamoDB(wallet);
        return "Successfully inserted into DynamoDB table";
    }

//    public Wallet saveWallet(Wallet wallet) {
//        log.info("Inside saveWallet method of WalletService");
//        return  walletRepository.save(wallet);
//    }
//
//    @GetMapping("/{id}")
//    public Wallet findWalletById(String walletId) {
//        log.info("Inside saveWallet of WalletService");
//        return walletRepository.findWalletById(walletId);
//    }
//
//    @GetMapping("/address/{walletAddress}")
//    public Wallet findWalletByAddress(String walletAddress) {
//        log.info("Inside findWalletById method of WalletController");
//        return walletRepository.findWalletByWalletAddress(walletAddress);
//    }
//
//    @GetMapping("/")
//    public List<Wallet> getWallets() {
//        log.info("Inside saveWallet of WalletService");
//        return walletRepository.findAll();
//    }
//
//    @PatchMapping("/{id}")
//    public Wallet storeFund(Wallet fund) {
//        log.info("Inside storeFund method of WalletService");
//        return  walletRepository.save(fund);
//    }
}

