/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import javax.persistence.*;
import java.time.*;
import com.jamapplicationserver.infra.Persistence.database.AttributeConverters.YearMonthDateAttributeConverter;
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
        this.producer = producer;
    }
    
    public ArtistModel getArtist() {
        return this.artist;
    }
    
    public void setArtist(ArtistModel artist) {
        this.artist = artist;
    }
    
}
