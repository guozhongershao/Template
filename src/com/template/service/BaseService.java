package com.template.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

public abstract class BaseService<T> {

    @Autowired
    private Mapper<T> mapper;

    /**
     * 根据主键查询对象
     * 
     * @param id
     * @return
     */
    public T queryById(Object id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询某张表所有数据
     * 
     * @return
     */
    public List<T> queryAll() {
        return this.mapper.select(null);
    }

    /**
     * 根据条件查询一条数据
     * 
     * @param record 要查询的类对象，对象中封装需要查询的条件
     * @return
     */
    public T queryOne(T record) {
        return this.mapper.selectOne(record);
    }

    /**
     * 根据条件查询数据列表
     * 
     * @param record
     * @return
     */
    public List<T> queryListByWhere(T record) throws Exception{
        return this.mapper.select(record);
    }

    /**
     * 分页查询
     * 
     * @param page
     * @param rows
     * @param record
     * @return
     */
    public PageInfo<T> queryPageListByWhere(Integer page, Integer rows, T record) {
        // 设置分页参数
        PageHelper.startPage(page, rows);
        List<T> list = this.mapper.select(record);
        return new PageInfo<T>(list);
    }

    /**
     * 新增数据
     * 
     * @param t
     */
    public Integer insert(T t) {
        return this.mapper.insert(t);
    }
    
    /**
     * 新增对象，只插入不为空的字段
     * @param t
     * @return
     */
    public Integer insertSelective(T t) throws Exception{
        return this.mapper.insertSelective(t);
    }

    /**
     * 有选择地保存，选择不为null的字段作为插入字段
     * 
     * @param t
     * @return
     */
    public Integer saveSelective(T t) {
        return this.mapper.insertSelective(t);
    }

    /**
     * 根据主键更新数据
     * 
     * @param t
     * @return
     */
    public Integer update(T t) {
        return this.mapper.updateByPrimaryKey(t);
    }

    /**
     * 有选择地更新，选择不为null的字段作为插入字段
     * 
     * @param t
     * @return
     */
    public Integer updateSelective(T t) {
        return this.mapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 根据主键删除数据
     * 
     * @param id
     * @return
     */
    public Integer deleteById(Object id) throws Exception{
        return this.mapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     * 
     * @param clazz
     * @param property
     * @param values
     * @return
     */
    public Integer deleteByIds(Class<T> clazz, String property, List<Object> values) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, values);
        return this.mapper.deleteByExample(example);
    }

    /**
     * 根据条件删除数据
     * 
     * @param t
     * @return 
     */
    public Integer deleteByWhere(T t) {
        return this.mapper.delete(t);
    }
}
