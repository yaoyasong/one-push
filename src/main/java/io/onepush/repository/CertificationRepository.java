package io.onepush.repository;

import io.onepush.model.PushCertification;
import org.springframework.data.repository.CrudRepository;

/**
 * 证书存储仓库
 * Created by yaoyasong on 2016/5/6.
 */
public interface CertificationRepository extends CrudRepository<PushCertification, String> {
    PushCertification findByAppId(String appId);
}
