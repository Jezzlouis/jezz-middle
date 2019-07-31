package com.jezz.dymdatasource;

import com.jezz.dymdatasource.entity.TbTaskDTO;
import com.jezz.dymdatasource.entity.TbUserDTO;
import com.jezz.dymdatasource.main.DymdatasourceApplication;
import com.jezz.dymdatasource.service.test1.TaskService;
import com.jezz.dymdatasource.service.test2.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DymdatasourceApplication.class)
public class DymdatasourceApplicationTests {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        TbTaskDTO vo = new TbTaskDTO();
        vo.setTaskId(1);
        TbTaskDTO tbTaskDTO = taskService.queryByTaskId(vo);
        System.out.println(tbTaskDTO);
        TbUserDTO uvo = new TbUserDTO();
        uvo.setUserId(1);
        TbUserDTO tbUserDTO = userService.queryByUserId(uvo);
        System.out.println(tbUserDTO);
    }

}
