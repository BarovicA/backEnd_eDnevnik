package com.iktpreobuka.eDnevnik.entities.enums;

import net.bytebuddy.asm.Advice.Return;


	public enum MarkEnum {INSUFFICIENT(1), SUFFICIENT(2), GOOD(3), VERY_GOOD(4), EXCELLENT(5);

		private int value;
		
		private MarkEnum(int i) {
		this.value = i;
		
	}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

	 public int getValue(int value) {
	        return value;
	    }
	
	

	
}
