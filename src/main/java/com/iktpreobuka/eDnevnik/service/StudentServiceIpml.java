package com.iktpreobuka.eDnevnik.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectGradeEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectStudentEntity;
import com.iktpreobuka.eDnevnik.entities.dto.ReportStudentDto;
import com.iktpreobuka.eDnevnik.entities.dto.StudentDto;
import com.iktpreobuka.eDnevnik.entities.dto.SubjectMarksDto;
import com.iktpreobuka.eDnevnik.entities.enums.RoleENUM;
import com.iktpreobuka.eDnevnik.repositories.GradeRepository;
import com.iktpreobuka.eDnevnik.repositories.MarkRepository;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectGradeRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectStudentRepository;

@Service
@Primary
public class StudentServiceIpml implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private MarkRepository markRepository;
	@Autowired
	GradeRepository gradeRepository;

	@Autowired
	TeacherSubjectGradeRepository teSuGrRepository;
	@Autowired
	TeacherSubjectStudentRepository teacherSubjectStudentRepository;

	@Override
	public StudentEntity mappNewStudent(StudentDto dto) {
		StudentEntity student = new StudentEntity();
		student.setFirstName(dto.getfirstName());
		student.setLastName(dto.getLastName());
		student.setUsername(dto.getUsername());
		student.setPassword(dto.getPassword());
		student.setRole(roleRepo.findByName(RoleENUM.STUDENT).get());
		student.setDeleted(false);
		return student;
	}

	@Override
	public Boolean isActive(Long id) {
		if (studentRepository.existsById(id)) {
			StudentEntity student = studentRepository.findById(id).get();
			if (student.getDeleted().equals(true)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public StudentEntity changeStudentEntity(StudentEntity student, StudentDto dto) {
		if (!(dto.getfirstName() == null))
			student.setFirstName(dto.getfirstName());
		if (!(dto.getLastName() == null))
			student.setLastName(dto.getLastName());
		if (!(dto.getUsername() == null))
			student.setUsername(dto.getUsername());
		if (!(dto.getPassword() == null))
			student.setPassword(dto.getPassword());
		student.setDeleted(dto.isDeleted());

		return student;
	}

	@Override
	public StudentEntity addStudentToGrade(Long studentId, Long gradeId) {
		StudentEntity studentEntity = studentRepository.findById(studentId).orElse(null);
		GradeEntity grade = gradeRepository.findById(gradeId).get();
		studentEntity.setGrade(grade);

		List<TeacherSubjectGradeEntity> listaTSG = new ArrayList<>();
		listaTSG = teSuGrRepository.findAllByGrade(grade);
		for (TeacherSubjectGradeEntity teacherSubjectGradeEntity : listaTSG) {
			TeacherSubjectStudentEntity tss = new TeacherSubjectStudentEntity();
			tss.setTeacherSubject(teacherSubjectGradeEntity.getTeacherSubject());
			tss.setStudent(studentEntity);
			teacherSubjectStudentRepository.save(tss);
		}
		gradeRepository.save(grade);

		return studentRepository.save(studentEntity);                                    

	}

	@Override
	public List<SubjectMarksDto> makeSubjectMarksDto(StudentEntity student) {

		List<TeacherSubjectStudentEntity> tssList = teacherSubjectStudentRepository.findByStudent(student);
		List<SubjectMarksDto> subjectsWithMarkslist = tssList.stream().map(tss -> {
			SubjectMarksDto dto = new SubjectMarksDto();
			dto.setName(tss.getTeacherSubject().getSubject().getName());
			dto.setMarks(markRepository.findByTeacherSubjectStudent(tss));
			return dto;
		}).collect(Collectors.toList());
		return subjectsWithMarkslist;

	}
	
	@Override
	public ReportStudentDto makeReportDto(StudentEntity student) {
		ReportStudentDto dto = new ReportStudentDto();
		List<SubjectMarksDto> list = makeSubjectMarksDto(student);
		dto.setFirstName(student.getFirstName());
		dto.setLastName(student.getLastName());
		dto.setSubjectMarkDto(list);
		return dto;
	}
	@Override
	public void deleteStudent(Long studentId) {
        Optional<StudentEntity> studentOptional = studentRepository.findById(studentId);

            StudentEntity student = studentOptional.get();
            student.setDeleted(true);
            List<TeacherSubjectStudentEntity> teacherSubjectStudents = teacherSubjectStudentRepository.findByStudent(student);
                if (teacherSubjectStudents != null) {
                	for (TeacherSubjectStudentEntity tss : teacherSubjectStudents) {
                        tss.setDeleted(true);
                        teacherSubjectStudentRepository.save(tss);			
                    }
                }
            studentRepository.save(student);   
        }
	
	
	
	
}
