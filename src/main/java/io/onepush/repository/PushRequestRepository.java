package io.onepush.repository;

import io.onepush.model.PushRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 推送请求存储仓库接口
 * Created by yaoyasong on 2016/5/3.
 */
public interface PushRequestRepository extends PagingAndSortingRepository<PushRequest, String> {
}
