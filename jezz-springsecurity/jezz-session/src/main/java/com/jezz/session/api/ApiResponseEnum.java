package com.jezz.session.api;


/**
 * @Author Hugo.Wwg
 * @Since 2017-06-06
 */
public enum ApiResponseEnum implements BaseEnum {

    SUCCESS(200, "success", "成功", true),
    FAIL(100, "fail", "失败", false),
    NEED_REDIRECT(301,"need_redirect","需要跳转地址",true),
    FORBIDDEN(403, "forbidden", "没有权限", false),
    RESOURCE_NOT_FOUND(404, "resource_not_found", "资源不存在", false),
    DO_NOT_HAVE_ANY_MORE_RECORD(700, "do_not_have_any_more_record", "没有更多记录", false),

    INTERNAL_ERROR(500, "internal_error", "服务器处理失败", false),

    PARAMETER_INVALID(600, "parameter_invalid", "非法参数", false),
    PARAMETER_CANT_BE_EMPTY(601, "parameter_cant_be_empty", "缺少必要参数", false),
    NEED_USER_LOGIN(602, "need_user_login", "需要用户登录", false),
    ILLEGAL_PROTOCOL(603, "illegal_protocol", "非法请求", false),
    VALIDATE_CODE_ERROR(604, "validate_code_error", "手机验证码错误", false),
    VINSUFFICIENT_PERMISSIONS(605, "vinsufficient_permissions", "权限不足", false),
    VALIDATE_COMMOM_CODE_ERROR(606, "validate_commom_code_error", "验证码错误", false),
    VALIDATE_SEND_CODE_ERROR(607, "validate_send_code_error", "验证码发送失败", false),
    USER_AREA_ERROR(608, "user_area_error", "客户国内外区域不匹配", false),
    SIGNATURE_EXPIRED(610, "signature_expired", "签名过期", false),
    SIGNATURE_INVALID(611, "signature_invalid", "非法签名", false),
    EXIST_USER_TASK(612, "exist_user_task", "该用户已经提交任务", false),
    LOTTERY_NO_CHANCE(613, "lottery_no_chance", "该用户已经没有抽奖机会", false),
    ACTIVITY_END(614, "activity_end", "该活动已经结束", false),
    ERROR_RETURN_REFUSE(615, "error_return_refuse", "此比金额不允许返还", false),
    API_NOT_SUPPORT_CHANNEL(616, "API_NOT_SUPPORT_CHANNEL", "此APi不支持该通道", false),
    FREQUENT_OPERATION(617,"FREQUENT_OPERATION","操作频繁，请稍后再试！",false),
    VALID_QINGYUN_RECHARGED(618,"valid_qingyun_recharged","该用户已充值1元兑青云券",false),
    UPDATE_TOTAL_CREDIT_ERROR(619,"update_total_credit_error","修改值过小，不能修改总额度",false),
    UPDATE_SUB_CREDIT_ERROR(620,"update_sub_credit_error","修改值过大，不能修改子账号额度",false),
    UPDATE_SUB_CREDIT_LOW_ERROR(621,"update_sub_credit_low_error","修改值过小，不能修改子账号额度",false),



