package com.example.wallet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wallet.domain.ResponseResult;
import com.example.wallet.domain.entity.WalletStatement;

public interface StatementService extends IService<WalletStatement> {
    ResponseResult<?> queryStatement(Long id);
}
