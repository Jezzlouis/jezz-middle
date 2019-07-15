## xxl-job 部署

### 调度中心部署

github上面clone 下来 https://github.com/xuxueli/xxl-job

1.首先是application.properties
主要是端口 还有datasource email 需要配置

    server.port=7755 
    server.context-path=/xxl-job-admin
    
    ### resources
    spring.mvc.static-path-pattern=/static/**
    spring.resources.static-locations=classpath:/static/
    
    ### freemarker
    spring.freemarker.templateLoaderPath=classpath:/templates/
    spring.freemarker.suffix=.ftl
    spring.freemarker.charset=UTF-8
    spring.freemarker.request-context-attribute=request
    spring.freemarker.settings.number_format=0.##########
    
    ### mybatis
    mybatis.mapper-locations=classpath:/mybatis-mapper/*Mapper.xml
    
    ### xxl-job, datasource
    spring.datasource.url=jdbc:mysql://localhost:3306/xxl_job?Unicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=true 
    spring.datasource.username=root
    spring.datasource.password=rendering2017_test 
    spring.datasource.driver-class-name=com.mysql.jdbc.Driver
    
    spring.datasource.type=org.apache.tomcat.jdbc.pool.DataSource
    spring.datasource.tomcat.max-wait=10000
    spring.datasource.tomcat.max-active=30
    spring.datasource.tomcat.test-on-borrow=true
    spring.datasource.tomcat.validation-query=SELECT 1
    spring.datasource.tomcat.validation-interval=30000
    
    ### xxl-job email
    spring.mail.host=smtpdm.aliyun.com 
    spring.mail.port=465 
    spring.mail.username=xxxxxxxxx 
    spring.mail.password=xxxxxxxx 
    spring.mail.properties.mail.smtp.auth=true 
    spring.mail.properties.mail.smtp.starttls.enable=true 
    spring.mail.properties.mail.smtp.starttls.required=true 
    spring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory 
   
    ### xxl-job, access token
    xxl.job.accessToken=
    
    ### xxl-job, i18n (default empty as chinese, "en" as english)
    xxl.job.i18n=
    
2.然后是配置 logback.xml

    <?xml version="1.0" encoding="UTF-8"?>
    <configuration debug="false" scan="true" scanPeriod="1 seconds">
    
        <contextName>logback</contextName>
        // 很重要 这里配错就会报错无法启动,报的就是日志有问题
        <property name="log.path" value="/opt/xxl-job/xxl-job-admin.log"/>
    
        <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
            <encoder>
                <pattern>%d{HH:mm:ss.SSS} %contextName [%thread] %-5level %logger{36} - %msg%n</pattern>
            </encoder>
        </appender>
    
        <appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <file>${log.path}</file>
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>${log.path}.%d{yyyy-MM-dd}.zip</fileNamePattern>
            </rollingPolicy>
            <encoder>
                <pattern>%date %level [%thread] %logger{36} [%file : %line] %msg%n
                </pattern>
            </encoder>
        </appender>
    
        <root level="info">
            <appender-ref ref="console"/>
            <appender-ref ref="file"/>
        </root>
    
    </configuration>

3. 然后就是 编译 mvn clean install 
           部署xxl-job-admin下边的包就行(core包和example包不用管) nohup java -jar xxl-job-admin-2.1.1.jar &
           打开网站可以看见 http://localhost:7755/xxl-job-admin/  账号 admin 密码 123456
           
### 执行器部署

1.引入maven依赖 (如果依赖有netty包可能会造成netty包冲突,报错的时候调试下源码选择版本最高的那个包解决冲突即可)
    
    <dependency>
        <groupId>com.xuxueli</groupId>
        <artifactId>xxl-job-core</artifactId>
        <version>2.1.0</version>
    </dependency>
    
2. XxlJobConfig文件
    
    不需要修改直接复制使用
    
3. JobHandler (不能有名字重复,重复会报错)
    
    java的话不需要选择GLUE模式，全都选bean模式 使用示例 DemoJobHandler    
    
4. 配置网站的Handler 注意和代码的JobHandler的名字保持一致

5. 配置日志 logback.xml不用变 

6. 配置 application.properties

日志保留天数最少保留4天，否则永久保留

注意是application.properties 里面的值   xxl.job.executor.logpath = xxx 这个路径一定要能找到并且有权限访问,否则报错file找不到

xxl.job.executor.port = xxxx 注意端口不能跟server.port端口一样，且不能跟其他执行器有冲突, 否则会报错

    # web port
    server.port=8081
    
    # log config
    logging.config=classpath:logback.xml
    
    
    ### xxl-jobhandler admin address list, such as "http://address" or "http://address01,http://address02"
    xxl.job.admin.addresses=http://10.60.197.85:7755/xxl-job-admin
    
    ### xxl-jobhandler executor address
    xxl.job.executor.appname=xxl-job-executor
    xxl.job.executor.ip=
    xxl.job.executor.port=7001
    
    ### xxl-jobhandler, access token
    xxl.job.accessToken=
    
    ### xxl-jobhandler log path
    xxl.job.executor.logpath=/Users/jezzlouis/xxl-job/jobhandler
    ### xxl-jobhandler log retention days
    xxl.job.executor.logretentiondays=-1
 
 7. 写代码的时候 使用 XxlJobLogger.log 就能把 日志 在 网站页面的执行日志 打印出来(不用就没有)   
 
 ###
    
    public static void trigger(int jobId, TriggerTypeEnum triggerType, int failRetryCount, String executorShardingParam, String executorParam) {
        // load data
        // 从数据库根据jobId找到任务信息
        XxlJobInfo jobInfo = XxlJobAdminConfig.getAdminConfig().getXxlJobInfoDao().loadById(jobId);
        if (jobInfo == null) {
            logger.warn(">>>>>>>>>>>> trigger fail, jobId invalid，jobId={}", jobId);
            return;
        }
        if (executorParam != null) {
            jobInfo.setExecutorParam(executorParam);
        }
        // 获取执行器信息
        int finalFailRetryCount = failRetryCount>=0?failRetryCount:jobInfo.getExecutorFailRetryCount();
        XxlJobGroup group = XxlJobAdminConfig.getAdminConfig().getXxlJobGroupDao().load(jobInfo.getJobGroup());

        // sharding param
        // 执行器分片参数不为空
        int[] shardingParam = null;
        if (executorShardingParam!=null){
            String[] shardingArr = executorShardingParam.split("/");
            if (shardingArr.length==2 && isNumeric(shardingArr[0]) && isNumeric(shardingArr[1])) {
                shardingParam = new int[2];
                shardingParam[0] = Integer.valueOf(shardingArr[0]);
                shardingParam[1] = Integer.valueOf(shardingArr[1]);
            }
        }
        // 如果是分片广播路由策略
        if (ExecutorRouteStrategyEnum.SHARDING_BROADCAST==ExecutorRouteStrategyEnum.match(jobInfo.getExecutorRouteStrategy(), null)
                && group.getRegistryList()!=null && !group.getRegistryList().isEmpty()
                && shardingParam==null) {
            for (int i = 0; i < group.getRegistryList().size(); i++) {
                processTrigger(group, jobInfo, finalFailRetryCount, triggerType, i, group.getRegistryList().size());
            }
        } else {
            if (shardingParam == null) {
                shardingParam = new int[]{0, 1};
            }
            processTrigger(group, jobInfo, finalFailRetryCount, triggerType, shardingParam[0], shardingParam[1]);
        }

    }
 
 ### 路由策略
 
    public enum ExecutorRouteStrategyEnum {
    
        FIRST(I18nUtil.getString("jobconf_route_first"), new ExecutorRouteFirst()),
        LAST(I18nUtil.getString("jobconf_route_last"), new ExecutorRouteLast()),
        ROUND(I18nUtil.getString("jobconf_route_round"), new ExecutorRouteRound()),
        RANDOM(I18nUtil.getString("jobconf_route_random"), new ExecutorRouteRandom()),
        CONSISTENT_HASH(I18nUtil.getString("jobconf_route_consistenthash"), new ExecutorRouteConsistentHash()),
        LEAST_FREQUENTLY_USED(I18nUtil.getString("jobconf_route_lfu"), new ExecutorRouteLFU()),
        LEAST_RECENTLY_USED(I18nUtil.getString("jobconf_route_lru"), new ExecutorRouteLRU()),
        FAILOVER(I18nUtil.getString("jobconf_route_failover"), new ExecutorRouteFailover()),
        BUSYOVER(I18nUtil.getString("jobconf_route_busyover"), new ExecutorRouteBusyover()),
        SHARDING_BROADCAST(I18nUtil.getString("jobconf_route_shard"), null);
    
        ExecutorRouteStrategyEnum(String title, ExecutorRouter router) {
            this.title = title;
            this.router = router;
        }
    
        private String title;
        private ExecutorRouter router;
    
        public String getTitle() {
            return title;
        }
        public ExecutorRouter getRouter() {
            return router;
        }
    
        public static ExecutorRouteStrategyEnum match(String name, ExecutorRouteStrategyEnum defaultItem){
            if (name != null) {
                for (ExecutorRouteStrategyEnum item: ExecutorRouteStrategyEnum.values()) {
                    if (item.name().equals(name)) {
                        return item;
                    }
                }
            }
            return defaultItem;
        }
    
    }
    
#### 第一个 ExecutorRouteFirst

直接选集群的第一个
    
    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList){
        return new ReturnT<String>(addressList.get(0));
    }
    
#### 最后一个 ExecutorRouteLast

直接选集群的最后一个

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        return new ReturnT<String>(addressList.get(addressList.size()-1));
    }
    
#### 轮询 ExecutorRouteRound

    private static ConcurrentMap<Integer, Integer> routeCountEachJob = new ConcurrentHashMap<Integer, Integer>();
    // 缓存过期时间戳
    private static long CACHE_VALID_TIME = 0;
    private static int count(int jobId) {
        // cache clear
        // 如果当前的时间，大于缓存的时间，那么说明需要刷新了
        if (System.currentTimeMillis() > CACHE_VALID_TIME) {
            routeCountEachJob.clear();
            // 设置缓存时间戳，默认缓存一天，一天之后会从新开始
            CACHE_VALID_TIME = System.currentTimeMillis() + 1000*60*60*24;
        }

        // count++
        Integer count = routeCountEachJob.get(jobId);
        // count == null 的时候 是第一次进来 
        // 第二次之后  只要 count != null 并且 count < 1000000 就用 count + 1
        count = (count==null || count>1000000)?(new Random().nextInt(100)):++count;  // 初始化时主动Random一次，缓解首次压力
        routeCountEachJob.put(jobId, count);
        return count;
    }

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        //取余的方式可以避免 集中到一台机器 1%3 = 1 2%3 = 2 3 % 3 = 0 4%3 = 1 5% 3= 2 6%3 = 0
        String address = addressList.get(count(triggerParam.getJobId())%addressList.size());
        return new ReturnT<String>(address);
    }
    
#### 随机 ExecutorRouteRandom
    
    private static Random localRandom = new Random();

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        String address = addressList.get(localRandom.nextInt(addressList.size()));
        return new ReturnT<String>(address);
    }
    
#### 一致性hash ExecutorRouteConsistentHash

分组下机器地址相同，不同JOB均匀散列在不同机器上，保证分组下机器分配JOB平均；且每个JOB固定调度其中一台机器

原理:
先构造一个长度为232的整数环（这个环被称为一致性Hash环），根据节点名称的Hash值（其分布为[0, 232-1]）将服务器节点放置在这个Hash环上，
然后根据数据的Key值计算得到其Hash值（其分布也为[0, 232-1]），
接着在Hash环上顺时针查找距离这个Key值的Hash值最近的服务器节点，完成Key到服务器的映射查找。

https://blog.csdn.net/u010412301/article/details/52441400
    
    private static int VIRTUAL_NODE_NUM = 5;
    
    /**
     * get hash code on 2^32 ring (md5散列的方式计算hash值)
     * @param key
     * @return
     */
    private static long hash(String key) {

        // md5 byte
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        byte[] keyBytes = null;
        try {
            keyBytes = key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unknown string :" + key, e);
        }

        md5.update(keyBytes);
        byte[] digest = md5.digest();

        // hash code, Truncate to 32-bits
        long hashCode = ((long) (digest[3] & 0xFF) << 24)
                | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8)
                | (digest[0] & 0xFF);

        long truncateHashCode = hashCode & 0xffffffffL;
        return truncateHashCode;
    }

    public String hashJob(int jobId, List<String> addressList) {

        // ------A1------A2-------A3------
        // -----------J1------------------
        TreeMap<Long, String> addressRing = new TreeMap<Long, String>();
        for (String address: addressList) {
            for (int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                long addressHash = hash("SHARD-" + address + "-NODE-" + i);
                addressRing.put(addressHash, address);
            }
        }

        long jobHash = hash(String.valueOf(jobId));
        SortedMap<Long, String> lastRing = addressRing.tailMap(jobHash);
        if (!lastRing.isEmpty()) {
            return lastRing.get(lastRing.firstKey());
        }
        return addressRing.firstEntry().getValue();
    }

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        String address = hashJob(triggerParam.getJobId(), addressList);
        return new ReturnT<String>(address);
    }
    