    /**
     * 登陆注册异常
     */
    ACCOUNT_BINDING_USER_NULL(800, "account_binding_user_null", "账号不存在", false),
    ACCOUNT_NOT_BINDING(801, "account_not_binding", "未绑定设备", false),
    ACCOUNT_BINDING_FAIL(802, "account_binding_fail", "设备绑定失败", false),
    ACCOUNT_NOACTIVE(803, "account_no_active", "账号未激活", false),
    ACCOUNT_LOCKED(804, "account_locked", "账号已被禁用", false),
    ACCOUNT_USERNAME_PASSWORD_FAIL(805, "account_username_password_fail", "用户名或密码错误", false),
    ACCOUNT_UNIONID_FAIL(806, "account_unionid_fail", "账号未绑定第三方用户", false),
    ACCOUNT_PHONE_FAIL(807, "account_phone_fail", "手机未绑定第三方用户", false),
    ACCOUNT_UNIONID_PHONE(808, "account_unionid_phone", "手机已绑定其他第三方用戶", false),
    ACCOUNT_WEIXIN_FAIL(809, "account_weixin_fail", "微信登录失败", false),
    ACCOUNT_WEIBO_FAIL(810, "account_weibo_fail", "微博登录失败", false),
    ACCOUNT_LOGOUT_FAIL(811, "account_logout_fail", "登出失败", false),
    ACCOUNT_LOGIN_IPLIMITFAIL(812, "account_login_iplimitfail", "ip被限制登录", false),
    ACCOUNT_QQ_FAIL(813, "account_qq_fail", "qq登录失败", false),
    NOREPEAT_SELECT_SOFTWARE(814, "norepeat_select_software", "无法重复选择常用软件", false),
    ACCOUNT_UNIONID_EXISTS(815, "account_unionid_exists", "unionid已存在", false),
    ACCOUNT_IPLIMIT_EXISTS(816, "account_iplimit_exists", "无法添加重复的限制ip", false),
    ACCOUNT_NO_WXPUB_SUBSCRIBE(817, "account_no_wxpub_subscribe", "未关注微信公众号", false),
    ACCOUNT_LOGIN_TIMES_WARNING(818, "login_times_warning", "您还有三次机会", false),
    ACCOUNT_INVALID_ADDRESSES(819, "account_invalid_addresses", "无效的邮件地址", false),
    ACCOUNT_LOGIN_TWO_TIMES_WARNING(820, "login_two_times_warning", "您还有两次次机会", false),
    ACCOUNT_LOGIN_ONE_TIMES_WARNING(821, "login_one_times_warning", "您还有一次机会", false),
    ACCOUNT_DISENABLED(822, "account_disenabled", "账号已被停用", false),
    VERIFICATION_CODE_SEND_FORBIDDEN(823, "verification_code_send_forbidden", "今日已不能发送验证码", false),
    ACCOUNT_LOGIN_NEED_GRAPHIC_VERIFICATION(824, "account_login_need_graphic_verification", "需要图形验证码验证", false),
    ACCOUNT_CODE_VALIDATE_OVER(825, "account_code_validate_over", "验证次数过多，请重新发送", false),
    ACCOUNT_LOGIN_NO_UNIONID(826,"account_login_no_unionid","不存在unionid",false),
    ACCOUNT_LOGIN_NO_BIND(827,"account_login_no_bind","未绑定平台账号，请联系管理员绑定",false),
    ACCOUNT_LOGIN_SENCE_EXPIRED(828,"account_login_sence_expired","二维码已经过期",false),
    ORDINARY_ACCONT_CANNOT_LOGIN(829,"ordinary_accont_cannot_login","非管理员账号不能登录",false),


