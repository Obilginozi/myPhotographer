package com.fotografcim.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_surname")
    private String clientSurname;

    @Column(name = "client_mail")
    private String clientMail;

    @Column(name = "client_phone")
    private String clientPhone;

    @Column(name = "client_address")
    private String clientAddress;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "files", "client" }, allowSetters = true)
    private Set<Album> albums = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "clients" }, allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Client id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getClientName() {
        return this.clientName;
    }

    public Client clientName(String clientName) {
        this.setClientName(clientName);
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSurname() {
        return this.clientSurname;
    }

    public Client clientSurname(String clientSurname) {
        this.setClientSurname(clientSurname);
        return this;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public String getClientMail() {
        return this.clientMail;
    }

    public Client clientMail(String clientMail) {
        this.setClientMail(clientMail);
        return this;
    }

    public void setClientMail(String clientMail) {
        this.clientMail = clientMail;
    }

    public String getClientPhone() {
        return this.clientPhone;
    }

    public Client clientPhone(String clientPhone) {
        this.setClientPhone(clientPhone);
        return this;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientAddress() {
        return this.clientAddress;
    }

    public Client clientAddress(String clientAddress) {
        this.setClientAddress(clientAddress);
        return this;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Set<Album> getAlbums() {
        return this.albums;
    }

    public void setAlbums(Set<Album> albums) {
        if (this.albums != null) {
            this.albums.forEach(i -> i.setClient(null));
        }
        if (albums != null) {
            albums.forEach(i -> i.setClient(this));
        }
        this.albums = albums;
    }

    public Client albums(Set<Album> albums) {
        this.setAlbums(albums);
        return this;
    }

    public Client addAlbum(Album album) {
        this.albums.add(album);
        album.setClient(this);
        return this;
    }

    public Client removeAlbum(Album album) {
        this.albums.remove(album);
        album.setClient(null);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Client company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", clientName='" + getClientName() + "'" +
            ", clientSurname='" + getClientSurname() + "'" +
            ", clientMail='" + getClientMail() + "'" +
            ", clientPhone='" + getClientPhone() + "'" +
            ", clientAddress='" + getClientAddress() + "'" +
            "}";
    }
}
