import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
    集合划分生成器，实现了：
    (1) 所有集合划分
    (2) 所有集合p划分
    算法时间复杂度是划分的个数，空间复杂度是集合元素的个数
 */
public class SetPartitionGenerator {

    private int n;
    private int[] k;
    private int[] m;
    private int p;

    /**
     * 构造函数
     * @param n 集合中元素的个数
     */
    public SetPartitionGenerator(int n){
        this.n = n;
        this.k = new int[n];
        this.m = new int[n];
    }

    /**
     * 构造函数
     * @param n 集合中元素的个数
     * @param p p划分
     */
    public SetPartitionGenerator(int n,int p){
        this(n);
        this.p = p;
    }

    /**
     * 重置到初始状态
     */
    private void reset(){
        for(int i=0;i<n;i++){
            k[i] = 0;
            m[i] = 0;
        }
    }

    /**
     * 重置到p划分的初始状态
     */
    private void resetP(){
        reset();
        for(int i=n-p+1;i<n;i++){
            k[i] = m[i] = i - (n - p);
        }
    }

    /**
     * 下一个划分
     * @return
     *      true  下一个划分存在
     *      false 下一个划分不存在，即列举完了
     */
    private boolean next(){
        for(int i = n - 1; i > 0; i--){
            if(k[i] <= m[i-1]){
                k[i]++;
                m[i] = Math.max(m[i],k[i]);
                for(int j = i + 1; j < n; j++){
                    k[j] = k[0];
                    m[j] = m[i];
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 下一个p划分
     * @return
     *      true  下一个p划分存在
     *      false 下一个p划分不存在，即列举完了
     */
    private boolean nextP(){
        for(int i = n - 1; i > 0; i--){
            if(k[i] < p-1 && k[i] <= m[i-1]){
                k[i]++;
                m[i] = Math.max(m[i],k[i]);
                for(int j = i + 1; j < n; j++){
                    k[j] = 0;
                    m[j] = m[i];
                }
                for(int j=n-(p-m[i])+1;j<n;j++){
                    k[j] = m[j] = p - (n - j);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 输出当前的划分
     */
    private void printCurrentPartition(){
        for(int i=0;i<n;i++){
            System.out.print(k[i]);
            if(i < n-1){
                System.out.print(",");
            }
        }
        System.out.println();
    }

    /**
     * 将当前划分输出到 writer
     * @param writer 输出流
     */
    private void dumpCurrentPartition(PrintWriter writer){
        for(int i=0;i<n;i++){
            writer.print(k[i]);
            if(i < n-1){
                writer.print(",");
            }
        }
        writer.println();
    }

    /**
     * 输出全部的划分
     */
    public void print(){
        if(p == 0) {
            reset();
            do {
                printCurrentPartition();
            } while (next());
        }
        else{
            resetP();
            do{
                printCurrentPartition();
            }while(nextP());
        }
    }

    /**
     * 警告：如果 n 设置的过大，运行时间会很长，文件可能会很大。
     * @param file 文件名
     */
    public void dump(String file){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(file), true);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(p == 0) {
            reset();
            do {
                dumpCurrentPartition(writer);
            } while (next());
        }
        else{
            resetP();
            do{
                dumpCurrentPartition(writer);
            }while(nextP());
        }
        writer.close();
    }

    public static void main(String[] args){
        SetPartitionGenerator generator = new SetPartitionGenerator(7,3);
        generator.print();
    }
}
