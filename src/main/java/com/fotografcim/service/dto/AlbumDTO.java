package com.fotografcim.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fotografcim.domain.Album;
import com.fotografcim.domain.Client;
import com.fotografcim.domain.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

public class AlbumDTO implements Serializable {

    private List<MultipartFile> files = new ArrayList<>();

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private Client client;
}
