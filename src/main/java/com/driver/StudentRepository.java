package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class StudentRepository {

    HashMap<String, Student> studentMap;
    HashMap<String, Teacher> teacherMap;
    HashMap<String, List<String>> pairMap;

    public StudentRepository() {
        this.studentMap = new HashMap<>();
        this.teacherMap = new HashMap<>();
        this.pairMap = new HashMap<>();
    }

    public void addStudent(Student student){
        studentMap.put(student.getName(),student);
    }
    public void addTeacher(Teacher teacher){
        teacherMap.put(teacher.getName(),teacher);
    }
    public void addStudentTeacherPair(String student, String teacher){
        if(studentMap.containsKey(student) && teacherMap.containsKey(teacher)){
            studentMap.put(student, studentMap.get(student));
            teacherMap.put(teacher, teacherMap.get(teacher));
            List<String> currentStudents = new ArrayList<String>();
            if(pairMap.containsKey(teacher)) currentStudents = pairMap.get(teacher);
            currentStudents.add(student);
            pairMap.put(teacher, currentStudents);
        }
    }
    public Student getStudentFromDB(String studentName){
        return studentMap.get(studentName);
    }

    public Teacher getTeacherFromDB(String teacherName){
        return teacherMap.get(teacherName);
    }

    public List<String> getStudentsListFromDB(String teacherName){
        List<String> studentsList = new ArrayList<String>();
        if(pairMap.containsKey(teacherName)) studentsList = pairMap.get(teacherName);
        return studentsList;
    }
    public List<String> getAllStudentsFromDB(){
        return new ArrayList<>(studentMap.keySet());
    }

    public void deleteTeacherStudentsFromDB(String director){
        List<String> students = new ArrayList<String>();
        if(pairMap.containsKey(director)){
            students = pairMap.get(director);
            for(String movie: students){
                if(studentMap.containsKey(movie)){
                    studentMap.remove(movie);
                }
            }

            pairMap.remove(director);
        }

        if(teacherMap.containsKey(director)){
            teacherMap.remove(director);
        }
    }
    public void deleteAllTeacherStudentsFromDB(){
        for(String teacherName:teacherMap.keySet()){
            deleteTeacherStudentsFromDB(teacherName);
        }
    }
}
