package io.onepush.repository;

import io.onepush.model.UserDevice;
import io.onepush.util.CriteriaCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户设备自定义查询仓库接口实现
 * Created by yaoyasong on 2016/5/4.
 */
@Repository
public class DeviceRepositoryImpl implements DeviceRepositoryCustom<UserDevice, String> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<UserDevice> findDevicesByQuery(Map<String, Object> params) {
        return mongoTemplate.find(getQuery(params), UserDevice.class);
    }

    private Query getQuery(Map<String, Object> params) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is("1"));
        for (String key : params.keySet()) {
            Criteria c = null;
            if (key.equals("alias")) {
                c = Criteria.where(key).in((Collection<String>)params.get(key));
            } else if (key.equals("listenFlag")) {
                c = CriteriaCreator.like(key,(String)params.get(key));
            } else {
                c = Criteria.where(key).is(params.get(key));
            }
            query.addCriteria(c);
        }
        return query;
    }

    @Override
    public Page<UserDevice> findDevicesByQuery(Map<String, Object> params, Pageable pageable) {
        Query query = getQuery(params);
        long count = mongoTemplate.count(query, UserDevice.class);
        query.skip(pageable.getOffset());
        query.limit(pageable.getPageSize());
         return new PageImpl<UserDevice>(mongoTemplate.find(query, UserDevice.class),pageable,count);
    }
}
