package com.example.wallet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wallet.domain.ResponseResult;
import com.example.wallet.domain.entity.UserWallet;

import java.math.BigDecimal;

public interface WalletService extends IService<UserWallet> {
    ResponseResult<?> getBalanceById(Long id);

    ResponseResult<?> pay(Long id, BigDecimal price);

    ResponseResult<?> refund(Long id, BigDecimal price);
}
