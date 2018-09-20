package com.pancm.utils;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;


/**
* @Title: IPWhiteCheck
* @Description:
* IP白名单工具类 
* @Version:1.0.0  
* @author pancm
* @date 2018年6月25日
*/
public class IPWhiteCheck {

	private static String VERTICAL="\\|";

    // IP的正则
    private static Pattern pattern = Pattern
            .compile("(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
                    + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
                    + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
                    + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})");

    
    private static Set<String> getAvaliIpList(String allowIp) {

        Set<String> ipList = new HashSet<String>();
        for (String allow : allowIp.replaceAll("\\s", "").split(";")) {
            if (allow.indexOf("*") > -1) {
                String[] ips = allow.split("\\.");
                String[] from = new String[] { "0", "0", "0", "0" };
                String[] end = new String[] { "255", "255", "255", "255" };
                List<String> tem = new ArrayList<String>();
                for (int i = 0; i < ips.length; i++)
                    if (ips[i].indexOf("*") > -1) {
                        tem = complete(ips[i]);
                        from[i] = null;
                        end[i] = null;
                    } else {
                        from[i] = ips[i];
                        end[i] = ips[i];
                    }

                StringBuffer fromIP = new StringBuffer();
                StringBuffer endIP = new StringBuffer();
                for (int i = 0; i < 4; i++)
                    if (from[i] != null) {
                        fromIP.append(from[i]).append(".");
                        endIP.append(end[i]).append(".");
                    } else {
                        fromIP.append("[*].");
                        endIP.append("[*].");
                    }
                fromIP.deleteCharAt(fromIP.length() - 1);
                endIP.deleteCharAt(endIP.length() - 1);

                for (String s : tem) {
                    String ip = fromIP.toString().replace("[*]",
                            s.split(";")[0])
                            + "-"
                            + endIP.toString().replace("[*]", s.split(";")[1]);
                    if (validate(ip)) {
                        ipList.add(ip);
                    }
                }
            } else {
                if (validate(allow)) {
                    ipList.add(allow);
                }
            }
        }

        return ipList;
    }

    /**
     * 对单个IP节点进行范围限定
     * 
     * @param arg
     * @return 返回限定后的IP范围，格式为List[10;19, 100;199]
     */
    private static List<String> complete(String arg) {
        List<String> com = new ArrayList<String>();
        if (arg.length() == 1) {
            com.add("0;255");
        } else if (arg.length() == 2) {
            String s1 = complete(arg, 1);
            if (s1 != null)
                com.add(s1);
            String s2 = complete(arg, 2);
            if (s2 != null)
                com.add(s2);
        } else {
            String s1 = complete(arg, 1);
            if (s1 != null)
                com.add(s1);
        }
        return com;
    }

    private static String complete(String arg, int length) {
        String from = "";
        String end = "";
        if (length == 1) {
            from = arg.replace("*", "0");
            end = arg.replace("*", "9");
        } else {
            from = arg.replace("*", "00");
            end = arg.replace("*", "99");
        }
        if (Integer.valueOf(from) > 255)
            return null;
        if (Integer.valueOf(end) > 255)
            end = "255";
        return from + ";" + end;
    }

    /**
     * 在添加至白名单时进行格式校验
     * @param ip
     * @return
     */
    private static boolean validate(String ip) {
        for (String s : ip.split("-"))
            if (!pattern.matcher(s).matches()) {
                return false;
            }
        return true;
    }

    /**
     * 
     * checkLoginIP:(根据IP,及可用Ip列表来判断ip是否包含在白名单之中).
     * @param ip
     * @param ipList
     * @return
     */
    private static boolean checkLoginIP(String ip, Set<String> ipList) {
        if (ipList.isEmpty() || ipList.contains(ip))
            return true;
        else {
            for (String allow : ipList) {
                if (allow.indexOf("-") > -1) {
                    String[] from = allow.split("-")[0].split("\\.");
                    String[] end = allow.split("-")[1].split("\\.");
                    String[] tag = ip.split("\\.");

                    // 对IP从左到右进行逐段匹配
                    boolean check = true;
                    for (int i = 0; i < 4; i++) {
                        int s = Integer.valueOf(from[i]);
                        int t = Integer.valueOf(tag[i]);
                        int e = Integer.valueOf(end[i]);
                        if (!(s <= t && t <= e)) {
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
     /**
      * 根据IP地址，及IP白名单设置规则判断IP是否包含在白名单
      * 例如:ip =192.169.0.10
      * ipWhiteConfig=192.169.0.1-192.169.0.11;
      * 则可以通过
      * ip =192.169.1.10
      * ipWhiteConfig=192.169.1.*
      * 也可以通过
      * @param ip
      * @param ipWhiteConfig
      * @return
      */
    public static boolean checkLoginIP(String ip,String ipWhiteConfig){
    	if(ip==null||ipWhiteConfig==null){
 		   return false;
 	   }
        Set<String> ipList = getAvaliIpList(ipWhiteConfig);
        return checkLoginIP(ip, ipList);
    }
    
    /**
     * 支持多个
     * 根据IP地址，及IP白名单设置规则判断IP是否包含在白名单
     * 例如:ip =192.169.0.10
     * ipWhiteConfig=192.169.1.*|192.169.0.1-192.169.0.11;
     * 则可以通过
     * ip =192.169.0.12
     * ipWhiteConfig=192.169.1.*|192.169.0.1-192.169.0.11
     * 不可以通过
     * @param ip
     * @param ipWhiteConfig
     * @return
     */
   public static boolean checkLoginIPS(String ip,String ipWhiteConfig){
	   if(ip==null||ipWhiteConfig==null){
		   return false;
	   }
	   String []ips=ipWhiteConfig.split(VERTICAL);
		boolean falg=false;
		for(String i:ips){
			falg=checkLoginIP(ip, i);
			if(falg){
				break;
			}
		}
       return falg;
   }
   
   
   public static void main(String[] args) {
	   String ip="192.169.0.10";
		String ipWhiteConfig="192.169.0.1-192.169.0.11";
		String ip2="192.169.0.10";
		String ipWhiteConfig2="192.169.1.*";
		String ipWhiteConfig3="192.169.1.*|192.169.0.1-192.169.0.11";
		String []ips=ipWhiteConfig3.split("\\|");
		boolean falg=false;
		for(String i:ips){
			falg=IPWhiteCheck.checkLoginIP(ip2, i);
			if(falg){
				break;
			}
		}
		System.out.println("是否通过1："+falg);
		
		System.out.println("是否通过2："+IPWhiteCheck.checkLoginIP(ip, ipWhiteConfig));
		System.out.println("是否通过3："+IPWhiteCheck.checkLoginIP(ip2, ipWhiteConfig2));
}
   
}
