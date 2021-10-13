/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import javax.persistence.*;
import org.hibernate.envers.*;

/**
 *
 * @author amirhossein
 */
@Entity
@DiscriminatorValue("B")
@Audited
public class BandModel extends ArtistModel {
    
    public BandModel() {
    }
    
    @ManyToMany
    @JoinTable(
        name="band_members",
        joinColumns = {
            @JoinColumn(name="member_id", referencedColumnName="id")
        },
        inverseJoinColumns = {
            @JoinColumn(name="band_id", referencedColumnName="id")
        }
    )
    @NotAudited
    private Set<SingerModel> members = new HashSet<>();
    
    public void addMember(SingerModel member) {
        this.members.add(member);
    }
    
    public void removeMember(SingerModel member) {
        this.members.remove(member);
    }
    
    public Set<SingerModel> getMembers() {
        return this.members;
    }
    
    public void setMembers(Set<SingerModel> members) {
        this.members = members;
    }

}
