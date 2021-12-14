package com.dbc.feedbackscontinuos;

import com.dbc.feedbackscontinuos.dto.FeedbacksDTO;
import com.dbc.feedbackscontinuos.dto.TagsDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestesUnitarios{
    @Test
    public void verificaMapeamentoEntreFeedbacksAndTags(){
        TagsDTO tags = new TagsDTO();
        tags.setIdTag(1);
        tags.setNomeTag("JAVA");

        TagsDTO tagsDois = new TagsDTO();
        tagsDois.setIdTag(2);
        tagsDois.setNomeTag("WEB");

        List<TagsDTO> listaTags = new ArrayList<>();
        listaTags.add(tags);
        listaTags.add(tagsDois);

        FeedbacksDTO feedbacksDTO = new FeedbacksDTO();
        feedbacksDTO.setIdFeedback(1);
        feedbacksDTO.setConteudo("Conteudo");
        feedbacksDTO.setTags(listaTags);

        Assert.assertFalse(feedbacksDTO.getTags().isEmpty());
        Assert.assertEquals(feedbacksDTO.getTags().size(), 2);

    }

}
