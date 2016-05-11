package io.onepush.repository;

import io.onepush.model.UserDevice;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 用户设备信息保存仓库
 * Created by yaoyasong on 2016/5/3.
 */
public interface DeviceRepository extends PagingAndSortingRepository<UserDevice,String>, DeviceRepositoryCustom {

    UserDevice findByAppIdAndNativeToken(String appId, String nativeToken);
}
