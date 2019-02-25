package com.xf.wxf.realizationofmathematicalmodel.combination;

import java.util.ArrayList;
import java.util.List;

/**
 *  组合的实现
 */
public class Combination {
    /**
     *  该方法描述为长度为n的数组中取出m个元素一共有多少种组合
     *   相当于数学中的组合数的暴力解法 目前没有发现bug
     * @param n 数组长度
     * @param m 要取出的元素数
     * @return n中取m个元素的所有组合的集合
     */
    public static List<byte[]> CombinatorialNumber(int n, int m) {
        /**
         * 异常情况 自己定义异常种类
         */
        if (n==0){
            throw new RuntimeException();
        }
        java.util.ArrayList<byte[]> resultList=new ArrayList<>();
        /**
         *  数组长度为1的时候直接返回
         */
        if (n==1){
            byte[] bytes = {1};
            resultList.add(bytes);
            return  resultList;
        }
        if (m==0){
            throw new RuntimeException();
        }


        /**
         * 初始化返回的list
         */
        /**
         * 组合的二进制数组
         */
        byte[] tempNum=new byte[n];
        /**
         * 退出do  while 循环的条件布尔值
         */
        boolean flag;
        /**
         * 对二进制数组进行赋值 （类似【1,1,1,1,0,0,0】）
         *  将前m（不包括m）个元素赋值为1 之后赋值为0；得到第一种组合
         */
        for(int i=0;i<n;i++){
            if(i<m){
                tempNum[i]=1;
            }else{
                tempNum[i]=0;
            }
        }
        /**
         * 复制该数组 并保存
         */
        byte[] result=tempNum.clone();
        resultList.add(result);

        /**
         * m>=n时意味着只有一种组合 直接返回
         */
        if(m>=n)return resultList;


        /**
         * index当前10相邻出现的下标
         */
        int index=0;
        int sum;
        do{
            flag=true;
            sum=0;
            for(int i=0;i<n-1;i++){
                if(tempNum[i]==1&&tempNum[i+1]==0){//出现1 0相邻
                    //值交换为0 1
                    tempNum[i]=0;
                    tempNum[i+1]=1;
                    index=i;
                    flag=true;
                    break;
                }else {
                    flag=false;
                }
            }
            /**
             * 计算当前index下标前的1的个数
             */
            for (int i=0;i<index;i++){
                if(tempNum[i]==1){
                    sum++;
                }
            }
            /**
             * 将当前出现index位置前的所有的1移动到最左边
             */
            for (int i=0;i<index;i++){
                if (i<sum){
                    tempNum[i]=1;
                }else {
                    tempNum[i]=0;
                }
            }
            /**
             * 复制组合数组
             */
            if (flag){
                byte[] result1=tempNum.clone();
                resultList.add(result1);
            }

        }while(flag);
        return resultList;

    }






    public static List<byte[]> CombinatorialNumber(List ts, int m){
        return CombinatorialNumber(ts.size(),m);
    }
    public static List<byte[]> CombinatorialNumber(Object[] ts, int m){
        return CombinatorialNumber(ts.length,m);
    }
    public static List<byte[]> CombinatorialNumber(int[] ts, int m){
        return CombinatorialNumber(ts.length,m);
    }
    public static List<byte[]> CombinatorialNumber(byte[] ts, int m){
        return CombinatorialNumber(ts.length,m);
    }
    public static List<byte[]> CombinatorialNumber(long[] ts, int m){
        return CombinatorialNumber(ts.length,m);
    }
    public static List<byte[]> CombinatorialNumber(short[] ts, int m){
        return CombinatorialNumber(ts.length,m);
    }
    public static List<byte[]> CombinatorialNumber(boolean[] ts, int m){
        return CombinatorialNumber(ts.length,m);
    }
    public static List<byte[]> CombinatorialNumber(double[] ts, int m){
        return CombinatorialNumber(ts.length,m);
    }
    public static List<byte[]> CombinatorialNumber(float[] ts, int m){
        return CombinatorialNumber(ts.length,m);
    }
    public static List<byte[]> CombinatorialNumber(char[] ts, int m){
        return CombinatorialNumber(ts.length,m);
    }

    /**
     * 重载方法 数量的返回
     * @param ts
     * @param m
     * @return
     */
    public static int ComNumbers(Object[] ts, int m){
        return CombinatorialNumber(ts.length,m).size();
    }
    public static int ComNumbers(int[] ts, int m){
        return CombinatorialNumber(ts.length,m).size();
    }
    public static int ComNumbers(short[] ts, int m){
        return CombinatorialNumber(ts.length,m).size();
    }
    public static int ComNumbers(long[] ts, int m){
        return CombinatorialNumber(ts.length,m).size();
    }
    public static int ComNumbers(float[] ts, int m){
        return CombinatorialNumber(ts.length,m).size();
    }
    public static int ComNumbers(double[] ts, int m){
        return CombinatorialNumber(ts.length,m).size();
    }
    public static int ComNumbers(char[] ts, int m){
        return CombinatorialNumber(ts.length,m).size();
    }
    public static int ComNumbers(boolean[] ts, int m){
        return CombinatorialNumber(ts.length,m).size();
    }
}
