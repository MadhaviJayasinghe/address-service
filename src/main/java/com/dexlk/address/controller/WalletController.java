package com.dexlk.address.controller;

import com.dexlk.address.model.Wallet;
import com.dexlk.address.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/wallets")
public class WalletController {
    @Autowired
    private WalletRepository walletRepository;

    @PostMapping
    public void saveWallet(@RequestBody Wallet wallet) {
        walletRepository.insertIntoDynamoDB(wallet);
    }

    @GetMapping("/addresses/{userId}")
    public List<String> getWallets(@PathVariable("userId") String userId) {
        List<String> walletAddresses = walletRepository.getWalletAddresses(userId);
//        log.info("Inside saveWallet of WalletService {}", walletResponseEntity);
        return walletAddresses;
    }

    @GetMapping("/address/{walletAddress}")
    public ResponseEntity<Wallet> getWalletByWalletAddress(@PathVariable("walletAddress") String walletAddress) {
        Wallet wallet = walletRepository.getWalletByAddress(walletAddress);
                log.info("Inside findWalletById method of WalletController");

        return new ResponseEntity<Wallet>(wallet, HttpStatus.OK);
    }

    @PatchMapping("/{walletAddress}/{convertFrom}/{convertTo}/{amount}")
    public void storeFund(@PathVariable("walletAddress") String walletAddress, @PathVariable("convertFrom") String convertFrom, @PathVariable("convertTo") String convertTo, @PathVariable("amount") Number amount) {
        walletRepository.storeFund(walletAddress, convertFrom, convertTo, amount);
    }

}
