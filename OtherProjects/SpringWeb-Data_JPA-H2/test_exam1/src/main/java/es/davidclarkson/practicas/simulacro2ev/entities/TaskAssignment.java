package es.davidclarkson.practicas.simulacro2ev.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "task_assignment")
public class TaskAssignment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_assignment_id", nullable = false)
	private int taskAssignamentId;

	@ManyToOne
	@JoinColumn(name = "family_member_id")
	private FamilyMember familyMemberId;

	@ManyToOne
	@JoinColumn(name = "task_id", nullable = false)
	private Task taskId;

	@Column(name = "assignment_datetime", nullable = false)
	private LocalDateTime assignmentDatetime;


	@Column(name = "completion_datetime", nullable = true)
	private LocalDateTime completionDatetime;


	@ManyToOne
	@JoinColumn(name = "task_status_id", nullable = false)
	private TaskStatus taskStatusId;

}
