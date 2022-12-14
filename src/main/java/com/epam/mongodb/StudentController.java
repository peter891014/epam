package com.epam.mongodb;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: epam
 * @description:
 * @author: 作者名字
 * @create: 2022-06-14 09:04
 **/
@RequestMapping("/mongo")
@RestController
@Api(tags = "mongo相关")
public class StudentController {
//    /** 查询玩家账户信息列表 */
//   // @RequiresPermissions("game:user:list")
//    @PostMapping("/list")
//    @ResponseBody
//    public TableDataInfo list(User user) {
//        Query query = MOngodbstartPage();
//        ViewResponse viewResponse = userService.selectUserList(user, query);
//        return MongogetDataTable(viewResponse.getLists(), viewResponse.getSize());
//    }
//
//
//    /** 设置请求分页数据 得到query */
//    protected Query MOngodbstartPage() {
//        PageDomain pageDomain = TableSupport.buildPageRequest();
//        Integer pageNum = pageDomain.getPageNum();
//        Integer pageSize = pageDomain.getPageSize();
//        Query query = new Query().skip((pageNum - 1) * pageSize).limit(pageSize);
//        return query;
//    }
    //后续查询将query代入接口中，如需其他查询条件  就query.方法  这要既有条件，又有分页了
  //userService.selectUserList(user, query);

    @Autowired
    private IStudentService studentService;

    @PostMapping("add")
    @ApiOperation(value = "新增用户信息" ,notes = "具体描述")
    public String add(@RequestBody Student student) {
        int i = studentService.insertStudent(student);
        if (i == 1) {
            return "success";
        }
        return "false";
    }

    @PostMapping("update")
    @ApiOperation(value = "更新用户信息")
    public String update(Student student) {
        int i = studentService.updateStudent(student);
        if (i == 1) {
            return "success";
        }
        return "false";
    }

    @GetMapping("remove")
    @ApiOperation(value = "删除用户信息")
    public String remove(Long id) {
        int i = studentService.removeStudent(id);
        if (i == 1) {
            return "success";
        }
        return "false";
    }

    @PostMapping("findone")
    @ApiOperation(value = "查询用户信息")
    public Student findone(Student student) {
        Student one = studentService.findOne(student);
        return one;

    }

    @PostMapping("findlike")
    @ApiOperation(value = "模糊查询用户信息")
    public List<Student> findlike(Student student) {
        List<Student> findlike = studentService.findlike(student);
        return findlike;

    }

    @PostMapping("findmore")
    @ApiOperation(value = "列表用户信息")
    public List<Student> findmore(Student student) {
        List<Student> findlike = studentService.findmore(student);
        return findlike;

    }
    @PostMapping("findtime")
    @ApiOperation(value = "倒序排列用户信息")
    public List<Student> findtime(Student student) {
        List<Student> findlike = studentService.findtime(student);
        return findlike;

    }
    @PostMapping("findtimeByPage")
    @ApiOperation(value = "分页用户信息")
    public List<Student> findtimeByPage(Student student) {
        List<Student> findlike = studentService.findtimeByPage(student);
        return findlike;

    }

}
