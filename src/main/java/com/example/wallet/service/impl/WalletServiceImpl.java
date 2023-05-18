package com.example.wallet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wallet.constants.SystemConstants;
import com.example.wallet.dao.WalletMapper;
import com.example.wallet.domain.ResponseResult;
import com.example.wallet.domain.entity.UserWallet;
import com.example.wallet.domain.entity.WalletStatement;
import com.example.wallet.domain.vo.UserWalletVo;
import com.example.wallet.enums.HttpCodeEnum;
import com.example.wallet.service.StatementService;
import com.example.wallet.service.WalletService;
import com.example.wallet.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, UserWallet> implements WalletService {

    @Resource
    StatementService statementService;

    @Override
    public ResponseResult<?> getBalanceById(Long id) {
        // 根据用户id查询
        LambdaQueryWrapper<UserWallet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserWallet::getUserId, id);
        UserWallet wallet = queryWrapper.getEntity();
        // 封装Vo
        UserWalletVo userWalletVo = BeanCopyUtils.copyBean(wallet, UserWalletVo.class);
        return ResponseResult.okResult(userWalletVo);
    }

    @Override
    public ResponseResult<?> pay(Long id, BigDecimal price) {
        // 获取当前钱包
        LambdaQueryWrapper<UserWallet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserWallet::getUserId, id);
        UserWallet wallet = queryWrapper.getEntity();
        // 如果钱包余额小于消费金额
        if (wallet.getBalance().compareTo(price) < 0) {
            return ResponseResult.errorResult(HttpCodeEnum.PRICE_ERROR);
        }else {
            // 更新钱包表
            BigDecimal balance = wallet.getBalance();
            balance.subtract(price);
            LambdaUpdateWrapper<UserWallet> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(UserWallet::getUserId, id);
            updateWrapper.set(UserWallet::getBalance, balance);
            update(updateWrapper);
            // 添加流水记录
            WalletStatement statement = new WalletStatement();
            statement.setType(SystemConstants.STATEMENT_TYPE_OUTCOME);
            statement.setPriceChange(price);
            statement.setUserWalletId(wallet.getId());
            statementService.save(statement);
            return ResponseResult.okResult();
        }
    }

    @Override
    public ResponseResult<?> refund(Long id, BigDecimal price) {
        // 获取当前钱包
        LambdaQueryWrapper<UserWallet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserWallet::getUserId, id);
        UserWallet wallet = queryWrapper.getEntity();
        // 更新钱包表
        BigDecimal balance = wallet.getBalance();
        balance.add(price);
        LambdaUpdateWrapper<UserWallet> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserWallet::getUserId, id);
        updateWrapper.set(UserWallet::getBalance, balance);
        update(updateWrapper);
        // 添加流水记录
        WalletStatement statement = new WalletStatement();
        statement.setType(SystemConstants.STATEMENT_TYPE_INCOME);
        statement.setPriceChange(price);
        statement.setUserWalletId(wallet.getId());
        statementService.save(statement);
        return ResponseResult.okResult();
    }



}
