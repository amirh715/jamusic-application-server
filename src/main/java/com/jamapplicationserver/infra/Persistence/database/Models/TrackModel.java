/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import javax.persistence.*;
import java.io.Serializable;
import org.hibernate.envers.*;

/**
 *
 * @author amirhossein
 */
@Entity
@DiscriminatorValue("T")
@Audited
public class TrackModel extends ArtworkModel {
    
    public TrackModel() {
        super();
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
    
    public void setFormat(String format) {
        this.format = format;
    }
    
    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
    
    public AlbumModel getAlbum() {
        return this.album;
    }
    
    public void setAlbum(AlbumModel album) {
        this.album = album;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    
}
