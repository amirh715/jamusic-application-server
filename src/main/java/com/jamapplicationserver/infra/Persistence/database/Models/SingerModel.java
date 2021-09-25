/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import javax.persistence.*;

/**
 *
 * @author amirhossein
 */
@Entity
@DiscriminatorValue("S")
public class SingerModel extends ArtistModel {
    
    public SingerModel() {
        super();
        this.bands = new HashSet<>();
    }
    
    @ManyToMany(mappedBy="members")
    private Set<BandModel> bands;
    
    public Set<BandModel> getBands() {
        return this.bands;
    }
    
    public void setBands(Set<BandModel> bands) {
        this.bands = bands;
    }
    
}
