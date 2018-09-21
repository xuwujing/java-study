package com.pancm.arithmetic;

import java.util.Scanner;

/**
 * @Title: PatternMatchingTest
 * @Description: 模式匹配算法
 * 来源:http://www.cnblogs.com/jiaohanhan/p/6654874.html
 * @Version:1.0.0
 * @author pancm
 * @date 2018年9月14日
 */
public class PatternMatchingTest {

	
	
	/**
	 * 暴力匹配
	 * 　时间复杂度为O(n*m)；n为主串长度，m为模式串长度
　　		算法的基本思想：
　　　　　　从主串的起始位置（或指定位置）开始与模式串的第一个字符比较，若相等，则继续逐个比较后续字符；
                       否则从主串的下一个字符再重新和模式串的字符比较。
                       依次类推，直到模式串成功匹配，返回主串中第一次出现模式串字符的位置，或者模式串匹配不成功，这里约定返回-1；
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static int bruteForceStringMatch(String source, String pattern) {
		int slen = source.length();
		int plen = pattern.length();
		char[] s = source.toCharArray();
		char[] p = pattern.toCharArray();
		int i = 0;
		int j = 0;

		if (slen < plen)
			return -1; // 如果主串长度小于模式串，直接返回-1，匹配失败
		else {
			while (i < slen && j < plen) {
				if (s[i] == p[j]) // 如果i,j位置上的字符匹配成功就继续向后匹配
				{
					++i;
					++j;
				} else {
					i = i - (j - 1); // i回溯到主串上一次开始匹配下一个位置的地方
					j = 0; // j重置，模式串从开始再次进行匹配
				}
			}
			if (j == plen) // 匹配成功
				return i - j;
			else
				return -1; // 匹配失败
		}
	}

	
	
	
	
	/**
	 * KMP算法
	　　KMP算法是D.E.Knuth、V.R.Pratt和J.H.Morris同时发现，所以命名为KMP算法。
	　　此算法可以在O(n+m)的时间数量级上完成串的模式匹配。
	　　主要就是改进了暴力匹配中i回溯的操作，KMP算法中当一趟匹配过程中出现字符比较不等时，
	不直接回溯i，而是利用已经得到的“部分匹配”的结果将模式串向右移动（j-next[k]）的距离。
	
	* @param source
	 * @param pattern
	 * @return
	 */
	public static int kmpStringMatch(String source, String pattern)
    {
        int i = 0;
        int j = 0;
        char[] s = source.toCharArray();
        char[] p = pattern.toCharArray();
        int slen = s.length;
        int plen = p.length;
        int[] next = getNext(p);
        while(i < slen && j < plen)
        {
            if(j == -1 || s[i] == p[j])
            {
                ++i;
                ++j;
            }
            else
            {
                //如果j != -1且当前字符匹配失败，则令i不变，
                //j = next[j],即让pattern模式串右移j - next[j]个单位
                j = next[j];
            }
        }
        
        
        if(j == plen)
            return i - j;
        else
            return -1;
    }
    
	
	/**
	 * 关于next[k]数组的计算引出的两种办法，一种是递归，一种对递归优化，第一种对应的就是KMP算法，第二种就是优化的KMP算法。
		next函数值仅取决于模式串本身而和主串无关。	
		有很多讲next函数值计算办法的资料，在此我想用一种直观的比较容易理解的办法来表达。	
		举个栗子：现在有一个模式串abab
		
		    模式串的各个字串　   	                     前缀                       	                    后缀                    	最大公共元素长度
		a	null	null	0
		ab	a	b	0
		aba	a,ab	a,ba	1
		abab	a,ab,aba	b,ab,bab	2
	 * @param p
	 * @return
	 */
    private static int[] getNext(char[] p)
    {
        /**
         * 已知next[j] = k, 利用递归的思想求出next[j+1]的值
         * 1.如果p[j] = p[k]，则next[j+1] = next[k] + 1;
         * 2.如果p[j] != p[k],则令k = next[k],如果此时p[j] == p[k],则next[j+1] = k+1
         * 如果不相等，则继续递归前缀索引，令k=next[k],继续判断，直至k=-1(即k=next[0])或者p[j]=p[k]为止
         */
        int plen = p.length;
        int[] next = new int[plen];
        int k = -1;
        int j = 0;
        next[0] = -1;                //这里采用-1做标识
        while(j < plen -1)
        {
            if(k == -1 || p[j] == p[k])
            {
                ++k;
                ++j;
                next[j] = k;
            }
            else
            {
                k = next[k];
            }
        }
        System.out.println("next函数值：");
        for(int ii = 0;ii<next.length;ii++)
        {
            
            System.out.print(next[ii]+ " ");
        }
        System.out.println();
        return next;
    }
    
    
    
    @SuppressWarnings("resource")
	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();
        String b = sc.nextLine();
        System.out.println(bruteForceStringMatch(a, b));
        System.out.println(kmpStringMatch(a, b));
    }
}
