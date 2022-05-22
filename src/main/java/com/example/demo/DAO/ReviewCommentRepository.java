package com.example.demo.DAO;


import com.example.demo.Models.ReviewComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewCommentRepository extends JpaRepository<ReviewComments, Integer> {
    public List<ReviewComments> getAllByEmployeePackageId(Integer employee_package_id);
}