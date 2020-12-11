package nohi.test.sm4_1538;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-07-16 15:36
 **/
public class SM4_Context {
    public int mode;

    public int[] sk;

    public boolean isPadding;

    public SM4_Context() {
        this.mode = 1;
        this.isPadding = true;
        this.sk = new int[32];
    }
}