    VALIDATE_PHONE_FAIL(900, "validate_phone_fail", "手机号已存在", false),
    VALIDATE_EMAIL_FAIL(901, "validate_email_fail", "邮箱已存在", false),
    VALIDATE_USERNAME_FAIL(902, "validate_username_fail", "用户名已存在", false),
    ACCOUNT_EMAIL_FAIL(903, "account_email_fail", "邮箱未绑定账户", false),
    CURRENCY_NOT_SUPPORT(904, "currency_not_support", "币种不支持", false),
    AGENT_NOT_SUPPORT(905, "agent_not_support", "代理商不支持", false),
    AMOUNT_NOT_SUPPORT(906, "amount_not_support", "请输入合理的充值范围", false),
    COUPONNO_NOT_SUPPORT(908, "couponno_not_support", "优惠码不支持", false),
    PAYMETHOD_NOT_SUPPORT(909, "paymethod_not_support", "支付方式不支持", false),
    NO_INVOICE_ORDER(910, "no_invoice_order", "无可用的发票订单", false),
    NO_INVOICE_ADDRESS(911, "no_invoice_address", "无可用的发票收件地址", false),
    SUBUSER_EXISTS_TASKS(912, "subuser_exists_tasks", "无法删除一个存在执行任务的子账号", false),
    SUBUSER_ARREARAGE(913, "subuser_arrearage", "当前子账号欠费", false),
    DELSUBUSER_EXISTS_BALANCE(914, "delsubuser_exists_balance", "删除子账号存在余额", false),
    NO_INVOICE_TEMPLATE(915, "no_invoice_template", "无可用的发票信息", false),
    RECEIPT_TYPE_ERROR(916, "receipt_type_error", "发票类型不匹配", false),
    RECEIPT_TITLE_EXISTS(917, "receipt_title_exists", "发票抬头已存在", false),
    RECEIPT_RECOGNITION_EXISTS(918, "receipt_recognition_exists", "发票纳税人识别号已存在", false),
    USER_ACCOUNT_NOT_EXIST(919, "user_account_not_exist", "用户账号不存在", false),
    NO_LATEST_VERSION(920, "no_latest_version", "暂无客户端最新版本", false),
    BALANCE_NOT_ENOUGH(921, "balance_not_enough", "账户余额不够此次转账", false),
    ACCOUNT_PHONE_BIND_FAIL(922, "account_phone_bind_fail", "手机未绑定账户", false),
    RECEIPT_TEMPLATE_EXISTS(923, "receipt_template_exists", "发票模板已存在", false),
    UPDATE_COUPON_ERROR(924, "update_coupon_error", "更新用户优惠码失败", false),
    INTELLIGENT_VERIFY_FAIL(925, "intelligent_verify_fail", "智能验证失败", false),
    ORGANIZATION_NAME_EXISTS(926, "organization_name_exists", "部门名称已存在", false),
    PAY_RECORD_NOT_EXIST(927, "pay_record_not_exist", "订单不存在", false),
    THIRD_PARTIES_PAY_VERIFY_FAIL(928, "third_parties_pay_verify_fail", "三方支付验签失败", false),
    UPDATE_USER_ACCOUNT_BALANCE_FAIL(929, "update_user_account_balance_fail", "更新用户账户余额信息失败", false),
    UPDATE_USER_ACCOUNT_ROLE_FAIL(930, "update_user_account_role_fail", "更新用户账户等级信息失败", false),
    UPDATE_PAT_RECORD_FAIL(931, "update_pat_record_fail", "异步回调更新订单信息失败", false),
    ROLE_NAME_EXISTS(932, "role_name_exists", "权限名称已存在", false),
    CREATE_USER_PROJECT_ERROR(933, "create_user_project_error", "创建用户默认project失败", false),
    RPC_TASKRENDERING_ERROR(934, "rpc_task_error", "调用task模块dubbo服务出错", false),
    COUPON_USED_OR_EXPIRED_ERROR(935, "coupon_used_or_expired", "优惠码已使用或已过期", false),
    SUBUSER_QUANTITY_ENOUGH(936, "subuser_quantity_enough", "添加子账号达到上限", false),
    TASKID_NOT_EXIST(937,"taskid_not_exist","任务号不存在",false),
    MISMATCH_TASKID_USERID(938,"mismatch_taskid_userid","任务id和用户id不匹配",false),
    INVOICE_ORDERS_OUT_OF_LENGTH(939, "invoice_orders_out_of_length", "开具发票订单数超过限制", false),
    EXPORT_TOO_MANY(940, "export_too_many", "导出数据超出5000条", false),
    DELSUBUSER_EXISTS_COUPON_BALANCE(941, "delsubuser_exists_coupon_balance", "删除子账号存在渲染券余额", false),
    DISABLED_OPEN_SDK(942, "disabled_open_sdk", "无权限，请联系您的商务经理为您开放权限", false),
    STORAGE_TYPE_DOES_NOT_MATCH(950, "storage_type_does_not_match", "原存储类型和目标存储类型不匹配", false),
    DISABLED_DELETE_ACTIVITY(972, "disabled_delete_activity", "该活动券已被领取不予许删除", false),
    ADMIN_NO_USER(973, "admin_no_user", "源名下没有用户", false),
    CHARGING_ERROR(974, "CHARGING_ERROR", "用户扣费异常", false),

    REDIS_CACHE_FAIL(1000, "redis_cache_fail", "redis缓存异常", false),

    PLUGIN_IMPORT_FAIL(1001, "plugin_import_fail", "插件导入异常", false),
    TEMP_PASSWORD_MISS_INFO(1002, "temp_password_miss_info", "缺少用户名或邮箱或手机号码，无法获取临时密码", false),
    DENY_SUB_SET_BALANCE_SHARE(1003, "DENY_SUB_SET_BALANCE_SHARE", "不允许设置余额共享", false)
    ;

    protected int id;

    protected String code;

    protected String label;

    protected boolean success;

    ApiResponseEnum(int id, String code, String label, boolean success) {
        this.id = id;
        this.code = code;
        this.label = label;
        this.success = success;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean success() {
        return success;
    }

}
