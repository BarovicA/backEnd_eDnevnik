package com.iktpreobuka.eDnevnik.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import net.bytebuddy.asm.Advice.Return;


	public enum MarkEnum {INSUFFICIENT(1), SUFFICIENT(2), GOOD(3), VERY_GOOD(4), EXCELLENT(5);

		private int value;
		
		private MarkEnum(int i) {
		this.value = i;
		
	}

		public int getValue() {
			return value;
		}
		
	    @JsonValue
	    public String toString() {
	        return name() + "(" + value + ")";
	    }

//		public static MarkEnum fromInt(int i) {
//	        for (MarkEnum mark : MarkEnum.values()) {
//	            if (mark.value == i) {
//	                return mark;
//	            }
//	        }
//	        throw new IllegalArgumentException("Invalid mark value: " + i);
//		}
}
