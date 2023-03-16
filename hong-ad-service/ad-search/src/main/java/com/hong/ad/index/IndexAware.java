package com.hong.ad.index;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  9:42 PM 2020/1/29
 */
public interface IndexAware<K,V> {

    V  get(K key);

    void add(K key,V value);

    void update(K key,V value);

    void delete(K key,V value);

}
