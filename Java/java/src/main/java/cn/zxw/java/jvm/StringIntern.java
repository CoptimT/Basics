package cn.zxw.java.jvm;

public class StringIntern {
	/**
	  1.直接定义字符串变量的时候赋值，如果表达式右边只有字符串常量，那么就是把变量存放在常量池里面。
	　2.new出来的字符串是存放在堆里面。
	　3.对字符串进行拼接操作，也就是做"+"运算的时候，分2中情况：
	　　　i.表达式右边是纯字符串常量，那么存放在栈里面。
　　          ii.表达式右边如果存在字符串引用，也就是字符串对象的句柄，那么就存放在堆里面。
	 */
	public static void main(String[] args) {
		String str1 = "aaa";  //常量池
        String str2 = "bbb";  //常量池
        String str3 = "aaabbb"; //常量池
        String str4 = str1 + str2; //堆
        String str5 = "aaa" + "bbb"; //常量池
        String str6 = new String("aaabbb"); //堆
        
        System.out.println(str3 == str4); // false
        System.out.println(str3 == str4.intern()); // true
        System.out.println(str3 == str5);// true
        
        System.out.println(str3 == str6);// false
        System.out.println(str4 == str6);// false
	}

}
