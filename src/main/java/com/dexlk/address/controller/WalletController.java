package com.dexlk.address.controller;

import com.dexlk.address.VO.ValidationResponseTemplateVO;
import com.dexlk.address.model.Wallet;
import com.dexlk.address.repository.AuthRepository;
import com.dexlk.address.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/wallets")
public class WalletController {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private AuthRepository authRepository;

    @PostMapping
    public void saveWallet(@RequestBody Wallet wallet, @RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            if (key.equals("authorization")) {
                String token = value.substring(7);
                ValidationResponseTemplateVO a = authRepository.validate(token);
                wallet.setUserId(a.getValidationResponse().getUserId());
                log.info(String.format("Header '%s' = %s", key, token));
            }
        });

        walletRepository.insertIntoDynamoDB(wallet);
    }

    @GetMapping("/addresses/{userId}")
    public List<String> getWallets(@PathVariable("userId") String userId, @RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            if (key.equals("authorization")) {
                String token = value.substring(7);
                ValidationResponseTemplateVO a = authRepository.validate(token);
                log.info(String.format("Header '%s' = %s", key, token));
            }
        });
        List<String> walletAddresses = walletRepository.getWalletAddresses(userId);
        return walletAddresses;
    }

    @GetMapping("/address/{walletAddress}")
    public ResponseEntity<Wallet> getWalletByWalletAddress(@PathVariable("walletAddress") String walletAddress) {
        Wallet wallet = walletRepository.getWalletByAddress(walletAddress);
                log.info("Inside findWalletById method of WalletController");

        return new ResponseEntity<Wallet>(wallet, HttpStatus.OK);
    }

    @PostMapping("/{walletAddress}")
    public void storeFund(@PathVariable("walletAddress") String walletAddress, @RequestBody Wallet wallet, @RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            if (key.equals("authorization")) {
                String token = value.substring(7);
                ValidationResponseTemplateVO a = authRepository.validate(token);
                log.info(String.format("Header '%s' = %s", key, token));
            }
        });

        walletRepository.storeFund(walletAddress, wallet);
    }

}
