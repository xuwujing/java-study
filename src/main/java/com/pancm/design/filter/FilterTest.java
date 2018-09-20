package com.pancm.design.filter;

import java.util.ArrayList;
import java.util.List;

/**
* @Title: FilterTest
* @Description: 过滤器模式
* 过滤器模式（Filter Pattern）或标准模式（Criteria Pattern）是一种设计模式，
* 这种模式允许开发人员使用不同的标准来过滤一组对象，通过逻辑运算以解耦的方式把它们连接起来。
* 这种类型的设计模式属于结构型模式，它结合多个标准来获得单一标准。
* @Version:1.0.0  
* @author pancm
* @date 2018年8月8日
*/
public class FilterTest {

	public static void main(String[] args) {
		/*
		 *  1.创建学生，有姓名、性别、年级这三个属性
		 *  2.根据这三个属性进行过滤分组
		 *  
		 */
		List<Student> list=new ArrayList<Student>();
		list.add(new Student("小明", "male", 1));
		list.add(new Student("小红", "female", 2));
		list.add(new Student("小刚", "male", 2));
		list.add(new Student("小霞", "female", 3));
		list.add(new Student("小智", "male", 3));
		list.add(new Student("虚无境", "male", 1));
		
		
		FilterinGrule male = new MaleStudents();
		FilterinGrule female = new FemaleStudents();
		FilterinGrule secondGrade = new SecondGrade();
		FilterinGrule secondGradeMale = new And(secondGrade, male);
		FilterinGrule secondGradeOrFemale = new Or(secondGrade, female);
	    
		System.out.println("男生:"+male.filter(list));
		System.out.println("女生:"+female.filter(list));
		System.out.println("二年级学生:"+secondGrade.filter(list));
		System.out.println("二年级男生:"+secondGradeMale.filter(list));
		System.out.println("二年级的学生或女生:"+secondGradeOrFemale.filter(list));
		
		/*
		 * 
			 男生:[Student [name=小明, gender=male, grade=1], Student [name=小刚, gender=male, grade=2], Student [name=小智, gender=male, grade=3], Student [name=虚无境, gender=male, grade=1]]
			女生:[Student [name=小红, gender=female, grade=2], Student [name=小霞, gender=female, grade=3]]
			二年级学生:[Student [name=小红, gender=female, grade=2], Student [name=小刚, gender=male, grade=2]]
			二年级男生:[Student [name=小刚, gender=male, grade=2]]
			二年级的学生或女生:[Student [name=小红, gender=female, grade=2], Student [name=小刚, gender=male, grade=2], Student [name=小霞, gender=female, grade=3]]
		 */
		
	}
}

class Student{
	private String name; 
	private String gender; 
	private Integer grade;
	public Student(String name, String gender, Integer grade) {
		super();
		this.name = name;
		this.gender = gender;
		this.grade = grade;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Integer getGrade() {
		return grade;
	}
	
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", gender=" + gender + ", grade=" + grade + "]";
	}
}

interface FilterinGrule {
	List<Student>  filter(List<Student> students);
}

class MaleStudents implements FilterinGrule{
	@Override
	public List<Student> filter(List<Student> students) {
		List<Student> maleStudents = new ArrayList<Student>(); 
		students.forEach(student->{
			 if(student.getGender().equalsIgnoreCase("male")){
	        	 maleStudents.add(student);
	         }
		});
	    return maleStudents;
	}
}

class FemaleStudents implements FilterinGrule{
	@Override
	public List<Student> filter(List<Student> students) {
		List<Student> femaleStudents = new ArrayList<Student>(); 
		students.forEach(student->{
			 if(student.getGender().equalsIgnoreCase("female")){
				 femaleStudents.add(student);
	         }
		});
	    return femaleStudents;
	}
}

class SecondGrade implements FilterinGrule{
	@Override
	public List<Student> filter(List<Student> students) {
		List<Student> secondGradeStudents = new ArrayList<Student>(); 
		students.forEach(student->{
			 if(student.getGrade() == 2){
				 secondGradeStudents.add(student);
	         }
		});
		
	    return secondGradeStudents;
	}
}


class And implements FilterinGrule{
	 private FilterinGrule filter;
	 private FilterinGrule filter2;
	
	 public And(FilterinGrule filter,FilterinGrule filter2) {
		 this.filter=filter;
		 this.filter2=filter2;
	 }
	
	@Override
	public List<Student> filter(List<Student> students) {
		List<Student> students2=filter.filter(students);
		return filter2.filter(students2);
	}
}

class Or implements FilterinGrule{
	 private FilterinGrule filter;
	 private FilterinGrule filter2;
	
	 public Or(FilterinGrule filter,FilterinGrule filter2) {
		 this.filter=filter;
		 this.filter2=filter2;
	 }
	
	@Override
	public List<Student> filter(List<Student> students) {
		List<Student> students1=filter.filter(students);
		List<Student> students2=filter2.filter(students);
		students2.forEach(student->{
			 if(!students1.contains(student)){
				 students1.add(student);
	         }
		});
		return students1;
	}
}


