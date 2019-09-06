package com.huidisen.ep750.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.huidisen.ep750.R;
import com.orhanobut.logger.Logger;

import java.util.HashMap;

/**
 * Created by Eason Lu on 2014/5/2.
 */
public class SoundPoolUtils {
    public static final String BEYOND_THE_MARK = "beyondthemark";
    public static final String CHECK_CAR_FAILURE = "checkcarfailure";
    public static final String CHECK_CAR_SUCCESS = "checkcarsuccess";
    public static final String COMMIT_NODE_FAILURE = "commitnodefailure";
    public static final String NEW_TASK = "newtask";
    public static final String OVER_SPEED = "overspeed";
    public static final String TASK_FINISHED = "taskfinished";
    public static final String WELCOME = "welcome";
    public static final String HAVE_OTHER_TASKS = "haveothertasks";
    public static final String TASK_UNFINISHED = "taskunfinished";
    private static SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
    private static HashMap<String, Integer> hashMap = new HashMap<>();
    private static boolean isPlaying = false;
    private static MediaPlayer player;

    public static void initSound(Context context) {

        hashMap.put(BEYOND_THE_MARK, soundPool.load(context, R.raw.beyond_the_mark, 1));
        hashMap.put(CHECK_CAR_FAILURE, soundPool.load(context, R.raw.check_car_commit_failure, 1));
        hashMap.put(CHECK_CAR_SUCCESS, soundPool.load(context, R.raw.check_car_commit_sucess, 1));
        hashMap.put(COMMIT_NODE_FAILURE, soundPool.load(context, R.raw.commit_node_failure, 1));
        hashMap.put(NEW_TASK, soundPool.load(context, R.raw.new_task, 1));
        hashMap.put(OVER_SPEED, soundPool.load(context, R.raw.overspeed, 1));
        hashMap.put(TASK_FINISHED, soundPool.load(context, R.raw.task_finished, 1));
        hashMap.put(WELCOME, soundPool.load(context, R.raw.welcome, 1));
        hashMap.put(HAVE_OTHER_TASKS, soundPool.load(context, R.raw.you_have_other_tasks, 1));
        hashMap.put(TASK_UNFINISHED, soundPool.load(context, R.raw.task_unfinished, 1));
        //player = MediaPlayer.create(context, R.raw.newsms);
        //soundPool.load(context, R.raw.newsms,1);
    }

    public static boolean getPlayStatus() {
        return isPlaying;
    }

    public static void setPlayStatus(boolean status) {
        isPlaying = status;
    }

    public synchronized static void playSound(Context context, int priority, String witch) {
        if (isPlaying) {
            Logger.w("is playing");
            return;
        }
        SoundPoolUtils.setPlayStatus(true);
        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        float currentVolume = manager.getStreamVolume(AudioManager.STREAM_RING);
        float maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_RING);
        float volume = currentVolume / (5 * maxVolume);

        soundPool.play(hashMap.get(witch), volume, volume, priority, 0, 1);
        SoundPoolUtils.setPlayStatus(false);
    }
}
