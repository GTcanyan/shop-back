package com.javaweb.hhjrp.service.impl;

import com.javaweb.hhjrp.dao.PicDao;
import com.javaweb.hhjrp.result.Results;
import com.javaweb.hhjrp.service.PicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class PicServiceImpl implements PicService {

    @Autowired
    private PicDao picDao;

    // 上传图片
    @Override
    public Results add(String uuid, int userID) {
        picDao.add(uuid, userID);
        return new Results(1, "上传成功");
    }

}
