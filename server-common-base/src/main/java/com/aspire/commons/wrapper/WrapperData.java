package com.aspire.commons.wrapper;

import java.io.Serializable;

import lombok.Data;

@Data
public class WrapperData implements Serializable {

	private static final long serialVersionUID = 2067936585476969805L;

	private ConditionMark conditionMark;
	private transient Object[] params;

	public WrapperData() {
	}

	public WrapperData(ConditionMark conditionMark, Object[] params) {
		super();
		this.conditionMark = conditionMark;
		this.params = params;
	}
}
