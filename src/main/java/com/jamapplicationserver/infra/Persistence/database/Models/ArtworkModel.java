/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import javax.persistence.*;
import java.time.*;
import com.jamapplicationserver.infra.Persistence.database.AttributeConverters.YearMonthDateAttributeConverter;
import java.util.stream.Collectors;
import org.hibernate.envers.*;

/**
 *
 * @author dada
 */
@Entity
@Audited
public abstract class ArtworkModel extends LibraryEntityModel {
    
    @Column(name="record_label")
    private String recordLabel;
    
    @Column(name="record_producer")
    private String producer;
    
    @Column(name="inherited_tags")
    private String inheritedTags;
    
    @Column(name="release_date", columnDefinition="date")
    @Convert(converter=YearMonthDateAttributeConverter.class)
    private YearMonth releaseDate;
    
    public ArtworkModel() {
        
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @NotAudited
    private ArtistModel artist;
    
    public String getRecordLabel() {
        return this.recordLabel;
    }
    
    public void setRecordLabel(String recordLabel) {
        if(recordLabel != null && recordLabel.isBlank())
            this.recordLabel = null;
        this.recordLabel = recordLabel;
    }
    
    public YearMonth getReleaseDate() {
        return this.releaseDate;
    }
    
    public void setReleaseDate(YearMonth releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    public String getProducer() {
        return this.producer;
    }
    
    public void setProducer(String producer) {
        if(producer != null && producer.isBlank())
            this.producer = null;
        this.producer = producer;
    }
    
    public ArtistModel getArtist() {
        return this.artist;
    }
    
    public void setArtist(ArtistModel artist) {
        this.artist = artist;
        this.setInheritedTags(artist.getTags());
    }
    
    public Set<String> getInheritedTags() {
        if(this.inheritedTags == null || this.inheritedTags.isEmpty())
            return new HashSet<>();
        final String separator = "#";
        final List<String> tagsList = Arrays.asList(this.inheritedTags.split(separator));
        return tagsList.stream().collect(Collectors.toSet());
    }
    
    public void setInheritedTags(Set<String> tags) {
        if(tags == null || tags.isEmpty()) {
            this.inheritedTags = null;
            return;
        }
        final String separator = "#";
        this.inheritedTags =
                tags.stream()
                .map(tag -> {
                    return tag
                            .trim()
                            .replace(" ", "_")
                            .concat(separator);
                })
                .collect(Collectors.joining());
    }
    
}
