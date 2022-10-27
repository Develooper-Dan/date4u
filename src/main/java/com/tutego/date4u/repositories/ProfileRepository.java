package com.tutego.date4u.repositories;

import com.tutego.date4u.entities.Profile;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    Optional<Profile> findProfileByNickname(String nickname);

   @Query(value = """
            SELECT p FROM Profile as p WHERE (:nickname is null or p.nickname like %:nickname% ) and
            (p.hornlength >= :minHornlength) and
            (p.hornlength <= :maxHornlength) and
            (p.birthdate between :maxAgeAsDate and :minAgeAsDate ) and
            (:gender is null or p.gender = :gender ) and
            (p.attractedToGender is null or p.attractedToGender = :attractedToGender )
            """
    )
    Page<Profile> findAllProfilesBySearchParams(String nickname,
                                            int minHornlength,
                                            int maxHornlength,
                                            LocalDate minAgeAsDate,
                                            LocalDate maxAgeAsDate,
                                            Byte gender,
                                            Integer attractedToGender,
                                            Pageable pageable);

}
