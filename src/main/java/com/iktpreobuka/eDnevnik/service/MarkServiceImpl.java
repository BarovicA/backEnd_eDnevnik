package com.iktpreobuka.eDnevnik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.MarkEntity;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.enums.MarkEnum;
import com.iktpreobuka.eDnevnik.repositories.MarkRepository;

@Service
public class MarkServiceImpl implements MarkService {

	@Autowired
	MarkRepository markRepository;
	
	
	@Override
	public MarkEnum enumByNo(int i) {
		switch (i) {
        case 1:
            return MarkEnum.INSUFFICIENT;
        case 2:
            return MarkEnum.SUFFICIENT;
        case 3:
            return MarkEnum.GOOD;
        case 4:
            return MarkEnum.VERY_GOOD;
        case 5:
            return MarkEnum.EXCELLENT;
	}
		return null;
	}
	@Override
	public SubjectEntity getSubjectFromMark(MarkEntity mark) {
		SubjectEntity subject = mark.getTeacherSubjectStudent().getTeacherSubject().getSubject();
		return subject;
	}
	
	@Override
	public TeacherEntity getTeacherFromMark(MarkEntity mark) {
		TeacherEntity teacher = mark.getTeacherSubjectStudent().getTeacherSubject().getTeacher();
		return teacher;
	}
	@Override
	public StudentEntity getStudentFromMark(MarkEntity mark) {
		StudentEntity student = mark.getTeacherSubjectStudent().getStudent();
		return student;
	}
	@Override
	public ParentEntity getStudentsParent(MarkEntity mark) {
		ParentEntity parent = mark.getTeacherSubjectStudent().getStudent().getParent();
		if (parent == null) {
			return null;
		}
		return parent;
	}
	
	
	
	
}



