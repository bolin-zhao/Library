package cn.bolin.dao;

import cn.bolin.domain.Admin;

/**
 * Create By Bolin on
 */
public interface AdminDao {
    public Admin login(String username,String password);
}
