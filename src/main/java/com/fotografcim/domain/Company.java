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
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_phone")
    private String companyPhone;

    @Column(name = "company_mail")
    private String companyMail;

    @Column(name = "company_address")
    private String companyAddress;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "albums", "company" }, allowSetters = true)
    private Set<Client> clients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Company id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Company companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return this.companyPhone;
    }

    public Company companyPhone(String companyPhone) {
        this.setCompanyPhone(companyPhone);
        return this;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyMail() {
        return this.companyMail;
    }

    public Company companyMail(String companyMail) {
        this.setCompanyMail(companyMail);
        return this;
    }

    public void setCompanyMail(String companyMail) {
        this.companyMail = companyMail;
    }

    public String getCompanyAddress() {
        return this.companyAddress;
    }

    public Company companyAddress(String companyAddress) {
        this.setCompanyAddress(companyAddress);
        return this;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Client> getClients() {
        return this.clients;
    }

    public void setClients(Set<Client> clients) {
        if (this.clients != null) {
            this.clients.forEach(i -> i.setCompany(null));
        }
        if (clients != null) {
            clients.forEach(i -> i.setCompany(this));
        }
        this.clients = clients;
    }

    public Company clients(Set<Client> clients) {
        this.setClients(clients);
        return this;
    }

    public Company addClient(Client client) {
        this.clients.add(client);
        client.setCompany(this);
        return this;
    }

    public Company removeClient(Client client) {
        this.clients.remove(client);
        client.setCompany(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", companyPhone='" + getCompanyPhone() + "'" +
            ", companyMail='" + getCompanyMail() + "'" +
            ", companyAddress='" + getCompanyAddress() + "'" +
            "}";
    }
}
