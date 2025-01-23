	package es.davidclarkson.practicas.act2ut5v1.pruebas1.entities;

	import jakarta.persistence.*;
	import lombok.Getter;
	import lombok.Setter;

	import java.time.LocalDate;

	@Entity
	@Table(name = "students")
	public class Student {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int studentId;
		private String firstName;
		private String lastName;
		private LocalDate dateOfBirth;

		public Student(){}

		public Student(int id, String firstName, String lastName, LocalDate dateOfBirth) {
			this.studentId = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.dateOfBirth = dateOfBirth;
		}

		public int getStudentId() {
			return studentId;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public LocalDate getDateOfBirth() {
			return dateOfBirth;
		}

		public void setStudentId(int studentId) {
			this.studentId = studentId;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public void setDateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}
	}

