package io.onepush.repository;

import io.onepush.Server;
import io.onepush.model.PushCertification;
import io.onepush.repository.CertificationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yaoyasong on 2016/5/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Server.class)
public class CertRepositoryTest {

    @Autowired
    private CertificationRepository certificationRepository;

    @Test
    public void saveCert() {
        PushCertification cert = new PushCertification();
        cert.setAppId("testapp1");
        cert.setCertFile("E:/appcert.p12");
        cert.setCertPassword("123456");
        certificationRepository.save(cert);
    }
}
