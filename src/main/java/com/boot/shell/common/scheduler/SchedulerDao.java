package com.boot.shell.common.scheduler;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@DependsOnDatabaseInitialization
public class SchedulerDao {

    @Qualifier("sqlSessionTemplate")
    private final SqlSessionTemplate sqlSession;

    private final String namespace = "scheduler.scheduler_SQL";

    public List<CustomScheduleVo> selectScheduleList() {
        return sqlSession.selectList(namespace + ".selectScheduleList");
    }
}
