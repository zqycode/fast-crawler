INSERT INTO `crawldb`.`crawl_user` (`id`, `password`, `username` , `site`, `enable`) VALUES (1,'rui@1234','cr__king@126.com', 'weibo', true);

INSERT INTO `crawldb`.`crawl_proxy`(`id`, `port`, `socksProxy`, `url`, `password`, `username`) VALUES (1, '8080', false, 'proxy.cnbj.sonyericsson.net', 'sss@1234', 'xp017734');

INSERT INTO `crawldb`.`crawl_site_config`(`id`, `authProvider`, `cronExp`, `parser`, `proxyProducer`, `name`, `url`, `useProxy`, `userTag`) VALUES (1, '', '0 0/10 * * * ?', 'SinaParser', 'SonyProxyProducer', 'weibo', 'http://s.weibo.com/weibo/{keyword}', true, 'weibo');
INSERT INTO `crawldb`.`crawl_site_config`(`id`, `authProvider`, `cronExp`, `parser`, `proxyProducer`, `name`, `url`, `useProxy`, `userTag`) VALUES (2, '', '0 0/1 * * * ?', 'TouTiaoParser', 'SonyProxyProducer', 'toutiao', 'http://toutiao.com/search_content/?offset=0&format=json&keyword={keyword}&autoload=true&count=20', true, '');

INSERT INTO `crawldb`.`crawl_keywords` (`id`, `value`) VALUES(1, 'iphone');
INSERT INTO `crawldb`.`crawl_keywords` (`id`, `value`) VALUES(2, 'sony');
INSERT INTO `crawldb`.`crawl_keywords` (`id`, `value`) VALUES(3, '苹果');
INSERT INTO `crawldb`.`crawl_keywords` (`id`, `value`) VALUES(4, '索尼');
INSERT INTO `crawldb`.`crawl_keywords` (`id`, `value`) VALUES(5, '三星');