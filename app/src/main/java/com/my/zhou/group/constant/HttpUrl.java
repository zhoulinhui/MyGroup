package com.my.zhou.group.constant;

/**
 * Created by Administrator on 2017/4/10.
 */

public class HttpUrl {
    //公共接口
    public static final String API = "http://120.26.225.230:813";
    //发送短信
    public static final String CODE = API + "/index.php/api/api/ssendMsgRegist";
    //注册
    public static final String REGIST = API + "/index.php/api/express/wm_reg";
    //跑腿填写地区
    public static final String REGION = API + "/index.php/api/express/region_info";
    //跑腿人完善个人信息
    public static final String UPDATA = API + "/index.php/api/express/updata_info";
    //忘记密码
    public static final String FORGET = API + "/index.php/api/express/forgetPassword";
    //登录
    public static final String LOGIN = API + "/index.php/api/express/login";
    //修改开工/忙碌状态
    public static final String ISOPEN = API + "/index.php/api/express/is_open";
    //图片上传
    public static final String FILEUPLOAD = API + "/index.php/api/api/fileUpload";
    //获取用户信息
    public static final String GETINFO = API + "/index.php/api/express/get_info";
    //新任务
    public static final String MISSIONLIST = API + "/index.php/api/express/mission_list";
    //修改用户经纬度
    public static final String LONGLAT = API + "/index.php/api/express/longitude_latitude";
    //点击抢单
    public static final String FRONTRUNNER = API + "/index.php/api/express/front_runner";
    //待取货
    public static final String WAITGAIN = API + "/index.php/api/express/wait_gain";
    //待送达
    public static final String WAITSEND = API + "/index.php/api/express/wait_send";
    //订单详情
    public static final String ORDERDETAIL = API + "/index.php/api/express/order_detail";
    //确认取货
    public static final String CONFIRMPICK = API + "/index.php/api/express/confirm_pick";
    //.确认送达
    public static final String CONFIRMREACH = API + "/index.php/api/express/confirm_reach";
    //我的余额
    public static final String REMAININGSUM = API + "/index.php/api/express/remaining_sum";
    //结算明细
    public static final String MONEYDETAIL = API + "/index.php/api/express/money_detail";
    //历史订单
    public static final String HISTORYORDER = API + "/index.php/api/express/history_order";
    //绑定银行卡
    public static final String BINDBANK = API + "/index.php/api/express/bind_bank";
    //.提现申请
    public static final String WITHDRAWCASH = API + "/index.php/api/express/withdraw_cash";
}
