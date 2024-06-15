package com.example.eldermate.repository;

import com.example.eldermate.entity.Cancel;
import com.example.eldermate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CancelRepository extends JpaRepository<Cancel, Long>{
}
