import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private List<List<Integer>> partition;
    private String formatedPartition;

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
     * 获得当前划分的划分大小
     */
    private int getCurrentPartitionSize(){
        int max = 0;
        for(int i=1;i<n;i++){
            if(max < k[i]){
                max = k[i];
            }
        }
        return max + 1;
    }

    /**
     * 将k[]转化成集合划分 partition 属性
     */
    private void k2partiton(){
        int size = getCurrentPartitionSize();
        partition = new ArrayList<List<Integer>>(size);
        for(int i=0;i<size;i++){
            partition.add(new ArrayList<Integer>());
        }
        for(int i=0;i<n;i++){
            partition.get(k[i]).add(i);
        }
    }

    /**
     * 将partition属性转化成人看得懂的字符串 formatedPartition
     */
    private void formatCurrentPartition(){
        k2partiton();
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for(int i=0;i<partition.size();i++){
            builder.append("{");
            List<Integer> block = partition.get(i);
            for(int j=0;j<block.size();j++){
                builder.append(block.get(j));
                if(j < block.size() - 1){
                    builder.append(",");
                }
            }
            builder.append("}");
            if(i < partition.size() - 1){
                builder.append(",");
            }
        }
        builder.append("}");
        formatedPartition = builder.toString();
    }

    /**
     * 输出当前的划分
     */
    private void printCurrentPartition(){
        formatCurrentPartition();
        System.out.println(formatedPartition);
    }

    /**
     * 将当前划分输出到 writer
     * @param writer 输出流
     */
    private void dumpCurrentPartition(PrintWriter writer){
        formatCurrentPartition();
        writer.println(formatedPartition);
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
        SetPartitionGenerator generator = new SetPartitionGenerator(5,3);
        generator.dump("output.txt");
    }
}
