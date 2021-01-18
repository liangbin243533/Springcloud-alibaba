package com.chen.dao;

import com.chen.domain.Order;
import com.chen.domain.TxLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxLogDao extends JpaRepository<TxLog, String> {
}
