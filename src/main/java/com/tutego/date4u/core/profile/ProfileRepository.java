package com.tutego.date4u.core.profile;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query( nativeQuery = true, value = """
      SELECT YEAR(lastseen) AS y, MONTH(lastseen) AS m, COUNT(*) AS count
      FROM profile
      GROUP BY YEAR(lastseen), MONTH(lastseen)""" )
    List<Tuple> findMonthlyProfileCount();

    @Query( nativeQuery = true, value = """
      SELECT YEAR(lastseen) AS Y, MONTH(lastseen) AS M, count(*) AS count
      FROM   Profile
      WHERE  LASTSEEN > ?1 AND LASTSEEN < ?2
      GROUP BY MONTH(lastseen), YEAR(lastseen)""" )
    List<Tuple> getLastSeenStatisticsPerMonth( LocalDate startDate,
                                               LocalDate endDate );

}