#### 最不经常使用 ExecutorRouteLFU
    
    private static ConcurrentMap<Integer, HashMap<String, Integer>> jobLfuMap = new ConcurrentHashMap<Integer, HashMap<String, Integer>>();
    // 缓存时间戳
    private static long CACHE_VALID_TIME = 0;

    public String route(int jobId, List<String> addressList) {

        // cache clear
        if (System.currentTimeMillis() > CACHE_VALID_TIME) {
            jobLfuMap.clear();
            CACHE_VALID_TIME = System.currentTimeMillis() + 1000*60*60*24;
        }

        // lfu item init
        HashMap<String, Integer> lfuItemMap = jobLfuMap.get(jobId);     // Key排序可以用TreeMap+构造入参Compare；Value排序暂时只能通过ArrayList；
        if (lfuItemMap == null) {
            lfuItemMap = new HashMap<String, Integer>();
            jobLfuMap.putIfAbsent(jobId, lfuItemMap);   // 避免重复覆盖
        }

        // put new
        for (String address: addressList) {
            if (!lfuItemMap.containsKey(address) || lfuItemMap.get(address) >1000000 ) {
                lfuItemMap.put(address, new Random().nextInt(addressList.size()));  // 初始化时主动Random一次，缓解首次压力
            }
        }
        // remove old
        List<String> delKeys = new ArrayList<>();
        for (String existKey: lfuItemMap.keySet()) {
            if (!addressList.contains(existKey)) {
                delKeys.add(existKey);
            }
        }
        if (delKeys.size() > 0) {
            for (String delKey: delKeys) {
                lfuItemMap.remove(delKey);
            }
        }

        // load least userd count address
        List<Map.Entry<String, Integer>> lfuItemList = new ArrayList<Map.Entry<String, Integer>>(lfuItemMap.entrySet());
        Collections.sort(lfuItemList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        Map.Entry<String, Integer> addressItem = lfuItemList.get(0);
        String minAddress = addressItem.getKey();
        addressItem.setValue(addressItem.getValue() + 1);

        return addressItem.getKey();
    }

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        String address = route(triggerParam.getJobId(), addressList);
        return new ReturnT<String>(address);
    }
    
