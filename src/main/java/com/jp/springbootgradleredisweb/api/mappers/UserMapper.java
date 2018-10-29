package com.jp.springbootgradleredisweb.api.mappers;

import com.jp.springbootgradleredisweb.api.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    @Select("SELECT * FROM users")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age")
    })
    List<User> getAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age")
    })
    User get(String id);

    @Select("SELECT * FROM users WHERE name = #{name}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age")
    })
    User getByName(String name);

    @Insert("INSERT INTO users(id, name, password, age) values(#{id}, #{name}, #{password}, #{age})")
    void insert(User user);

    @Update("UPDATE users SET name=#{name},age=#{age},password=#{password} WHERE id=#{id}")
    void update(User user);

    @Delete("DELETE FROM users WHERE id=#{id}")
    void delete(Long id);
}
