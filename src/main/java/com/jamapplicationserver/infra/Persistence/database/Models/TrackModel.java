/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import java.time.LocalDateTime;
import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author amirhossein
 */
@Entity
@DiscriminatorValue("T")
public class TrackModel extends LibraryEntityModel {
    
    public TrackModel() {
        
    }
    
    @Column(name="audio_path", unique=true)
    private String audioPath;
    
    @Column(name="audio_size")
    private long size;
    
    @Column(name="audio_format")
    private String format;
    
    @Column(name="lyrics")
    private String lyrics;
    
    @ManyToOne
    @JoinColumn(name="album_id")
    private AlbumModel album;
    
    @ManyToOne
    @JoinColumn(name="band_id")
    private BandModel band;
    
    @ManyToOne
    @JoinColumn(name="track_id")
    private SingerModel singer;
    
    public String getAudioPath() {
        return this.audioPath;
    }
    
    public long getSize() {
        return this.size;
    }
    
    public String getFormat() {
        return this.format;
    }
    
    public String getLyrics() {
        return this.lyrics;
    }
    
    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }
    
    public void setSize(long size) {
        this.size = size;
    }
    
    public void setFormat(String format) { // ENUM ??
        this.format = format;
    }
    
    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
    
}
