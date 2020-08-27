package com.animana.assessment.app.mapper;

import com.animana.assessment.app.dto.SearchResponse;
import com.animana.assessment.app.model.VolumeInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@ContextConfiguration(classes = AlbumResponseMapperTest.SpringTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BookResponseMapperTest {

    @Autowired
    private BookResponseMapper bookResponseMapper;

    @Test
    public void test_book_map_to_dto() {
        VolumeInfo volumeInfo = new VolumeInfo();
        volumeInfo.setTitle("Hello, Goodbye");
        volumeInfo.setAuthors(List.of("Karmin"));
        volumeInfo.setType("Book");

        final SearchResponse searchResponse = bookResponseMapper.mapToDto(volumeInfo);
        Assert.assertEquals(searchResponse.getAuthors(), List.of("Karmin"));
        Assert.assertEquals(searchResponse.getTitle(), "Hello, Goodbye");
        Assert.assertEquals(searchResponse.getType(), "Book");
        Assert.assertNotEquals(searchResponse.getType(), "Album");
    }

    @Configuration
    @ComponentScan("com.animana.assessment.app.mapper")
    public static class SpringTestConfig {
    }
}
