package com.example.wallet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wallet.dao.StatementMapper;
import com.example.wallet.domain.ResponseResult;
import com.example.wallet.domain.entity.UserWallet;
import com.example.wallet.domain.entity.WalletStatement;
import com.example.wallet.service.StatementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatementServiceImpl extends ServiceImpl<StatementMapper, WalletStatement> implements StatementService {

    @Override
    public ResponseResult<?> queryStatement(Long id) {
        // 查询用户id对应的钱包
        LambdaQueryWrapper<UserWallet> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(UserWallet::getUserId, id);
        UserWallet wallet = queryWrapper1.getEntity();
        // 查询该钱包对应的所有流水记录
        LambdaQueryWrapper<WalletStatement> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(WalletStatement::getUserWalletId, wallet.getId());
        List<WalletStatement> list = list(queryWrapper2);

        return ResponseResult.okResult(list);
    }
}
