package nohi.think._29jpa.entity;

import javax.persistence.*;

/**
 * Created by nohi on 2018/5/6.
 */
@Entity
@Table(name="T_USER")
public class TUserEntity {

	@Id
	@Column(name = "U_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid;

	private String name;

	private int age;

	private String address;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
