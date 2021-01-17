package cn.bolin.dao;

import cn.bolin.domain.Reader;

/**
 * Create By Bolin on
 */
public interface ReaderDao {
    Reader login(String username, String password);
}
