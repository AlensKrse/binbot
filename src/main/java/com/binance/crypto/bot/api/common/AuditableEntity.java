package com.binance.crypto.bot.api.common;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;

import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public abstract class AuditableEntity extends Entity {

	@Id
	@Column(name = "id", columnDefinition = "bigserial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false)
	private Date created;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated", nullable = false)
	private Date updated;

	@Version
	@Column(name = "version")
	private Integer version;

	@PrePersist
	protected void onCreate() {
		created = new Date();
		updated = created;
	}

	@PreUpdate
	protected void onUpdate() {
		updated = new Date();
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(final Date updated) {
		this.updated = updated;
	}

	public void setCreated(final Date created) {
		this.created = created;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

	public Date getCreated() {
		return created;
	}

	@Override
	public String toString() {
		return "GeneratedIdEntity{" + "id=" + id + ", created=" + created + ", updated=" + updated + '}';
	}

	public Integer getVersion() {
		return version;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		final AuditableEntity that = (AuditableEntity) o;
		return Objects.equals(id, that.id) && Objects.equals(created, that.created) && Objects.equals(updated, that.updated) && Objects.equals(version, that.version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id, created, updated, version);
	}
}
