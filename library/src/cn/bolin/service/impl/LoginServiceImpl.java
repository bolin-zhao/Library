package cn.bolin.service.impl;

import cn.bolin.dao.AdminDao;
import cn.bolin.dao.ReaderDao;

import cn.bolin.dao.impl.AdminDaoImpl;
import cn.bolin.dao.impl.ReaderDaoImpl;
import cn.bolin.domain.Admin;
import cn.bolin.domain.Reader;
import cn.bolin.service.LoginService;


/**
 * Create By Bolin on
 */
public class LoginServiceImpl implements LoginService {
    ReaderDao readerDao = new ReaderDaoImpl();
    AdminDao adminDao = new AdminDaoImpl();
    @Override
    public Object login(String username, String password,String type) {
        Object object = null;
        switch (type){
            case "reader":
                object = readerDao.login(username , password);
                break;
            case "admin":
                object = adminDao.login(username, password);
                break;
        }
        return object;

    }
}
