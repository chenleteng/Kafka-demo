package com.clt.kafka.consumer.dao;

import com.clt.kafka.consumer.entity.TbItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TbItemRepository extends JpaRepository<TbItem, Long> {
}
