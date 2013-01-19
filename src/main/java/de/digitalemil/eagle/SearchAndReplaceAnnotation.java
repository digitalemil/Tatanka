package de.digitalemil.eagle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface SearchAndReplaceAnnotation {
	public final static String SUPERCONSTRUCTOR= "SC", METHODPARAMETERNAME= "MPN", METHODPARAMETERTYPE= "MPT", INBODY= "BY";
	String[] value();
}
