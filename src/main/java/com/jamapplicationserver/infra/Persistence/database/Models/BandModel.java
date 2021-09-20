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
@DiscriminatorValue("B")
public class BandModel extends ArtistModel {
    
    public BandModel() {
        super();
    }
    
    @ManyToMany
    @JoinTable(
        name="band_members",
        joinColumns = {
            @JoinColumn(name="singer_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name="band_id")
        }
    )
    private Set<SingerModel> members = new HashSet<SingerModel>();
    
    public void addMember(SingerModel member) {
        this.members.add(member);
    }
    
    public void removeMember(SingerModel member) {
        this.members.remove(member);
    }
    
    public Set<SingerModel> getMembers() {
        return this.members;
    }
    
    private void setMembers(Set<SingerModel> members) {
        this.members = members;
    }

}
