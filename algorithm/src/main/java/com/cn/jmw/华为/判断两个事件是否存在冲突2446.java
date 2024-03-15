package com.cn.jmw.华为;

public class 判断两个事件是否存在冲突2446 {

    public boolean haveConflict(String[] event1, String[] event2) {
        String[] MIN = null, MAX = null;
        if (Integer.parseInt(event1[0].substring(0, 2)) < Integer.parseInt(event2[0].substring(0, 2))) {
            MIN = event1;
            MAX = event2;
        } else if (Integer.parseInt(event1[0].substring(0, 2)) == Integer.parseInt(event2[0].substring(0, 2)) &&
                Integer.parseInt(event1[1].substring(3, 5)) < Integer.parseInt(event2[0].substring(3, 5))) {
            MIN = event1;
            MAX = event2;
        } else {
            MIN = event2;
            MAX = event1;
        }

        if (Integer.parseInt(MIN[1].substring(0, 2)) < Integer.parseInt(MAX[0].substring(0, 2))) {
            return false;
        } else if (Integer.parseInt(MIN[1].substring(0, 2)) == Integer.parseInt(MAX[0].substring(0, 2))) {
            if (Integer.parseInt(MIN[1].substring(3, 5)) < Integer.parseInt(MAX[0].substring(3, 5))) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public boolean haveConflict2(String[] event1, String[] event2) {
        return !(event1[1].compareTo(event2[0]) < 0 || event2[1].compareTo(event1[0]) < 0);
    }

    public static void main(String[] args) {
        //测试1
        String[] event1 = new String[]{"01:04", "01:17"};
        String[] event2 = new String[]{"01:57", "18:48"};

        String[] event3 = new String[]{"01:00", "02:00"};
        String[] event4 = new String[]{"01:20", "03:00"};

        String[] event5 = new String[]{"10:00", "11:00"};
        String[] event6 = new String[]{"14:00", "15:00"};

        判断两个事件是否存在冲突2446 判断两个事件是否存在冲突2446 = new 判断两个事件是否存在冲突2446();
        System.out.println(判断两个事件是否存在冲突2446.haveConflict(event1, event2));
        System.out.println(判断两个事件是否存在冲突2446.haveConflict(event3, event4));
        System.out.println(判断两个事件是否存在冲突2446.haveConflict(event5, event6));
    }
}