#### 最近最久未使用 ExecutorRouteLRU
    
    // 定义个静态的MAP， 用来存储任务ID对应的执行信息
    private static ConcurrentHashMap<Integer, LinkedHashMap<String, String>> jobLRUMap = new ConcurrentHashMap<Integer, LinkedHashMap<String, String>>();
    // 定义过期时间戳
    private static long CACHE_VALID_TIME = 0;
    public String route(int jobId, ArrayList<String> addressList) {
     
        // cache clear
        if (System.currentTimeMillis() > CACHE_VALID_TIME) {
            jobLRUMap.clear();
            //重新设置过期时间，默认为一天
            CACHE_VALID_TIME = System.currentTimeMillis() + 1000*60*60*24;
        }
        // init lru
        LinkedHashMap<String, String> lruItem = jobLRUMap.get(jobId);
        if (lruItem == null) {
            /**
             * LinkedHashMap
             * a、accessOrder：ture=访问顺序排序（get/put时排序）；false=插入顺序排期；
             * b、removeEldestEntry：新增元素时将会调用，返回true时会删除最老元素；可封装LinkedHashMap并重写该方法，比如定义最大容量，超出是返回true即可实现固定长度的LRU算法；
             */
            lruItem = new LinkedHashMap<>(16, 0.75f, true);
            jobLRUMap.put(jobId, lruItem);
        }
     
        // 如果地址列表里面有地址不在map中，此处是可以再次放入，防止添加机器的问题
        for (String address: addressList) {
            if (!lruItem.containsKey(address)) {
                lruItem.put(address, address);
            }
        }
    
        // 取头部的一个元素，也就是最久操作过的数据
        String eldestKey = lruItem.entrySet().iterator().next().getKey();
        String eldestValue = lruItem.get(eldestKey);
        return eldestValue;
    
    }
  
