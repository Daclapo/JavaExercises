package es.davidclarkson.practicas.simulacro2ev.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "family_member")
public class FamilyMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "family_member_id", nullable = false)
	private int familyMemberId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "age")
	private int age;


}
