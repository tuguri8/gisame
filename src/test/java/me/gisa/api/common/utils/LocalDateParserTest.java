package me.gisa.api.common.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LocalDateParserTest {

    @Test
    public void 날짜_변경_테스트() {
        String date = "20190715";
        assertThat(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")).toString(), is("2019-07-15"));
    }
}