package org.utils.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Student implements Serializable {

	private static final long serialVersionUID = 6973576143316146251L;

	private long id;
	private String name;
	private String course;
	// private Timestamp addtime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	/*
		public Timestamp getAddtime() {
			return addtime;
		}
		public void setAddtime(Timestamp addtime) {
			this.addtime = addtime;
		}	
	*/
	@Override
	public String toString() {
		return "Student{" + "id=" + id + ", name=" + name + ", course=" + course
		/*+ ", addtime=" + addtime */ + '}';
	}
}