#### 故障转移 ExecutorRouteFailover

遍历集群地址列表，如果失败，则继续调用下一台机器，成功则跳出循环，返回成功信息
    
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
    
        StringBuffer beatResultSB = new StringBuffer();
        for (String address : addressList) {
            // beat
            ReturnT<String> beatResult = null;
            try {
                // 向执行器发送 执行beat信息  ， 试探该机器是否可以正常工作
                ExecutorBiz executorBiz = XxlJobScheduler.getExecutorBiz(address);
                beatResult = executorBiz.beat();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                beatResult = new ReturnT<String>(ReturnT.FAIL_CODE, ""+e );
            }
            // 拼接日志 ， 收集日志信息，后期一起返回
            beatResultSB.append( (beatResultSB.length()>0)?"<br><br>":"")
                    .append(I18nUtil.getString("jobconf_beat") + "：")
                    .append("<br>address：").append(address)
                    .append("<br>code：").append(beatResult.getCode())
                    .append("<br>msg：").append(beatResult.getMsg());

            // beat success
            if (beatResult.getCode() == ReturnT.SUCCESS_CODE) {

                beatResult.setMsg(beatResultSB.toString());
                beatResult.setContent(address);
                return beatResult;
            }
        }
        return new ReturnT<String>(ReturnT.FAIL_CODE, beatResultSB.toString());

    }  
    
