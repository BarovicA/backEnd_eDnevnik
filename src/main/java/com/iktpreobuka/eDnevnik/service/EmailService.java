package com.iktpreobuka.eDnevnik.service;

import com.iktpreobuka.eDnevnik.entities.MarkEntity;

public interface EmailService {

	

	void sendMarkEmail(MarkEntity mark);

	String createMarkTableHtml(MarkEntity mark);

}
