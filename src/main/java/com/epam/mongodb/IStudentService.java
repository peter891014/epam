package com.epam.mongodb;

import java.util.List;

public interface IStudentService {
    int insertStudent(Student student);

    int updateStudent(Student student);

    int removeStudent(Long id);

    Student findOne(Student student);

    List<Student> findlike(Student student);

    List<Student> findmore(Student student);


    List<Student> findtime(Student student);

    List<Student> findtimeByPage(Student student);

}
