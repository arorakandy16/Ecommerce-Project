package com.project.ecommerce.caching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    @Value("${property.resolving.cache.resolver}")
    private String PROPERTY_RESOLVING_CACHE_RESOLVER_BEAN_NAME;

    public String getPROPERTY_RESOLVING_CACHE_RESOLVER_BEAN_NAME() {
        return PROPERTY_RESOLVING_CACHE_RESOLVER_BEAN_NAME;
    }

    public void setPROPERTY_RESOLVING_CACHE_RESOLVER_BEAN_NAME(String PROPERTY_RESOLVING_CACHE_RESOLVER_BEAN_NAME) {
        this.PROPERTY_RESOLVING_CACHE_RESOLVER_BEAN_NAME = PROPERTY_RESOLVING_CACHE_RESOLVER_BEAN_NAME;
    }



//    public static final String PROPERTY_RESOLVING_CACHE_RESOLVER_BEAN_NAME = "propertyResolvingCacheResolver";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private Environment springEnv;

    @Bean(PROPERTY_RESOLVING_CACHE_RESOLVER_BEAN_NAME)
    @Override
    public CacheResolver cacheResolver() {
        return new PropertyResolvingCacheResolver(cacheManager, springEnv);
    }
}