package io.onepush.repository;

import io.onepush.model.Application;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Application 仓库
 * Created by yaoyasong on 2016/5/11.
 */
public interface AppRepository extends PagingAndSortingRepository<Application,String> {
}
