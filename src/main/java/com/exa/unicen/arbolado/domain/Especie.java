package com.exa.unicen.arbolado.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * A Especie.
 */
@Entity
@Table(name = "especie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Especie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_cientifico")
    private String nombreCientifico;

    @NotNull
    @Column(name = "nombre_comun", nullable = false)
    private String nombreComun;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "estado_conservacion")
    private String estadoConservacion;

    @Column(name = "altura_max")
    private Float alturaMax;

    @Column(name = "familia")
    private String familia;

    @Column(name = "origen")
    private String origen;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Especie id(Long id) {
        this.setId(id);
        return this;
    }

    public Long getId() {
        return this.id;
    }

    public Especie(Long id) {
        this.id = id;
    }

    public Especie() {}

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCientifico() {
        return this.nombreCientifico;
    }

    public Especie nombreCientifico(String nombreCientifico) {
        this.setNombreCientifico(nombreCientifico);
        return this;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public String getNombreComun() {
        return this.nombreComun;
    }

    public Especie nombreComun(String nombreComun) {
        this.setNombreComun(nombreComun);
        return this;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Especie descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return this.imagen;
    }

    public Especie imagen(String imagen) {
        this.setImagen(imagen);
        return this;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstadoConservacion() {
        return this.estadoConservacion;
    }

    public Especie estadoConservacion(String estadoConservacion) {
        this.setEstadoConservacion(estadoConservacion);
        return this;
    }

    public void setEstadoConservacion(String estadoConservacion) {
        this.estadoConservacion = estadoConservacion;
    }

    public Float getAlturaMax() {
        return this.alturaMax;
    }

    public Especie alturaMax(Float alturaMax) {
        this.setAlturaMax(alturaMax);
        return this;
    }

    public void setAlturaMax(Float alturaMax) {
        this.alturaMax = alturaMax;
    }

    public String getFamilia() {
        return this.familia;
    }

    public Especie familia(String familia) {
        this.setFamilia(familia);
        return this;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getOrigen() {
        return this.origen;
    }

    public Especie origen(String origen) {
        this.setOrigen(origen);
        return this;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Especie)) {
            return false;
        }
        return id != null && id.equals(((Especie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Especie{" +
            "id=" + getId() +
            ", nombreCientifico='" + getNombreCientifico() + "'" +
            ", nombreComun='" + getNombreComun() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", estadoConservacion='" + getEstadoConservacion() + "'" +
            ", alturaMax=" + getAlturaMax() +
            ", familia='" + getFamilia() + "'" +
            ", origen='" + getOrigen() + "'" +
            "}";
    }
}
