package com.example.wallet.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWallet {
    private Long id;
    private Long userId;
    private BigDecimal balance;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    private Integer delFlag;
}
