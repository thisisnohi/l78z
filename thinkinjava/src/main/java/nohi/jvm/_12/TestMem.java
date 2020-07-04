package nohi.jvm._12;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-04-21 19:51
 **/
public class TestMem {

    public static void main(String[] args){
        System.out.println("===============");
        System.out.println("totalMemory:" + Runtime.getRuntime().totalMemory());
        System.out.println("freeMemory:" + Runtime.getRuntime().freeMemory());
        System.out.println("maxMemory:" + Runtime.getRuntime().maxMemory());

        byte[] _1m = new byte[1 * 1024 * 1024];
        System.out.println("====== 1 ========");
        System.out.println("totalMemory:" + Runtime.getRuntime().totalMemory());
        System.out.println("freeMemory:" + Runtime.getRuntime().freeMemory());
        System.out.println("maxMemory:" + Runtime.getRuntime().maxMemory());

        byte[] _5m = new byte[5 * 1024 * 1024];
        System.out.println("====== 5 ========");
        System.out.println("totalMemory:" + Runtime.getRuntime().totalMemory());
        System.out.println("freeMemory:" + Runtime.getRuntime().freeMemory());
        System.out.println("maxMemory:" + Runtime.getRuntime().maxMemory());

        byte[] _15m = new byte[15 * 1024 * 1024];
        System.out.println("====== 5 ========");
        System.out.println("totalMemory:" + Runtime.getRuntime().totalMemory());
        System.out.println("freeMemory:" + Runtime.getRuntime().freeMemory());
        System.out.println("maxMemory:" + Runtime.getRuntime().maxMemory());

    }
}
