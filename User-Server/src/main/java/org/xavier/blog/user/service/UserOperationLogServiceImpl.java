package org.xavier.blog.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xavier.blog.common.enums.UserTypeEnum;
import org.xavier.blog.user.dao.UserOperationLogMapper;
import org.xavier.blog.user.domain.po.user.UserOperationLog;
import org.xavier.common.exception.Universal403Exception;
import org.xavier.common.logging.HyggeLoggerMsgBuilder;
import org.xavier.webtoolkit.base.DefaultUtils;
import org.xavier.webtoolkit.domain.PageResult;

import java.util.ArrayList;


/**
 * 用户操作日志 service
 */
@Service
public class UserOperationLogServiceImpl extends DefaultUtils {
    @Autowired
    UserOperationLogMapper userOperationLogMapper;
    @Autowired
    UserServiceImpl userService;

    /**
     * 添加用户操作日志
     */
    public Boolean saveUserOperationLog(String operatorUId, UserOperationLog userOperationLog) throws Universal403Exception {
        userOperationLog.validate();
        userService.checkRight(operatorUId, UserTypeEnum.ROOT, "U00000003");
        Integer saveUserOperationLogAffectedRow = userOperationLogMapper.saveUserOperationLog(userOperationLog);
        Boolean saveUserOperationLogFlag = saveUserOperationLogAffectedRow == 1;
        if (!saveUserOperationLogFlag) {
            logger.warn(HyggeLoggerMsgBuilder.assertFail("save userOperationLog affected row", "1", saveUserOperationLogAffectedRow, userOperationLog));
        }
        return saveUserOperationLogFlag;
    }

    /**
     * 添加用户操作日志
     */
    public PageResult<UserOperationLog> quarryUserOperationLogByUIdList(String operatorUId, ArrayList<String> uIdList, Integer currentPage, Integer size, String orderKey, Boolean isDESC) throws Universal403Exception {
        userService.checkRight(operatorUId, UserTypeEnum.ROOT, "U00000003");
        String order = isDESC ? "DESC" : "ASC";
        switch (orderKey) {
            case "uId":
                orderKey = "uId";
                break;
            default:
                orderKey = "ts";
                break;
        }
        ArrayList<UserOperationLog> dataSet;
        Integer totalCount;
        if (uIdList == null || uIdList.size() < 1) {
            dataSet = userOperationLogMapper.queryUserOperationLogByUIdList(uIdList, (currentPage - 1) * size, size, orderKey, order);
            totalCount = userOperationLogMapper.queryTotalCountOfUserOperationLogByUIdList(uIdList, (currentPage - 1) * size, size, orderKey, order);
        } else {
            dataSet = userOperationLogMapper.queryUserOperationLogByUIdList(null, (currentPage - 1) * size, size, orderKey, order);
            totalCount = userOperationLogMapper.queryTotalCountOfUserOperationLogByUIdList(null, (currentPage - 1) * size, size, orderKey, order);
        }
        PageResult<UserOperationLog> result = new PageResult();
        result.setResultSet(dataSet);
        result.setTotalCount(totalCount);
        return result;
    }
}