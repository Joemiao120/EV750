package com.huidisen.ep750.utils;

/**
 * Created by miaoyichao on 16/6/13.
 */
public class TaskUtils {

    public static String toNameByType(String type) {
        String name = null;
        switch (type) {
            case Commondata.TASKTYPE_BAIDU:
                name = "摆渡";
                break;
            case Commondata.TASKTYPE_QUANSHUN:
                name = "全顺";
                break;
            case Commondata.TASKTYPE_DAKETI:
                name = "搭客梯";
                break;
            case Commondata.TASKTYPE_CANJIRENBAOZHANG:
                name = "残疾人保障车";
                break;
            case Commondata.TASKTYPE_CHEKETI:
                name = "撤客梯";
                break;
            case Commondata.TASKTYPE_YINDAO:
                name = "引导";
                break;
            case Commondata.TASKTYPE_QIANYIN:
                name = "牵引";
                break;
            case Commondata.TASKTYPE_DIANYUAN:
                name = "电源";
                break;
            case Commondata.TASKTYPE_QIYUAN:
                name = "气源";
                break;
            case Commondata.TASKTYPE_KONGTIAO:
                name = "空调";
                break;
            case Commondata.TASKTYPE_QINGSHUI:
                name = "清水";
                break;
            case Commondata.TASKTYPE_WUSHUI:
                name = "污水";
                break;
            case Commondata.TASKTYPE_LAJI:
                name = "垃圾";
                break;
            case Commondata.TASKTYPE_CHUBING:
                name = "除冰";
                break;
            case Commondata.TASKTYPE_DIQIN_IN:
                name = "进港地勤";
                break;
            case Commondata.TASKTYPE_DIQIN_OUT:
                name = "出港地勤";
                break;
            case Commondata.TASKTYPE_DENGJIQIAO_IN:
                name = "进港登机桥";
                break;
            case Commondata.TASKTYPE_DENGJIQIAO_OUT:
                name = "出港登机桥";
                break;
            case Commondata.TASKTYPE_XINGLIQIANYIN:
                name = "行李牵引";
                break;
            case Commondata.TASKTYPE_CHUANSONGDAI:
                name = "传送带";
                break;
            case Commondata.TASKTYPE_ZHUANGXIEDUI:
                name = "装卸队";
                break;
        }
        return name;
    }

