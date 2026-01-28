package cn.xbatis.plus.base;

import cn.xbatis.core.mybatis.mapper.context.Pager;
import cn.xbatis.core.sql.executor.chain.DeleteChain;
import cn.xbatis.core.sql.executor.chain.QueryChain;
import cn.xbatis.core.sql.executor.chain.UpdateChain;
import cn.xbatis.plus.vo.PagerVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class ServiceImpl<D extends BaseMapper<T>, T> implements IService<T> {

    @Autowired
    protected D baseMapper;

    @Override
    public QueryChain<T> lambdaQuery() {
        return QueryChain.of(baseMapper);
    }

    @Override
    public UpdateChain lambdaUpdate() {
        return UpdateChain.of(baseMapper);
    }

    @Override
    public DeleteChain lambdaDelete() {
        return DeleteChain.of(baseMapper);
    }

    @Override
    public T getById(Serializable id) {
        return this.baseMapper.getById(id);
    }

    @Override
    public List<T> list() {
        return this.baseMapper.listAll();
    }

    @Override
    public List<T> listByIds(Collection<? extends Serializable> ids) {
        return this.baseMapper.listByIds(ids);
    }

    @Override
    public PagerVo<T> page(Pager<T> pager) {
        return new PagerVo<>(this.baseMapper.page(pager));
    }

    @Override
    public boolean save(T entity) {
        return this.baseMapper.save(entity) > 0;
    }

    @Override
    public boolean saveBatch(Collection<T> list) {
        return this.baseMapper.saveBatch(list) > 0;
    }

    @Override
    public boolean updateById(T entity) {
        return this.baseMapper.update(entity) > 0;
    }

    @Override
    public boolean updateBatchById(Collection<T> list) {
        return this.baseMapper.updateBatch(list) > 0;
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        return this.baseMapper.saveOrUpdate(entity) > 0;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> list) {
        return this.baseMapper.saveOrUpdate(list) > 0;
    }

    @Override
    public boolean deleteById(Serializable id) {
        return this.baseMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return this.baseMapper.deleteByIds(ids) > 0;
    }
}
