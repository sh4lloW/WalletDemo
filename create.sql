-- 钱包表
DROP TABLE IF EXISTS `user_wallet`;
CREATE TABLE `user_wallet` (
	`id` BIGINT PRIMARY KEY COMMENT '钱包id',
	`user_id` BIGINT NOT NULL COMMENT '用户id',
	`balance` DECIMAL(9, 2) NOT NULL COMMENT '余额',
	`update_time` DATETIME NOT NULL COMMENT '余额更新时间',
	`del_flag` INT NOT NULL COMMENT '删除标志，0为未删除，1为已删除'
);

-- 钱包流水表
DROP TABLE IF EXISTS `wallet_statement`;
CREATE TABLE `wallet_statement` (
	`id` BIGINT PRIMARY KEY COMMENT '流水单id',
	`user_wallet_id` BIGINT NOT NULL COMMENT '钱包id',
	`price_change` DECIMAL(9, 2) NOT NULL COMMENT '变动金额',
	`type` INT NOT NULL COMMENT '流水类型，0为出账，1为入账',
	`update_time` DATETIME NOT NULL COMMENT '余额更新时间',
	`del_flag` INT NOT NULL COMMENT '删除标志，0为未删除，1为已删除'
);