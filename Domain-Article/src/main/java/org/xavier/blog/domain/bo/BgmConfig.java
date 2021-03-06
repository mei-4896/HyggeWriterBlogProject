package org.xavier.blog.domain.bo;

import org.xavier.common.util.PropertiesHelper;
import org.xavier.common.util.UtilsCreator;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 19-12-28
 * @since Jdk 1.8
 */
public class BgmConfig {

    /**
     * 0 本地音乐 1 网易云音乐外链
     */
    private Integer bgmType;

    /**
     * 音乐路径
     */
    private String src;

    /**
     * 是否启用 0 禁用 1 启用
     */
    private boolean state;

    public void setDefaultConfigIfAbsent() {
        PropertiesHelper propertiesHelper = UtilsCreator.getDefaultPropertiesHelperInstance();
        this.state = propertiesHelper.booleanFormatOfNullable(state, false, "[state] should be a boolean.");
        if (state) {
            propertiesHelper.intRangeNotNull(bgmType, "[bgmType] can't be null,and it should be a integer number.");
            propertiesHelper.stringNotNull(src, "[src] can't be null,and it should be a string.");
        }
    }

    public Integer getBgmType() {
        return bgmType;
    }

    public void setBgmType(Integer bgmType) {
        this.bgmType = bgmType;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}