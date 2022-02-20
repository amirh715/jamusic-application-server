/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.time.*;
import javax.persistence.*;
import org.hibernate.envers.*;
import com.jamapplicationserver.infra.Persistence.database.AttributeConverters.YearMonthDateAttributeConverter;

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
        if(recordLabel == null) return;
        if(recordLabel.isBlank()) this.recordLabel = null;
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
        if(producer == null) return;
        if(producer.isBlank()) this.producer = null;
        this.producer = producer;
    }
    
    public ArtistModel getArtist() {
        return this.artist;
    }
    
    public void setArtist(ArtistModel artist) {
        this.artist = artist;
    }
    
}
