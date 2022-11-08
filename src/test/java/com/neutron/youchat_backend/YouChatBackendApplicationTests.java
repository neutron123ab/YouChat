package com.neutron.youchat_backend;

import com.neutron.youchat_backend.entity.Group;
import com.neutron.youchat_backend.mapper.GroupMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YouChatBackendApplicationTests {

    @Autowired
    private GroupMapper groupMapper;

    @Test
    void contextLoads() {

        //Integer demo1 = groupMapper.createGroup(2, "demo2", 100);
        Group group = new Group();
        group.setGroupOwnerId(4);
        group.setGroupName("demo4");
        group.setContainer(1000);
        Integer group1 = groupMapper.createGroup(group);

        System.out.println(group.getId());

    }

}
