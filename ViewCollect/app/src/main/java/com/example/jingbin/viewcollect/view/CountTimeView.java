package com.example.jingbin.viewcollect.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.jingbin.viewcollect.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiaguangcheng on 16/4/21.
 */
public class CountTimeView extends TextView {
    long parseTime;
    public CountTimeView(Context context) {
        super(context);

    }

    public CountTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void calculateTime(long time,String flag){
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        if("upcoming".equals(flag)){
            /**即将开始*/
            parseTime= time-System.currentTimeMillis()/1000;
            parseTime=parseTime*1000;
            Message message=Message.obtain();
            message.what=0;
            handler.sendMessage(message);
        }else if("ongoing".equals(flag)){
            /**正在进行*/
            parseTime=System.currentTimeMillis()/1000-time;
            parseTime=parseTime*1000;
            Message message=Message.obtain();
            message.what=1;
            handler.sendMessage(message);
        }else if("closed".equals(flag)){
            /**已经结束*/
            Message message=Message.obtain();
            message.what=4;
            handler.sendMessage(message);
        }else{
            /**已经结束*/
            Message message=Message.obtain();
            message.what=4;
            handler.sendMessage(message);
        }
    }

    /**
     *
     * @param time 距离结束还剩多少秒
     */
    public void calculateTime(long time){
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        parseTime= time-System.currentTimeMillis()/1000;
        parseTime=parseTime*1000;
        Message message=Message.obtain();
        message.what=5;
        handler.sendMessage(message);
    }

    public void calculateVip(long time){
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        parseTime= time-System.currentTimeMillis()/1000;
        parseTime=parseTime*1000;
        Message message=Message.obtain();
        message.what=7;
        handler.sendMessage(message);
    }
    /**
     *
     * @param time 活动开始时间
     */
    public void calculateTime( long time,boolean isBegin){
        /**如果当前时间超过活动时间,就显示活动终止*/
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        time=time*1000;
            /**距离活动结束还有*/
            if(time>0){
                parseTime=time;
                Message message=Message.obtain();
                message.what=2;
                handler.sendMessage(message);

            }else{
                /**活动已停止*/
                Message message=Message.obtain();
                message.what=3;
                handler.sendMessage(message);
            }

    }


    Handler handler=new Handler(){
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                /**活动未开始*/
                case 0:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            parseTime=parseTime-1000;
                            if(parseTime==1000){
                                Message ms2=Message.obtain();
                                ms2.what=1;
                                handler.sendMessage(ms2);
                            }else if(parseTime>1000){
                                Message ms=Message.obtain();
                                ms.what=0;
                                handler.sendMessage(ms);

                            }else{
                                Message ms2=Message.obtain();
                                ms2.what=1;
                                handler.sendMessage(ms2);
                            }
                            setText("距开始:"+formatDuring(parseTime));
                            setBackgroundResource(R.drawable.kuang_gray);
                            setTextColor(Color.parseColor("#000000"));
                        }
                    },1000);

                    break;
                /**活动已开始*/
                case 1:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setBackgroundResource(R.drawable.bg_checked);
                            setTextColor(Color.parseColor("#ffffff"));
                            parseTime=parseTime+1000;
                            setText("活动中:"+formatDuring(parseTime));
                            Message ms1=Message.obtain();
                            ms1.what=1;
                            handler.sendMessage(ms1);
                        }
                    },1000);

                    break;
                case 2:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            parseTime=parseTime-1000;
                            if(parseTime==1000){
                                Message ms2=Message.obtain();
                                ms2.what=2;
                                handler.sendMessage(ms2);
                            }else if(parseTime>1000){
                                Message ms=Message.obtain();
                                ms.what=2;
                                handler.sendMessage(ms);

                            }else{
                                Message ms2=Message.obtain();
                                ms2.what=3;
                                handler.sendMessage(ms2);
                            }
//                            setBackgroundResource(R.drawable.kuang_gray);
                            setTextColor(Color.parseColor("#f23030"));
                            setText(formatDuring(parseTime));
                        }
                    },1000);
                    break;
                case 3:
                    if(timeStopListener!=null){
                        timeStopListener.onTimeStopYouShouldDo();
                    }
                    setTextColor(Color.parseColor("#999999"));
                    setText(formatDuring(0));
