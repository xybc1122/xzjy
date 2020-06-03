package com.education.project.utils;

public class RecordNoUtils {

    //这里的0，0分别是      * @param workerId 工作ID (0~31)     * @param datacenterId 数据中心ID (0~31)，可以写在配置文件中。
    private static UniqueOrderGenerate idWorker = new UniqueOrderGenerate(0, 0);
    private static RecordNoUtils instance = new RecordNoUtils();

    private RecordNoUtils(){}
    public static RecordNoUtils getInstance() {
        return instance;
    }

    public String getOrderNumber() {
        return  "XZJY"+idWorker.nextId();
    }
}
