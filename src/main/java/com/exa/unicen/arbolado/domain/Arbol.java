package com.exa.unicen.arbolado.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A Arbol.
 */
@Entity
@Table(name = "arbol")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Arbol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "altura")
    private Float altura;

    @Column(name = "latitud")
    private String latitud;

    @Column(name = "longitud")
    private String longitud;

    @Column(name = "circunferencia")
    private Float circunferencia;

    @Column(name = "fecha_plantacion")
    private Date fechaPlantacion;

    @Column(name = "fecha_ultima_poda")
    private Date fechaUltimaPoda;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "estado")
    private String estado;

    @Column(name = "tipo_frente")
    private String tipoFrente;

    @Column(name = "interferencia")
    private String interferencia;

    @Column(name = "altura_calle")
    private Integer alturaCalle;

    @Column(name = "exposicion_sol")
    private String exposicionSol;

    @ManyToOne
    private Especie especie;

    @ManyToOne
    private Calle calle;

    /*@Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;*/

    public Arbol() {}

    public Arbol(
        Long id,
        Float altura,
        String latitud,
        String longitud,
        Float circunferencia,
        Date fechaPlantacion,
        Date fechaUltimaPoda,
        String descripcion,
        String imagen,
        String estado,
        String tipoFrente,
        String interferencia,
        Integer alturaCalle,
        String exposicionSol,
        Especie especie,
        Calle calle
    ) {
        this.id = id;
        this.altura = altura;
        this.latitud = latitud;
        this.longitud = longitud;
        this.circunferencia = circunferencia;
        this.fechaPlantacion = fechaPlantacion;
        this.fechaUltimaPoda = fechaUltimaPoda;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.estado = estado;
        this.tipoFrente = tipoFrente;
        this.interferencia = interferencia;
        this.alturaCalle = alturaCalle;
        this.exposicionSol = exposicionSol;
        this.especie = especie;
        this.calle = calle;
    }

    public Long getId() {
        return this.id;
    }

    public Arbol id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAltura() {
        return this.altura;
    }

    public Arbol altura(Float altura) {
        this.setAltura(altura);
        return this;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public String getLatitud() {
        return this.latitud;
    }

    public Arbol latitud(String latitud) {
        this.setLatitud(latitud);
        return this;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return this.longitud;
    }

    public Arbol longitud(String longitud) {
        this.setLongitud(longitud);
        return this;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Float getCircunferencia() {
        return this.circunferencia;
    }

    public Arbol circunferencia(Float circunferencia) {
        this.setCircunferencia(circunferencia);
        return this;
    }

    public void setCircunferencia(Float circunferencia) {
        this.circunferencia = circunferencia;
    }

    public Date getFechaPlantacion() {
        return this.fechaPlantacion;
    }

    public Arbol fechaPlantacion(Date fechaPlantacion) {
        this.setFechaPlantacion(fechaPlantacion);
        return this;
    }

    public void setFechaPlantacion(Date fechaPlantacion) {
        this.fechaPlantacion = fechaPlantacion;
    }

    public Date getFechaUltimaPoda() {
        return this.fechaUltimaPoda;
    }

    public Arbol fechaUltimaPoda(Date fechaUltimaPoda) {
        this.setFechaUltimaPoda(fechaUltimaPoda);
        return this;
    }

    public void setFechaUltimaPoda(Date fechaUltimaPoda) {
        this.fechaUltimaPoda = fechaUltimaPoda;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Arbol descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return this.imagen;
    }

    public Arbol imagen(String imagen) {
        this.setImagen(imagen);
        return this;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstado() {
        return this.estado;
    }

    public Arbol estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoFrente() {
        return this.tipoFrente;
    }

    public Arbol tipoFrente(String tipoFrente) {
        this.setTipoFrente(tipoFrente);
        return this;
    }

    public void setTipoFrente(String tipoFrente) {
        this.tipoFrente = tipoFrente;
    }

    public String getInterferencia() {
        return this.interferencia;
    }

    public Arbol interferencia(String interferencia) {
        this.setInterferencia(interferencia);
        return this;
    }

    public void setInterferencia(String interferencia) {
        this.interferencia = interferencia;
    }

    public Integer getAlturaCalle() {
        return this.alturaCalle;
    }

    public Arbol alturaCalle(Integer alturaCalle) {
        this.setAlturaCalle(alturaCalle);
        return this;
    }

    public void setAlturaCalle(Integer alturaCalle) {
        this.alturaCalle = alturaCalle;
    }

    public String getExposicionSol() {
        return this.exposicionSol;
    }

    public Arbol exposicionSol(String exposicionSol) {
        this.setExposicionSol(exposicionSol);
        return this;
    }

    public void setExposicionSol(String exposicionSol) {
        this.exposicionSol = exposicionSol;
    }

    public Especie getEspecie() {
        return this.especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Arbol especie(Especie especie) {
        this.setEspecie(especie);
        return this;
    }

    public Calle getCalle() {
        return this.calle;
    }

    public void setCalle(Calle calle) {
        this.calle = calle;
    }

    public Arbol calle(Calle calle) {
        this.setCalle(calle);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Arbol)) {
            return false;
        }
        return id != null && id.equals(((Arbol) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Arbol{" +
            "id=" + getId() +
            ", altura=" + getAltura() +
            ", latitud='" + getLatitud() + "'" +
            ", longitud='" + getLongitud() + "'" +
            ", circunferencia=" + getCircunferencia() +
            ", fechaPlantacion='" + getFechaPlantacion() + "'" +
            ", fechaUltimaPoda='" + getFechaUltimaPoda() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", estado='" + getEstado() + "'" +
            ", tipoFrente='" + getTipoFrente() + "'" +
            ", interferencia='" + getInterferencia() + "'" +
            ", alturaCalle=" + getAlturaCalle() +
            ", exposicionSol='" + getExposicionSol() + "'" +
            "}";
    }
}