//                    setBackgroundResource(R.drawable.kuang_gray);
                    break;
                case 4:
                    setText("已结束: 00:00:00");
                    setBackgroundResource(R.drawable.kuang_gray);
                    setTextColor(Color.parseColor("#999999"));
                    break;
                case 5:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            parseTime=parseTime-1000;

                            if(parseTime==0){
                                Message ms2=Message.obtain();
                                ms2.what=6;
                                handler.sendMessage(ms2);
                            }else if(parseTime>1000||parseTime==1000){
                                Message ms=Message.obtain();
                                ms.what=5;
                                handler.sendMessage(ms);

                            }else{
                                Message ms2=Message.obtain();
                                ms2.what=6;
                                handler.sendMessage(ms2);
                            }
                            setText("距截止支付还有:"+formatDurinShowTime(parseTime,true));
                            setTextColor(Color.parseColor("#f23030"));
                        }
                    },1000);
                    break;

                case 6:
                    if(timeStopListener!=null){
                        timeStopListener.onTimeStopYouShouldDo();
                    }
                    setTextColor(Color.parseColor("#f23030"));
                    setText("支付时间已截止");
                    break;
                case 7:// 管家 购买记录 待付款倒计时
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            parseTime=parseTime-1000;

                            if(parseTime==0){
                                Message ms2=Message.obtain();
                                ms2.what=6;
                                handler.sendMessage(ms2);
                            }else if(parseTime>1000||parseTime==1000){
                                Message ms=Message.obtain();
                                ms.what=8;
                                handler.sendMessage(ms);

                            }else{
                                Message ms2=Message.obtain();
                                ms2.what=6;
                                handler.sendMessage(ms2);
                            }
                            setText(formatDurinShowTime(parseTime,false));
                            setTextColor(Color.parseColor("#f23030"));
                        }
                    },1000);
                    break;
                case 8:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            parseTime=parseTime-1000;

                            if(parseTime==0){
                                Message ms2=Message.obtain();
                                ms2.what=6;
                                handler.sendMessage(ms2);
                            }else if(parseTime>1000||parseTime==1000){
                                Message ms=Message.obtain();
                                ms.what=8;
                                handler.sendMessage(ms);

                            }else{
                                Message ms2=Message.obtain();
                                ms2.what=6;
                                handler.sendMessage(ms2);
                            }
                            setText(formatDurinShowTime(parseTime,false));
                            setTextColor(Color.parseColor("#f23030"));
                        }
                    },1000);
                    break;
            }

        }
    };


    public static String formatDuring(long mss) {
        String h,m,s;
//        long days = mss / (1000 * 60 * 60 * 24);
//        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long hours=mss/(1000*60*60);
        if(hours<10&&hours>-10){
            h="0"+hours;
        }else{
            h=hours+"";
        }


        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        if(minutes<10&&minutes>-10){
            m="0"+minutes;
        }else{
            m=minutes+"";
        }

        long seconds = (mss % (1000 * 60)) / 1000;
        if(seconds<10&&seconds>-10){
            s="0"+seconds;
        }else{
            s=seconds+"";
        }
        return (h+ ":" + m + ":"
                + s).replaceAll("-","");
    }


    public static String formatDurinShowTime(long mss,boolean isCharacter) {
        String h,m,s;
//        long days = mss / (1000 * 60 * 60 * 24);
//        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long hours=mss/(1000*60*60);
        if(hours<10&&hours>-10){
            h="0"+hours;
        }else{
            h=hours+"";
        }


        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        if(minutes<10&&minutes>-10){
            m="0"+minutes;
        }else{
            m=minutes+"";
        }

        long seconds = (mss % (1000 * 60)) / 1000;
        if(seconds<10&&seconds>-10){
            s="0"+seconds;
        }else{
            s=seconds+"";
        }

        return isCharacter?(h+ "小时" + m + "分"
                + s+"秒").replaceAll("-",""):(h+":"+m+":"+s);
    }
    /**
     * 设置 活动开始时间
     * @param date 活动开始时间 格式为2016-05-04 05:20:20
     */
    public void setRawTime(String date){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(date);
            parseTime = parse.getTime();
            calculateTime(parseTime,true);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    /**活动结束时候的回调*/
    TimeStopListener timeStopListener;
    public void setTimeStopListener(TimeStopListener timeStopListener){
        this.timeStopListener=timeStopListener;
    }
}
