package com.whu.groupon.merchant.dao;

import com.whu.groupon.merchant.entity.Merchant;
import com.whu.framework.common.page.PagingResult;
import com.whu.framework.common.persistence.BaseMybatisDAO;
import com.whu.framework.common.search.Search;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MerchantDAO
 */
@Repository
public class MerchantDAO extends BaseMybatisDAO {

    private final String MAPPER_NAMESPACE = "com.whu.groupon.merchant.entity.MerchantMapper";

    /**
     * 分页查询商家信息
     * @param search
     * @return
     */
    public PagingResult<Merchant> findMerchantForPage(Search search) {
        return this.findForPage(MAPPER_NAMESPACE + ".countPageMerchant", MAPPER_NAMESPACE + ".selectPageMerchant", search);
    }

    /**
     * 查询所有商家信息
     * @return
     */
    public List<Merchant> findAll() {
        return findAll(MAPPER_NAMESPACE + ".selectAll");
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public Merchant getById(Long id) {
        return findOne(MAPPER_NAMESPACE + ".selectById", id);
    }

    /**
     * 保存
     * @param merchant
     * @return
     */
    public int save(Merchant merchant) {
        return save(MAPPER_NAMESPACE + ".insertSelective", merchant);
    }


}
