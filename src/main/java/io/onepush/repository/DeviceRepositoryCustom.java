package io.onepush.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 用户设备自定义查询仓库接口
 *
 * Created by yaoyasong on 2016/5/4.
 */
public interface DeviceRepositoryCustom<T, ID> {
    List<T> findDevicesByQuery(Map<String,Object> params);

    Page<T> findDevicesByQuery(Map<String,Object> params, Pageable pageable);
}
