package cn.bolin.dao;

import cn.bolin.domain.Book;

import java.util.List;

/**
 * Create By Bolin on 12.25
 */
public interface BookDao {
    List<Book> findAll(int index,int limit);

    int getCount();
}
