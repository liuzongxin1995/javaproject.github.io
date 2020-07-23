package com.liu.javaproject.model.common;

/**
 * @Author huangtao
 * @create 2019/6/15 17:05
 */
public class BosConstants {

    //登录平台 1-web, 2-手持pos
    public final static String PLATFORM_OTHER = "1";
    public final static String PLATFORM_WEB = "1";
    public final static String PLATFORM_POS = "2";
    public final static String DEFAULT_PLATFORM = "1";
    public final static String DEFAULT_DEVICEID = "web";

    // 用户状态 0-作废 1-启用
    public final static String USER_STATUS_ENABLE = "1";
    public final static String USER_STATUS_DISABLE = "0";

    //是否初始密码 0-非初始密码|1-初始密码
    public static final String INIT_PASSWORD_NO = "0";
    public static final String INIT_PASSWORD_YES = "1";

    //商品及商品种类删除标识 0-未删除|1-已删除
    public static final String IS_DELETE_NO = "0";
    public static final String IS_DELETE_YES = "1";

    //商品分类是否生效标识
    public static final String IS_ENABLED_NO = "0";
    public static final String IS_ENABLED_YES = "1";

    //是否为重点商品（0：非重点商品，1：重点商品）
    public static final String IS_KEY_GOODS_NO = "0";
    public static final String IS_KEY_GOODS_YES = "1";

    //标识
    public static final String IS_FALSE = "0";
    public static final String IS_TRUE = "1";

    // 员工卡类型 1-加油卡|2-维修卡|3-校验卡
    public final static String FUELLING_CARD = "1";
    public final static String MAINTAIN_CARD = "2";
    public final static String VERITY_VARD = "3";

    //BDispenserInfo 信息
    public static final String BDISPPENSER_DP_NAME = "号机";
    public static final String BDISPPENSER_FP_NAME = "号面";

    // 日结状态 1-日结中|2-日结成功|3-日结失败
    public static final String DAYLY_SHIFT_STARTING = "1";
    public static final String DAYLY_SHIFT_SUCCESS = "2";
    public static final String DAYLY_SHIFT_FAILURE = "3";

    //班结状态 1-开班|2-交班 | 0- 准备开班
    public static final String WORK_SHIFT_RERDY = "0";
    public static final String WORK_SHIFT_BEGIN = "1";
    public static final String WORK_SHIFT_CLOSE = "2";

    /**
     * 上传文件目录前缀
     */
    public static final String FILE_PREFIX = "/images/";

    /**
     * excel文件目录前缀
     */
    public static final String EXCEL_FILE_PREFIX = "/excel/";

    //类型 1-加油 2-加氢
    public static final String STATION_TYPE_OIL = "1";
    public static final String STATION_TYPE_HYDROGEN = "2";

    //报警类型：1-液位仪报警；2-加油机超长加油；3-损益报警
    public static final String ALARM_TYPE_ONE = "1";
    public static final String ALARM_TYPE_TWO = "2";
    public static final String ALARM_TYPE_THREE = "3";

    //液位仪报警子类型；0-脱机；1-油水高超过等于；2-油水高低于等于；3-水高低于等于；4-泄漏状态异常;5-液位计油高和人工检尺之差大于
    public static final String ALARM_TYPE_ONE_ZERO = "0";
    public static final String ALARM_TYPE_ONE_ONE = "1";
    public static final String ALARM_TYPE_ONE_TWO = "2";
    public static final String ALARM_TYPE_ONE_THREE = "3";
    public static final String ALARM_TYPE_ONE_FOUR = "4";
    public static final String ALARM_TYPE_ONE_FIVE = "5";

    //损益报警子类型：0-连续三天损耗率超过或等于；1-当日损耗率超过或等于；2-盘存超损率大于或等于
    public static final String ALARM_TYPE_THREE_ZERO = "0";
    public static final String ALARM_TYPE_THREE_ONE = "1";
    public static final String ALARM_TYPE_THREE_TWO = "2";


}
