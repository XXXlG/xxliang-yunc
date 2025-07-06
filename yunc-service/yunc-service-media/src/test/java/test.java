import com.alibaba.fastjson.JSON;
import com.xxliang.yunc.MediaApp;
import com.xxliang.yunc.domain.Login;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xxliang
 * @date 2025/6/6  10:46
 * @description
 */
@SpringBootTest(classes = MediaApp.class)
@RunWith(SpringRunner.class)
public class test {
    @Test
    public void testProcessBuilder() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        // processBuilder.command("ping","127.0.0.1");
        processBuilder.command("ipconfig");
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        try {
            //启动进程
            Process start = processBuilder.start();
            //获取输入流
            InputStream inputStream = start.getInputStream();
            //转成字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            int len = -1;
            char[] c = new char[1024];
            StringBuffer outputString = new StringBuffer();
            //读取进程输入流中的内容
            while ((len = inputStreamReader.read(c)) != -1) {
                String s = new String(c, 0, len);
                outputString.append(s);
                System.out.print(s);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testVideoBuilder() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        List<String> commond = new ArrayList<>();
        commond.add("D:\\ffmpeg-master-latest-win64-gpl-shared\\bin\\ffmpeg");
        commond.add("-i");
        commond.add("D:\\4\\c\\4ceff373729a41c3a14f643b02dc5817\\4ceff373729a41c3a14f643b02dc5817.mp4");
        commond.add("-hls_time");
        commond.add("10");
        commond.add("-hls_list_size");
        commond.add("0");
        commond.add("-hls_segment_filename");
        commond.add("D:\\4\\c\\4ceff373729a41c3a14f643b02dc5817\\aa\\habor%05d.ts");
        commond.add("D:\\4\\c\\4ceff373729a41c3a14f643b02dc5817\\aa\\habor.m3u8");
        processBuilder.command(commond);
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        try {
            //启动进程
            Process start = processBuilder.start();
            //获取输入流
            InputStream inputStream = start.getInputStream();
            //转成字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            int len = -1;
            char[] c = new char[1024];
            StringBuffer outputString = new StringBuffer();
            //读取进程输入流中的内容
            while ((len = inputStreamReader.read(c)) != -1) {
                String s = new String(c, 0, len);
                outputString.append(s);
                System.out.print(s);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void rocketMQTest(){
        rocketMQTemplate.sendOneWay("topic-test:sms",  "hello rocketMQ");
    }
    @Test
    public void rocketMQTest2(){
        Login login =Login.builder().username("xxliang").id(1L).type(0).build();
        SendResult sendResult = rocketMQTemplate.syncSend("message:sms", MessageBuilder.withPayload(JSON.toJSONString(login)).build());
        System.out.println(sendResult);
    }
}
