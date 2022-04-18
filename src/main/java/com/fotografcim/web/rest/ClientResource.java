package com.fotografcim.web.rest;

import com.fotografcim.domain.Client;
import com.fotografcim.domain.Company;
import com.fotografcim.domain.User;
import com.fotografcim.repository.ClientRepository;
import com.fotografcim.service.UserService;
import com.fotografcim.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.fotografcim.domain.Client}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private static final String ENTITY_NAME = "client";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientRepository clientRepository;

    private final UserService userService;
    private final CompanyResource companyResource;

    public ClientResource(ClientRepository clientRepository, UserService userService, CompanyResource companyResource) {
        this.clientRepository = clientRepository;
        this.userService = userService;
        this.companyResource = companyResource;
    }

    /**
     * {@code POST  /clients} : Create a new client.
     *
     * @param client the client to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new client, or with status {@code 400 (Bad Request)} if the client has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client) throws URISyntaxException {
        log.debug("REST request to save Client : {}", client);
        if (client.getId() != null) {
            throw new BadRequestAlertException("A new client cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user = userService.getUserWithAuthorities().get();
        Company company = companyResource.getCompanyByUser(user.getId());
        client.setCompany(company);
        Client result = clientRepository.save(client);
        return ResponseEntity
            .created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clients/:id} : Updates an existing client.
     *
     * @param id the id of the client to save.
     * @param client the client to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated client,
     * or with status {@code 400 (Bad Request)} if the client is not valid,
     * or with status {@code 500 (Internal Server Error)} if the client couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Client client)
        throws URISyntaxException {
        log.debug("REST request to update Client : {}, {}", id, client);
        if (client.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, client.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Client result = clientRepository.save(client);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, client.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /clients/:id} : Partial updates given fields of an existing client, field will ignore if it is null
     *
     * @param id the id of the client to save.
     * @param client the client to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated client,
     * or with status {@code 400 (Bad Request)} if the client is not valid,
     * or with status {@code 404 (Not Found)} if the client is not found,
     * or with status {@code 500 (Internal Server Error)} if the client couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/clients/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Client> partialUpdateClient(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody Client client
    ) throws URISyntaxException {
        log.debug("REST request to partial update Client partially : {}, {}", id, client);
        if (client.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, client.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Client> result = clientRepository
            .findById(client.getId())
            .map(existingClient -> {
                if (client.getClientName() != null) {
                    existingClient.setClientName(client.getClientName());
                }
                if (client.getClientSurname() != null) {
                    existingClient.setClientSurname(client.getClientSurname());
                }
                if (client.getClientMail() != null) {
                    existingClient.setClientMail(client.getClientMail());
                }
                if (client.getClientPhone() != null) {
                    existingClient.setClientPhone(client.getClientPhone());
                }
                if (client.getClientAddress() != null) {
                    existingClient.setClientAddress(client.getClientAddress());
                }

                return existingClient;
            })
            .map(clientRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, client.getId().toString())
        );
    }

    /**
     * {@code GET  /clients} : get all the clients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clients in body.
     */
    @GetMapping("/clients")
    public List<Client> getAllClients() {
        log.debug("REST request to get all Clients");
        return clientRepository.findAll();
    }

    /**
     * {@code GET  /clients/:id} : get the "id" client.
     *
     * @param id the id of the client to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the client, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClient(@PathVariable UUID id) {
        log.debug("REST request to get Client : {}", id);
        Optional<Client> client = clientRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(client);
    }

    /**
     * {@code DELETE  /clients/:id} : delete the "id" client.
     *
     * @param id the id of the client to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        log.debug("REST request to delete Client : {}", id);
        clientRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
