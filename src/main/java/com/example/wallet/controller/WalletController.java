package com.example.wallet.controller;

import com.example.wallet.domain.ResponseResult;
import com.example.wallet.service.StatementService;
import com.example.wallet.service.WalletService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Resource
    private WalletService walletService;
    @Resource
    private StatementService statementService;

    // 1.查询用户钱包余额
    @GetMapping("/{id}")
    public ResponseResult<?> getBalance(@PathVariable Long id) {
        return walletService.getBalanceById(id);
    }

    // 2.用户消费接口
    @PostMapping("/pay/{id}")
    public ResponseResult<?> pay(@PathVariable Long id, BigDecimal price) {
        // 消费100元写成消费接口，下同
        return walletService.pay(id, price);
    }

    // 3.用户退款接口
    @PostMapping("/refund/{id}")
    public ResponseResult<?> refund(@PathVariable Long id, BigDecimal price) {
        return walletService.refund(id, price);
    }

    // 4.查询用户钱包金额变动明细的接口
    @GetMapping("/statement/{id}")
    public ResponseResult<?> queryStatement(@PathVariable Long id) {
        return statementService.queryStatement(id);
    }
}
