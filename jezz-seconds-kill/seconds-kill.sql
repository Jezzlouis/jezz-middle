CREATE TABLE `tb_stock` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `count` int(11) NOT NULL COMMENT '库存',
  `sale` int(11) NOT NULL COMMENT '已售',
  `version` int(11) NOT NULL COMMENT '乐观锁，版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
-- 定义成order 会有问题 所以加tb_
CREATE TABLE `tb_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL COMMENT '库存ID',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '商品名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

INSERT INTO `seconds_kill`.`tb_stock`(`id`, `name`, `count`, `sale`, `version`) VALUES (1, '手机', 23, 1, 1);
INSERT INTO `seconds_kill`.`tb_stock`(`id`, `name`, `count`, `sale`, `version`) VALUES (2, '电脑', 15, 2, 1);
INSERT INTO `seconds_kill`.`tb_stock`(`id`, `name`, `count`, `sale`, `version`) VALUES (3, '水杯', 150, 12, 1);
INSERT INTO `seconds_kill`.`tb_stock`(`id`, `name`, `count`, `sale`, `version`) VALUES (4, '被子', 1200, 120, 1);
INSERT INTO `seconds_kill`.`tb_stock`(`id`, `name`, `count`, `sale`, `version`) VALUES (5, '衬衣', 3000, 1530, 1);
INSERT INTO `seconds_kill`.`tb_stock`(`id`, `name`, `count`, `sale`, `version`) VALUES (6, '鞋子', 10, 5, 1);
INSERT INTO `seconds_kill`.`tb_stock`(`id`, `name`, `count`, `sale`, `version`) VALUES (7, '铅笔', 12000, 60, 1);
INSERT INTO `seconds_kill`.`tb_stock`(`id`, `name`, `count`, `sale`, `version`) VALUES (8, '椅子', 15, 4, 1);
INSERT INTO `seconds_kill`.`tb_stock`(`id`, `name`, `count`, `sale`, `version`) VALUES (9, '插座', 28, 3, 1);
INSERT INTO `seconds_kill`.`tb_stock`(`id`, `name`, `count`, `sale`, `version`) VALUES (10, '牙刷', 100, 1, 1);