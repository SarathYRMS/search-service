package com.animana.assessment.app.mapper;

import com.animana.assessment.app.dto.SearchResponse;
import com.animana.assessment.app.model.AlbumResultResponse;
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
public class AlbumResponseMapperTest {

    @Autowired
    private AlbumResponseMapper albumResponseMapper;

    @Test
    public void test_album_map_to_dto() {
        AlbumResultResponse albumResultResponse = new AlbumResultResponse();
        albumResultResponse.setArtistName("test1");
        albumResultResponse.setCollectionName("collection1");
        albumResultResponse.setType("album");

        final SearchResponse searchResponse = albumResponseMapper.mapToDto(albumResultResponse);
        Assert.assertEquals(searchResponse.getAuthors(), List.of("test1"));
        Assert.assertEquals(searchResponse.getTitle(), "collection1");
        Assert.assertEquals(searchResponse.getType(), "album");
        Assert.assertNotEquals(searchResponse.getType(), "book");
    }

    @Configuration
    @ComponentScan("com.animana.assessment.app.mapper")
    public static class SpringTestConfig {
    }

}
