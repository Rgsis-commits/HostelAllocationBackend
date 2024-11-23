package com.example.HostelAllocation.Repository;

import com.example.HostelAllocation.Entities.Hostel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostelRepository extends CrudRepository<Hostel, Integer> {
    @Query("Select h from Hostel h join Student s on h.student = s")
    Iterable<Hostel> getAllHostel();
}
