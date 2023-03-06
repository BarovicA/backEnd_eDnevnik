package com.iktpreobuka.eDnevnik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectGradeEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectStudentEntity;
import com.iktpreobuka.eDnevnik.entities.dto.StudentDto;
import com.iktpreobuka.eDnevnik.repositories.GradeRepository;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectGradeRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectStudentRepository;

@Service
@Primary
public class StudentServiceIpml implements StudentService{
	
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private RoleRepository roleRepo;
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
		student.setRole(roleRepo.findByName("STUDENT"));
		student.setDeleted(false);
		return student;
	}


	@Override
	public Boolean isActive(Long id) {
		if(studentRepository.existsById(id)) {
			StudentEntity student = studentRepository.findById(id).get();
			if(student.getDeleted().equals(true)) {
				return false;
			}else {
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
		StudentEntity studentEntity = studentRepository.findById(studentId).get();
		GradeEntity grade = gradeRepository.findById(gradeId).get();
		studentEntity.setGrade(grade);
		TeacherSubjectGradeEntity teSuGr = grade.getTeacherSubjectGrade();
		TeacherSubjectStudentEntity teSuSu = new TeacherSubjectStudentEntity();
		teSuSu.setTeacherSubject(teSuGr.getTeacherSubject());
		teSuSu.setStudent(studentEntity);
		teSuSu.setDeleted(false);
		gradeRepository.save(grade);
		teacherSubjectStudentRepository.save(teSuSu);
		
		
		return studentRepository.save(studentEntity);
		
	}
}
