package wj.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author wangjing
 * Date 2019/7/30
 * Description
 */
public class ThreadPoolUtil {
    /*
    *   全局使用的线程池 线程数量固定
    *   线程空闲时不会被回收
    *   当所有线程处于活动状态时 新的任务将处于等待状态
    * */

    private static ExecutorService fixedThreadPool = null;
    // 双重检查
    public static ExecutorService getInstance() {
        if (fixedThreadPool == null) {
            synchronized (ExecutorService.class) {
                if (fixedThreadPool == null) {
                    fixedThreadPool = Executors.newFixedThreadPool(2);
                }
            }
        }
        return fixedThreadPool;
    }
}
