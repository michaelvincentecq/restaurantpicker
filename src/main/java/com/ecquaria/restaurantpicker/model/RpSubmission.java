package com.ecquaria.restaurantpicker.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "rp_submission")
@NoArgsConstructor
public class RpSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "session_uuid")
	private RpSession rpSession;

	@Column(name = "restaurant_name")
	private String restaurantName;

	@Column(name = "submitted_by")
	private String submittedBy;

	@Column(name = "created_date")
	private Date createdDate;

	public RpSubmission(RpSession rpSession, String restaurantName, String submittedBy) {
		this.rpSession = rpSession;
		this.restaurantName = restaurantName;
		this.submittedBy = submittedBy;
	}

}