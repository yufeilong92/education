package com.xuechuan.education.net;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: project
 * @Package com.xuechuan.myapplication.uitls
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/3 8:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class Net {
    private <T extends Comparable<? super T>> void insertSort(T[] a) {
        for (int p = 1; p < a.length; p++) {
            T tmp = a[p];//保存当前位置p的元素，其中[0,p-1]已经有序
            int j;
            for (j = p; j > 0 && tmp.compareTo(a[j - 1]) < 0; j--) {
                a[j] = a[j - 1];//后移一位
            }
            a[j] = tmp;//插入到合适的位置
        }
    }

    /***
     * 得到升序数据
     * @param arr 排序的数组
     * @return
     */
    public CharSequence getInsertSortData(Integer[] arr) {
        insertSort(arr);
        StringBuffer b = new StringBuffer();

        for (Integer i : arr) {
            b.append(i);
        }
        return b.toString();
    }
}
