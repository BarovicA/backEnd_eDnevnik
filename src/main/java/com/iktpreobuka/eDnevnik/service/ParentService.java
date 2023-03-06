package com.iktpreobuka.eDnevnik.service;

import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.dto.ParentDTO;

public interface ParentService {

	ParentEntity mappNewParent(ParentDTO dto);

	Boolean isActive(Long id);

	ParentEntity changeParentEntity(ParentEntity parent, ParentDTO dto);

}
