package com.xxliang.yunc.service.impl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xxliang.yunc.api.ESCourseFeign;
import com.xxliang.yunc.constant.ValidateConstant;
import com.xxliang.yunc.doc.CourseDoc;
import com.xxliang.yunc.domain.*;
import com.xxliang.yunc.dto.MessageSMSDto;
import com.xxliang.yunc.dto.MessageStationDto;
import com.xxliang.yunc.dto.User2PhoneDto;
import com.xxliang.yunc.exception.GlobalBussnessException;
import com.xxliang.yunc.mapper.*;
import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.service.ICourseService;
import com.xxliang.yunc.service.ICourseTeacherService;
import com.xxliang.yunc.util.AssertUtil;
import com.xxliang.yunc.vo.CourseDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * <p>
 * 服务实现类
 * </p>
 * @author xxliang
 * @since 2025-06-03
 * */

@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseMarketMapper courseMarketMapper;
    @Autowired
    private CourseSummaryMapper courseSummaryMapper;
    @Autowired
    private ESCourseFeign  esCourseFeign;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private CourseChapterMapper courseChapterMapper;
    @Autowired
    private CourseDetailMapper courseDetailMapper;
    @Autowired
    private CourseTeacherMapper courseTeacherMapper;
    @Autowired
    private ICourseTeacherService iCourseTeacherService;

    @Override
    public Course onLineCourse(Long id) {

        AssertUtil.isNull(id, "id不能为空");
        Wrapper wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);

        Course course = selectOne(wrapper);
        AssertUtil.isNull(course, "课程不存在");

        AssertUtil.isTrue(course.getStatus() == 1, "课程已上架");

        CourseDoc courseDoc = new CourseDoc();
        //远程调用个p啊都引入依赖了
        BeanUtils.copyProperties(course, courseDoc);

        //营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(course.getId());
        BeanUtils.copyProperties(courseMarket, courseDoc);
        courseDoc.setCharge(courseMarket.getCharge()==1?"收费":"免费");

        CourseSummary courseSummary = courseSummaryMapper.selectById(course.getId());
        BeanUtils.copyProperties(courseSummary, courseDoc);
//
//        CourseChapter courseChapter = new CourseChapter().selectOne(new EntityWrapper().eq("course_id", course.getId()));
//        courseDoc.setChapterName(courseChapter.getName());
        //远程服务器 添加到ES
        JSONResult save = esCourseFeign.save(courseDoc);

        //添加成功
        if (!save.isSuccess()) {
            throw new GlobalBussnessException("远程调用失败");
        }
        //6 修改课程状态
        course.setStatus(1);
        courseMapper.updateById(course);



        //7  在此推送消息广告。浪不浪费性能？？？而且广告推送也不会影响主业务。

        //先站内推送//
        publish(course);

        return course;
    }

    private void publish(Course course) {
        /**
         * MQ同步，异步，延迟；
         * 邮箱 2
         * 手机 2
         * 站内推送 1
         */

        // TODO 获取所用用户的ids
        List<Long> list = Arrays.asList(1l, 2l, 3l, 4l,5l);

        MessageStationDto messageStationDto = new MessageStationDto();
        messageStationDto.setContent("新课MQ已经发布");
        messageStationDto.setTitle(course.getName());
        messageStationDto.setType("系统消息");
        messageStationDto.setIds(list);

        SendResult sendResult = rocketMQTemplate.syncSend(ValidateConstant.ROCKETMQ_TOPIC_PUBLISH+":"+ValidateConstant.ROCKETMQ_TAGS_STATION,
                MessageBuilder.withPayload(messageStationDto).build());
        System.out.println(sendResult);

        //其他的推送方式雷同
        //电话推送；
        MessageSMSDto messageSMSDto = new MessageSMSDto();
        messageSMSDto.setContent("新课MQ已经发布");
        messageSMSDto.setTitle(course.getName());
        messageSMSDto.setType("系统消息");
        messageSMSDto.setIp("127.0.0.1");
        messageSMSDto.setUser2Phone(Arrays.asList(new User2PhoneDto("12345678901",1l),new User2PhoneDto("12345678902",2l)));
        SendResult sendResult1 = rocketMQTemplate.syncSend(ValidateConstant.ROCKETMQ_TOPIC_PUBLISH+":"+ValidateConstant.ROCKETMQ_TAGS_SMS,
                MessageBuilder.withPayload(messageSMSDto).build());
        System.out.println(sendResult1);

        //邮箱就散伙了....


    }


    @Override
    public CourseDetailVo detail(Long id) {
        //Here to get course detail;

        CourseDetailVo courseDetailVo = new CourseDetailVo();

        Course course = courseMapper.selectById(id);
        courseDetailVo.setCourse(course);

        //营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(id);
        courseDetailVo.setCourseMarket(courseMarket);

        //course chapter search;
        List<CourseChapter> courseChapters = courseChapterMapper.selectList(new EntityWrapper<CourseChapter>().eq("course_id", id));
        courseDetailVo.setCourseChapter(courseChapters);

        //去章节查询视频。


        //get teachers

        List<Teacher> teachers = iCourseTeacherService.queryTeachersByCourseId(id);
        courseDetailVo.setTeachers(teachers);
        //add detail message;
        CourseDetail courseDetail = courseDetailMapper.selectById(id);
        courseDetailVo.setCourseDetail(courseDetail);
        //
        CourseSummary courseSummary = courseSummaryMapper.selectById(id);
        courseDetailVo.setCourseSummary(courseSummary);
        return courseDetailVo;


    }
}