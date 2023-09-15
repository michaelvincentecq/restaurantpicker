package com.ecquaria.restaurantpicker.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.ecquaria.restaurantpicker.enums.RpSessionStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rp_session")
public class RpSession {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "session_uuid")
	private UUID sessionUuid;

	@Column(name = "session_code")
	private String sessionCode;

	@Enumerated(EnumType.STRING)
	private RpSessionStatusEnum status;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "created_by")
	private String createdBy;

	@OneToMany(mappedBy = "rpSession", cascade = CascadeType.ALL)
	private List<RpSubmission> rpSubmissions;

}