    public static String getNextNode(String taskName, String currentNode) {
        String node = null;
        switch (taskName) {
            case Commondata.TASKTYPE_BAIDU:  //摆渡
            case Commondata.TASKTYPE_CANJIRENBAOZHANG://残疾人保障车
            case Commondata.TASKTYPE_QUANSHUN:// 全顺
            case Commondata.TASKTYPE_YINDAO:
            case Commondata.TASKTYPE_DIANYUAN:
            case Commondata.TASKTYPE_QIYUAN:
            case Commondata.TASKTYPE_KONGTIAO:
            case Commondata.TASKTYPE_QINGSHUI:
            case Commondata.TASKTYPE_WUSHUI:
            case Commondata.TASKTYPE_LAJI:
            case Commondata.TASKTYPE_CHUBING:
            case Commondata.TASKTYPE_XINGLIQIANYIN:
            case Commondata.TASKTYPE_CHUANSONGDAI: {
                switch (currentNode) {
                    case Commondata.TASKNODE_DJS:
                        node = Commondata.TASKNODE_SJJS;
                        break;
                    case Commondata.TASKNODE_SJJS:
                        node = Commondata.TASKNODE_RCBD;
                        break;
                    case Commondata.TASKNODE_RCBD:
                        node = Commondata.TASKNODE_DW;
                        break;
                    case Commondata.TASKNODE_DW:
                        node = Commondata.TASKNODE_KS;
                        break;
                    case Commondata.TASKNODE_KS:
                        node = Commondata.TASKNODE_WC;
                        break;
                    case Commondata.TASKNODE_WC:
                        node = Commondata.TASKNODE_RWWC;
                        break;
                }
            }
            break;
            case Commondata.TASKTYPE_DAKETI://搭客梯
                switch (currentNode) {
                    case Commondata.TASKNODE_DJS:
                        node = Commondata.TASKNODE_SJJS;
                        break;
                    case Commondata.TASKNODE_SJJS:
                        node = Commondata.TASKNODE_RCBD;
                        break;
                    case Commondata.TASKNODE_RCBD:
                        node = Commondata.TASKNODE_DW;
                        break;
                    case Commondata.TASKNODE_DW:
                        node = Commondata.TASKNODE_KJWC;
                        break;
                    case Commondata.TASKNODE_KJWC:
                        node = Commondata.TASKNODE_WC;
                        break;
                    case Commondata.TASKNODE_WC:
                        node = Commondata.TASKNODE_RWWC;
                        break;
                }
                break;
            case Commondata.TASKTYPE_CHEKETI://撤客梯
                switch (currentNode) {
                    case Commondata.TASKNODE_DJS:
                        node = Commondata.TASKNODE_SJJS;
                        break;
                    case Commondata.TASKNODE_SJJS:
                        node = Commondata.TASKNODE_CMGB;
                        break;
                    case Commondata.TASKNODE_CMGB:
                        node = Commondata.TASKNODE_WC;
                        break;
                    case Commondata.TASKNODE_WC:
                        node = Commondata.TASKNODE_RWWC;
                        break;
                }
                break;

            case Commondata.TASKTYPE_QIANYIN://牵引
                switch (currentNode) {
                    case Commondata.TASKNODE_DJS:
                        node = Commondata.TASKNODE_SJJS;
                        break;
                    case Commondata.TASKNODE_SJJS:
                        node = Commondata.TASKNODE_RCBD;
                        break;
                    case Commondata.TASKNODE_RCBD:
                        node = Commondata.TASKNODE_DW;
                        break;
                    case Commondata.TASKNODE_DW:
                        node = Commondata.TASKNODE_DJWC;
                        break;
                    case Commondata.TASKNODE_DJWC:
                        node = Commondata.TASKNODE_KS;
                        break;
                    case Commondata.TASKNODE_KS:
                        node = Commondata.TASKNODE_WC;
                        break;
                    case Commondata.TASKNODE_WC:
                        node = Commondata.TASKNODE_RWWC;
                        break;
                }
                break;

            case Commondata.TASKTYPE_DIQIN_IN://进港地勤
                switch (currentNode) {
                    case Commondata.TASKNODE_DJS:
                        node = Commondata.TASKNODE_JS;
                        break;
                    case Commondata.TASKNODE_JS:
                        node = Commondata.TASKNODE_DW;
                        break;
                    case Commondata.TASKNODE_DW:
                        node = Commondata.TASKNODE_WC;
                        break;
                    case Commondata.TASKNODE_WC:
                        node = Commondata.TASKNODE_RWWC;
                        break;
                }
                break;
            case Commondata.TASKTYPE_DIQIN_OUT://出港地勤
                switch (currentNode) {
                    case Commondata.TASKNODE_DJS:
                        node = Commondata.TASKNODE_JS;
                        break;
                    case Commondata.TASKNODE_JS:
                        node = Commondata.TASKNODE_CLDWC;
                        break;
                    case Commondata.TASKNODE_CLDWC:
                        node = Commondata.TASKNODE_WC;
                        break;
                    case Commondata.TASKNODE_WC:
                        node = Commondata.TASKNODE_RWWC;
                        break;
                }
                break;
            case Commondata.TASKTYPE_DENGJIQIAO_IN://进港登机桥
                switch (currentNode) {
                    case Commondata.TASKNODE_DJS:
                        node = Commondata.TASKNODE_JS;
                        break;
                    case Commondata.TASKNODE_JS:
                        node = Commondata.TASKNODE_DJWC;
                        break;
                    case Commondata.TASKNODE_DJWC:
                        node = Commondata.TASKNODE_WC;
                        break;
                    case Commondata.TASKNODE_WC:
                        node = Commondata.TASKNODE_RWWC;
                        break;
                }
                break;
            case Commondata.TASKTYPE_DENGJIQIAO_OUT://出港登机桥
                switch (currentNode) {
                    case Commondata.TASKNODE_DJS:
                        node = Commondata.TASKNODE_JS;
                        break;
                    case Commondata.TASKNODE_JS:
                        node = Commondata.TASKNODE_KCMGB;
                        break;
                    case Commondata.TASKNODE_KCMGB:
                        node = Commondata.TASKNODE_WC;
                        break;
                    case Commondata.TASKNODE_WC:
                        node = Commondata.TASKNODE_RWWC;
                        break;
                }
                break;

            case Commondata.TASKTYPE_ZHUANGXIEDUI://装卸队
                switch (currentNode) {
                    case Commondata.TASKNODE_DJS:
                        node = Commondata.TASKNODE_JS;
                        break;
                    case Commondata.TASKNODE_JS:
                        node = Commondata.TASKNODE_DW;
                        break;
                    case Commondata.TASKNODE_DW:
                        node = Commondata.TASKNODE_HCMKQ;
                        break;
                    case Commondata.TASKNODE_HCMKQ:
                        node = Commondata.TASKNODE_ZXHWC;
                        break;
                    case Commondata.TASKNODE_ZXHWC:
                        node = Commondata.TASKNODE_WC;
                        break;
                    case Commondata.TASKNODE_WC:
                        node = Commondata.TASKNODE_RWWC;
                        break;
                }
                break;
        }
        return node;
    }

    public static String toNameByNode(String node) {
        String name = null;
        switch (node) {
            case Commondata.TASKNODE_DJS:
                name = "待接受";
                break;
            case Commondata.TASKNODE_SJJS:
                name = "司机接受";
                break;
            case Commondata.TASKNODE_RCBD:
                name = "人车绑定";
                break;
            case Commondata.TASKNODE_JS:
                name = "接受";
                break;
            case Commondata.TASKNODE_DW:
                name = "到位";
                break;
            case Commondata.TASKNODE_KS:
                name = "开始";
                break;
            case Commondata.TASKNODE_WC:
                name = "完成";
                break;
            case Commondata.TASKNODE_KJWC:
                name = "靠接完成";
                break;
            case Commondata.TASKNODE_CMGB:
                name = "舱门关闭";
                break;
            case Commondata.TASKNODE_DJWC:
                name = "牵引对接完成";
                break;
            case Commondata.TASKNODE_KCMGB:
                name = "客舱门关闭";
                break;
            case Commondata.TASKNODE_HCMKQ:
                name = "货舱门开启";
                break;
            case Commondata.TASKNODE_ZXHWC:
                name = "装/卸货完成";
                break;
            case Commondata.TASKNODE_CLDWC:
                name = "撤轮档完成";
                break;
        }
        return name;
    }

}
