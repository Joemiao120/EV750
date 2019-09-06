package com.huidisen.ep750.utils;

import android.content.Context;

import com.huidisen.ep750.bean.TaskDefBean;
import com.huidisen.ep750.bean.TaskNodeBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by miaoyichao on 16/6/24.
 */
public class XMLUtils {
    public static void parseXML(Context context, String filePath) {
        try {
            File path = new File(filePath);
            InputStream fis = new FileInputStream(path);

            parseXMLWithPull(context, fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void parseAssetsXML(Context context) {
        try {
            InputStream fis = context.getAssets().open("task_def.xml");
            parseXMLWithPull(context, fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parseXMLWithPull(Context context, InputStream fis) {
        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(fis, "utf-8");
            int eventType = xmlPullParser.getEventType();

            TaskDefBean taskDef = null;
            TaskNodeBean taskNode = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        switch (nodeName) {
                            case "taskDef":
                                taskDef = new TaskDefBean();
                                if (xmlPullParser.getAttributeValue(0) != null)
                                    taskDef.name = xmlPullParser.getAttributeValue(0);
                                if (xmlPullParser.getAttributeValue(1) != null)
                                    taskDef.type = xmlPullParser.getAttributeValue(1);
                                if (xmlPullParser.getAttributeValue(2) != null)
                                    taskDef.groupName = xmlPullParser.getAttributeValue(2);
                                if (xmlPullParser.getAttributeValue(3) != null)
                                    taskDef.isFlightTask = xmlPullParser.getAttributeValue(3);
                                break;
                            case "nodeList":   //  节点集合
                                taskDef.taskNodes = new ArrayList<>();
                                break;

                            case "taskNode":
                                taskNode = new TaskNodeBean();

                                if (xmlPullParser.getAttributeValue(0) != null)
                                    taskNode.name = xmlPullParser.getAttributeValue(0);
                                if (xmlPullParser.getAttributeValue(1) != null)
                                    taskNode.code = xmlPullParser.getAttributeValue(1);
                                if (xmlPullParser.getAttributeValue(2) != null)
                                    taskNode.auto = xmlPullParser.getAttributeValue(2);
                                break;
                        }

                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        switch (nodeName) {
                            case "taskNode":   //  节点添加到集合里面去
                                taskDef.taskNodes.add(taskNode);
                                break;
                            case "taskDef":
                                ObjectSaveUtil.saveObject(context, taskDef, Commondata.TASK_DEF + taskDef.type);
//                                Log.e("XML", ObjectSaveUtil.readObject(context, Commondata.TASK_DEF + taskDef.type).toString());
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
