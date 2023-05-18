package com.example.wallet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wallet.domain.entity.WalletStatement;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatementMapper extends BaseMapper<WalletStatement> {
}
