package cn.xbatis.plus.base;

import cn.xbatis.core.mybatis.mapper.context.Pager;
import cn.xbatis.core.sql.executor.chain.DeleteChain;
import cn.xbatis.core.sql.executor.chain.QueryChain;
import cn.xbatis.core.sql.executor.chain.UpdateChain;
import cn.xbatis.plus.vo.PagerVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface IService<T> {

    /**
     * 获取查询链
     * @return 查询链
     */
    QueryChain<T> lambdaQuery();

    /**
     * 获取更新链
     * @return 更新链
     */
    UpdateChain lambdaUpdate();

    /**
     * 获取删除链
     * @return 删除链
     */
    DeleteChain lambdaDelete();

    /**
     * 根据id查询
     * @param id  id
     * @return 实体
     */
    T getById(Serializable id);

    /**
     * 查询所有
     * @return 所有实体
     */
    List<T> list();

    /**
     * 根据ids查询
     * @param ids id列表
     * @return 实体列表
     */
    List<T> listByIds(Collection<? extends Serializable> ids);

    /**
     * 分页查询
     * @param pager 分页参数
     * @return 分页结果
     */
    PagerVo<T> page(Pager<T> pager);

    /**
     * 批量保存
     * @param list 实体列表
     * @return 是否保存成功
     */
    boolean saveBatch(Collection<T> list);

    /**
     * 根据id更新
     * @param entity 实体
     * @return 是否更新成功
     */
    boolean updateById(T entity);

    /**
     * 批量更新
     * @param list 实体列表
     * @return 是否更新成功
     */
    boolean updateBatchById(Collection<T> list);

    /**
     * 保存或更新
     * @param entity 实体
     * @return 是否保存或更新成功
     */
    boolean saveOrUpdate(T entity);

    /**
     * 批量保存或更新
     * @param list 批量实体列表
     * @return 是否保存或更新成功
     */
    boolean saveOrUpdateBatch(Collection<T> list);

    /**
     * 根据id删除
     * @param id  id
     * @return 是否删除成功
     */
    boolean deleteById(Serializable id);

    /**
     * 批量删除
     * @param ids id列表
     * @return 是否删除成功
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);


}
