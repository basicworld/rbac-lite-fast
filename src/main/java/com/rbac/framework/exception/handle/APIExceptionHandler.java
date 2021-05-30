/**
 *
 */
package com.rbac.framework.exception.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rbac.common.constant.ResultConstants;
import com.rbac.framework.web.domain.AjaxResult;

/**
 * 接口请求返回前，封装处理所有exception
 *
 * @author wlfei
 * @date 2021-05-10
 */
@RestControllerAdvice
public class APIExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(APIExceptionHandler.class);

    /**
     * 捕获controller的所有异常，返回ajax
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult allExceptionToAjaxResult(Exception e) {
        logger.error("Controller异常：{}", e.getMessage());
        e.printStackTrace();
        return AjaxResult.error(ResultConstants.CODE_SERVER_ERROR, "服务器运行异常，请联系管理员！");
    }

}
