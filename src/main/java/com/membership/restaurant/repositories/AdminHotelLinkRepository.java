package com.membership.restaurant.repositories;

import com.membership.restaurant.entities.AdminHotelLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AdminHotelLinkRepository extends JpaRepository<AdminHotelLink, Integer> {
    AdminHotelLink getAdminHotelLinkByUserId(Integer userId);
}