package com.service.usermanagement.repository;

import com.service.usermanagement.models.dto.TransactionDto;
import com.service.usermanagement.models.dto.TransactionPreviewDto;
import com.service.usermanagement.models.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Query("select new com.service.usermanagement.models.dto.TransactionPreviewDto(t.id, t.createdAt, t.modifiedAt) " +
            "from Transaction t " +
            "where t.user.id = ?1")
    Page<TransactionPreviewDto> getListUserTransactionPreviewDto(String userID, Pageable pageable);

    @Query("select new com.service.usermanagement.models.dto.TransactionDto(t) " +
            "from Transaction t " +
            "where t.user.id = ?1 and t.id = ?2")
    TransactionDto getUserTransactionDto(String userID, String transactionID);

    boolean existsByUser_IdAndId(String userID, String id);
}
