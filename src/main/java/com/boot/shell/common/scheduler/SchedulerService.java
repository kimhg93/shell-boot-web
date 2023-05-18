package com.boot.shell.common.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final SchedulerDao schedulerDao;

    public List<CustomScheduleVo> selectScheduleList() {
        return schedulerDao.selectScheduleList();
    }

}
