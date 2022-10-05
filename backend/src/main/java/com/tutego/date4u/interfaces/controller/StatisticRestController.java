package com.tutego.date4u.interfaces.controller;

import com.tutego.date4u.core.LastSeenStatistics;
import com.tutego.date4u.core.LastSeenStatistics.Data;
import com.tutego.date4u.profile.ProfileRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@RestController
@RequestMapping("/api/stat")
public class StatisticRestController {

    private final ProfileRepository profiles;

    public StatisticRestController( ProfileRepository profiles ) {
        this.profiles = profiles;
    }

    @GetMapping( "/total" )
    // long als Rückgabe ist auch in Ordnung
    public String totalNumberOfRegisteredUnicorns() {
        return String.valueOf( profiles.count() );
    }

    @GetMapping( value = "/last-seen"
            //, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public LastSeenStatistics lastSeenStatistics(@RequestParam(value = "start", required = false) String startString,
                                                 @RequestParam(value = "end", required = false) String endString){

        YearMonth start = startString == null ? YearMonth.now().minusYears(2) : YearMonth.parse(startString);
        YearMonth end = endString == null ? YearMonth.now() : YearMonth.parse(endString);

        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        /*return new LastSeenStatistics(
                Stream.iterate( start, o -> o.plusMonths( 1 ) )
                      .limit( start.until( end, ChronoUnit.MONTHS ) + 1 )
                      .map( yearMonth -> new LastSeenStatistics.Data(
                              yearMonth, rnd.nextInt(1000,10000) ) )
                      .collect( Collectors.toList() ) );*/

        List<Data> data = profiles.findMonthlyProfileCount().stream().map(
                tuple -> {
                    return new Data( YearMonth.of(Integer.parseInt(tuple.get("y").toString()),
                            Integer.parseInt(tuple.get("m").toString())),
                            Integer.parseInt( tuple.get( "count" ).toString() ) );
                } ).toList();

        return new LastSeenStatistics(data);
    }
}

