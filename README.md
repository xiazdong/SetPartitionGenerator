# SetPartitionGenerator

集合划分生成器

参考文章：[Efficient Generation of Set Partitions](http://www.informatik.uni-ulm.de/ni/Lehre/WS03/DMM/Software/partitions.pdf)

实现：

- 生成集合的所有划分
- 生成集合的所有p划分

这里为了简化思路，输入为一个整数n，表示集合 `S={0,...,n-1}`，我们将生成集合S的所有划分。

代码示例：

1、生成集合的所有划分

	SetPartitionGenerator generator = new SetPartitionGenerator(7); //集合中元素为{0,...,6}
	generator.print();//输出所有划分
	generator.dump("output.txt");//将所有划分输出到指定文件
	
2、生成集合的所有p划分

	SetPartitionGenerator generator = new SetPartitionGenerator(7,3); //集合中元素为{0,...,6}，第二个参数3表示生成集合的所有3划分
	generator.print();//输出所有3划分
	generator.dump("output.txt");//将所有3划分输出到指定文件