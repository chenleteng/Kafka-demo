package com.clt.kafka.consumer.dao;

import com.clt.kafka.consumer.entity.SuUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface SuUserInfoRepository  {
//    extends JpaRepository<SuUserInfo, Long>
//    SuUserInfo getFirstByGameIdAndChnlIdAndChnlUserId(Integer gameId, String chnlId, String chnlUserId);
//    SuUserInfo getFirstByGameIdAndChnlIdAndUserId(Integer gameId, String chnlId, Long userId);
//
//    @Query(
//            value = "update su_user_info set last_login_time = :#{#userInfo.lastLoginTime},login_count = :#{#userInfo.loginCount} " +
//                    "where game_id = :#{#userInfo.gameId} and user_id = :#{#userInfo.userId} and chnl_id = :#{#userInfo.chnlId}",
//            nativeQuery = true
//    )
//    @Modifying(clearAutomatically = true)
//    @Transactional
//    int updateLoginCountAndTime(@Param("userInfo") SuUserInfo userInfo);
}
