package com.fotografcim.repository;

import com.fotografcim.domain.Album;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Album entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlbumRepository extends JpaRepository<Album, UUID> {
    Optional<Album> findAlbumByToken(String token);
}
