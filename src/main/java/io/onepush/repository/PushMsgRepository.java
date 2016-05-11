package io.onepush.repository;

import io.onepush.model.PushMsg;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 推送的待确认消息仓库
 *
 * Created by yaoyasong on 2016/5/10.
 */
public interface PushMsgRepository extends PagingAndSortingRepository<PushMsg, String> {

    List<PushMsg> findByAudienceDeviceId(String audienceDeviceId, Sort sort);
}
