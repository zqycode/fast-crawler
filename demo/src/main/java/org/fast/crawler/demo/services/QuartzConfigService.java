package org.fast.crawler.demo.services;

import org.fast.crawler.core.auth.AuthProvider;
import org.fast.crawler.core.configuration.CrawlerSiteConfig;
import org.fast.crawler.demo.crawler.proxy.SonyProxyProducer;
import org.fast.crawler.demo.entity.CrawlSiteConfig;
import org.fast.crawler.demo.entity.dao.CrawlKeyWordDao;
import org.fast.crawler.demo.entity.dao.CrawlProxyDao;
import org.fast.crawler.demo.entity.dao.CrawlUserDao;
import org.fast.crawler.core.parser.Parser;
import org.fast.crawler.core.utils.Reflections;
import org.fast.crawler.core.configuration.CrawlerConfig;
import org.fast.crawler.demo.entity.CrawlKeyWord;
import org.fast.crawler.demo.entity.dao.CrawlSiteConfigDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuartzConfigService {

    @Autowired
    CrawlProxyDao crawlProxyDao;

    @Autowired
    CrawlSiteConfigDao crawlSiteConfigDao;

    @Autowired
    CrawlUserDao crawlUserDao;

    @Autowired
    CrawlKeyWordDao crawlKeyWordDao;

    public CrawlerConfig loadConfig() {
        CrawlerConfig webCollectorConfig = new CrawlerConfig();
        List<CrawlerSiteConfig> siteConfigs = new ArrayList<CrawlerSiteConfig>();

        List<CrawlSiteConfig> sites = crawlSiteConfigDao.queryAll();
        List<CrawlKeyWord> keyWords = crawlKeyWordDao.queryAll();

        if(sites != null && sites.size() > 0) {
            for(CrawlSiteConfig config : sites) {
                if(config.getUrl().contains("{keyword}")) {
                    for(CrawlKeyWord keyword : keyWords) {
                        CrawlerSiteConfig siteConfig = new CrawlerSiteConfig();
                        String url = tranToUrl(config.getUrl(), keyword.getValue());
                        
                        siteConfig.setName(config.getName());
                        siteConfig.setCronExp(config.getCronExp());
                        siteConfig.setUrl(url);
                        siteConfig.setNeedDepth(true);
                        siteConfig.setUseProxy(config.isUseProxy());
                        siteConfig.setParser((Parser) Reflections.newInstance(config.getParser()));
                        siteConfig.setAuthProvider((AuthProvider) Reflections.newInstance(config.getAuthProvider()));
                        
//                        siteConfig.setProxyProducer(new RandomProxyProducer());
                        siteConfig.setProxyProducer(new SonyProxyProducer());

                        siteConfigs.add(siteConfig);
                    }
                } else {
                    CrawlerSiteConfig siteConfig = new CrawlerSiteConfig();
                    
//                    siteConfig.setProxyProducer(new RandomProxyProducer());
                    siteConfig.setProxyProducer(new SonyProxyProducer());

                    siteConfig.setName(config.getName());
                    siteConfig.setCronExp(config.getCronExp());
                    siteConfig.setUrl(config.getUrl());
                    siteConfig.setNeedDepth(true);
                    siteConfig.setUseProxy(config.isUseProxy());
                    siteConfig.setParser((Parser) Reflections.newInstance(config.getParser()));
                    siteConfig.setAuthProvider((AuthProvider) Reflections.newInstance(config.getAuthProvider()));
                    siteConfigs.add(siteConfig);
                }
            }
        }
        webCollectorConfig.setSites(siteConfigs);
        return webCollectorConfig;
    }

    private String tranToUrl(String url, String keyword) {
        return url.replaceAll("\\{keyword\\}", keyword);
    }

}
