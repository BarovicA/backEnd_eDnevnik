package com.iktpreobuka.eDnevnik.service;

import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.enums.MarkEnum;

@Service
public class MarkServiceImpl implements MarkService {

	
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
	
}



