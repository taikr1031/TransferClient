import demo.CommerceToTransferAccountsTest;
import demo.RegisterTest;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

public class TestSuite {

    private final static int SLEEP_TIME = 200;

    private final static int TASK_SIZE = 3;

    private final static Map<String, Callable<Object>> TASK_MAP = new ConcurrentHashMap<String, Callable<Object>>();

    static {
        TASK_MAP.put("REGISTER", new RegisterTest());
        TASK_MAP.put("TRANSFER", new CommerceToTransferAccountsTest());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("----程序开始运行----");
        Date date1 = new Date();
        // 创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(TASK_SIZE);
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<Future>();
        for (int i = 1; i < TASK_SIZE + 1; i++) {
            System.out.println("----线程【" + i + "】开始运行： " + getSystemTimeString());
            Callable c = TASK_MAP.get("TRANSFER");
            // 执行任务并获取Future对象
            Future f = pool.submit(c);
            System.out.println(">>>" + f.get().toString());
            list.add(f);

            Thread.sleep(SLEEP_TIME);
        }
        // 关闭线程池
        pool.shutdown();

        // 获取所有并发任务的运行结果
        for (Future f : list) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            System.out.println(">>>" + f.get().toString());
        }
        Date date2 = new Date();
        System.out.println("----程序结束运行----，程序运行时间【"
                + (date2.getTime() - date1.getTime()) + "毫秒】");
    }

    private static String getSystemTimeString() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(today);
    }
}
