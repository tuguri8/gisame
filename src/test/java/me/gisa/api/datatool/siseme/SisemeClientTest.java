package me.gisa.api.datatool.siseme;

import me.gisa.api.datatool.siseme.model.Region;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SisemeClientTest {
    @Autowired
    SisemeClient sisemeClient;

    @Test
    public void getRegionList() {
        Optional<List<Region>> sidoList = sisemeClient.getRegionList("sido");
        Optional<List<Region>> gunguList = sisemeClient.getRegionList("gungu");
        Optional<List<Region>> dongList = sisemeClient.getRegionList("dong");
        assertThat(sidoList).isNotEmpty();
        assertThat(gunguList).isNotEmpty();
        assertThat(dongList).isNotEmpty();
    }
}