#### 忙碌转移 ExecutorRouteBusyover

忙碌转移是向执行器发送消息判断该任务,如果对应的线程正在执行,调用下一台服务器处理

    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        StringBuffer idleBeatResultSB = new StringBuffer();
        for (String address : addressList) {
            // beat
            ReturnT<String> idleBeatResult = null;
            try {
             // 向执行服务器发送消息，判断当前jobId对应的线程是否忙碌
                ExecutorBiz executorBiz = XxlJobScheduler.getExecutorBiz(address);
                idleBeatResult = executorBiz.idleBeat(triggerParam.getJobId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                idleBeatResult = new ReturnT<String>(ReturnT.FAIL_CODE, ""+e );
            }
            idleBeatResultSB.append( (idleBeatResultSB.length()>0)?"<br><br>":"")
                    .append(I18nUtil.getString("jobconf_idleBeat") + "：")
                    .append("<br>address：").append(address)
                    .append("<br>code：").append(idleBeatResult.getCode())
                    .append("<br>msg：").append(idleBeatResult.getMsg());

            // beat success
            // 成功
            if (idleBeatResult.getCode() == ReturnT.SUCCESS_CODE) {
                idleBeatResult.setMsg(idleBeatResultSB.toString());
                idleBeatResult.setContent(address);
                return idleBeatResult;
            }
        }

        return new ReturnT<String>(ReturnT.FAIL_CODE, idleBeatResultSB.toString());
    }
    
    // 如果当前jobId所对应的线程在运行或者在队列了,返回失败,否则返回成功
    public ReturnT<String> idleBeat(int jobId) {

        // isRunningOrHasQueue
        boolean isRunningOrHasQueue = false;
        JobThread jobThread = XxlJobExecutor.loadJobThread(jobId);
        if (jobThread != null && jobThread.isRunningOrHasQueue()) {
            isRunningOrHasQueue = true;
        }

        if (isRunningOrHasQueue) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "job thread is running or has trigger queue.");
        }
        return ReturnT.SUCCESS;
    }

