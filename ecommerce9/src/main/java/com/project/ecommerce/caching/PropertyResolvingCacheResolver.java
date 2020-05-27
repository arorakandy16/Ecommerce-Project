package com.project.ecommerce.caching;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.core.env.PropertyResolver;

public class PropertyResolvingCacheResolver
        extends SimpleCacheResolver {

    private final PropertyResolver propertyResolver;

    protected PropertyResolvingCacheResolver(CacheManager cacheManager, PropertyResolver propertyResolver) {
        super(cacheManager);
        this.propertyResolver = propertyResolver;
    }

    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        Collection<String> unresolvedCacheNames = super.getCacheNames(context);
        return unresolvedCacheNames.stream().map(unresolvedCacheName ->
                propertyResolver.resolveRequiredPlaceholders(unresolvedCacheName)).collect(Collectors.toList());
    }
}