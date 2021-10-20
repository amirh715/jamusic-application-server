/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import java.time.LocalDateTime;
import javax.persistence.*;
import org.hibernate.envers.*;

/**
 *
 * @author amirhossein
 */
@Entity
@Audited
@Table(name="users", schema="jamschema",
        uniqueConstraints={
            @UniqueConstraint(columnNames={"mobile"}, name="mobile_unique_key"),
            @UniqueConstraint(columnNames={"email"}, name="email_unique_key"),
            @UniqueConstraint(columnNames={"image_path"}, name="image_path_unique_key"),
            @UniqueConstraint(columnNames={"fcm_token"}, name="fcm_token_unique_key")
        }
)
public class UserModel extends EntityModel {
    
    @Column(name="name", nullable=false)
    private String name;
    
    @Column(name="mobile", nullable=false, updatable=false)
    private String mobile;
    
    @Column(name="password", nullable=false)
    @NotAudited
    private String password;
    
    @Column(name="email", nullable=true)
    private String email;
    
    @Column(name="email_verified", nullable=false)
    private boolean emailVerified;
    
    @Enumerated(EnumType.STRING)
    @Column(name="user_state", nullable=false)
    private UserStateEnum state;
    
    @Enumerated(EnumType.STRING)
    @Column(name="user_role", nullable=false)
    private UserRoleEnum role;
    
    @Column(name="image_path")
    private String imagePath;
    
    @Column(name="fcm_token")
    private String FCMToken;
    
    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;
    
    @Column(name="last_modified_at", nullable=false)
    private LocalDateTime lastModifiedAt;
    
    @OneToMany(mappedBy="player", orphanRemoval=true, cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<PlayedModel> playedTracks = new HashSet<PlayedModel>();
    
    @OneToMany(mappedBy="recipient", fetch=FetchType.LAZY)
    @NotAudited
    private Set<NotificationRecipientModel> notifications = new HashSet<>();
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="player")
    @NotAudited
    private final Set<PlaylistModel> playlists = new HashSet<>();

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(
            name="creator_id",
            referencedColumnName="id",
            foreignKey=@ForeignKey(name="creator_id_fk_key"),
            updatable=false
    )
    @Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
    private UserModel creator;
    
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
    @JoinColumn(
            name="updater_id",
            referencedColumnName="id",
            foreignKey=@ForeignKey(name="updater_id_fk_key")
    )
    private UserModel updater;
    
    @OneToOne(mappedBy="user", cascade={CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.LAZY)
    @NotAudited
    private UserVerificationModel verification;
    
    @OneToOne(mappedBy="user", cascade={CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.LAZY)
    @NotAudited
    private UserEmailVerificationModel emailVerification;
    
    @OneToOne(mappedBy="user", cascade={CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.LAZY)
    @NotAudited
    private UserPasswordResetVerificationModel passwordResetVerification;
    
    public UserModel() {
        
    }
    
    public UserModel(
            UUID id,
            String name,
            String mobile,
            String password,
            String email,
            boolean emailVerified,
            UserStateEnum state,
            UserRoleEnum role,
            String imagePath,
            String FCMToken,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            UserVerificationModel verification,
            UserEmailVerificationModel emailVerification
    ) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.email = email;
        this.emailVerified = emailVerified;
        this.state = state;
        this.role = role;
        this.imagePath = imagePath;
        this.FCMToken = FCMToken;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.verification = verification;
        this.emailVerification = emailVerification;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean isEmailVerified() {
        return this.emailVerified;
    }
    
    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
    
    public UserStateEnum getState() {
        return this.state;
    }
    
    public void setState(UserStateEnum state) {
        this.state = state;
    }
    
    public UserRoleEnum getRole() {
        return this.role;
    }
    
    public void setRole(UserRoleEnum role) {
        this.role = role;
    }
    
    public String getImagePath() {
        return this.imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public String getFCMToken() {
        return this.FCMToken;
    }
    
    public void setFCMToken(String FCMToken) {
        this.FCMToken = FCMToken;
    }
    
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getLastModifiedAt() {
        return this.lastModifiedAt;
    }
    
    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
    
    public UserModel getCreator() {
        return this.creator;
    }
    
    public void setCreator(UserModel creator) {
        this.creator = creator;
    }
    
    public UserModel getUpdater() {
        return this.updater;
    }
    
    public void setUpdater(UserModel updater) {
        this.updater = updater;
    }
    
    public UserVerificationModel getVerification() {
        return this.verification;
    }
    
    public void setVerification(UserVerificationModel verification) {
        this.verification = verification;
    }
    
    public UserEmailVerificationModel getEmailVerification() {
        return this.emailVerification;
    }
    
    public void setEmailVerification(UserEmailVerificationModel emailVerification) {
        this.emailVerification = emailVerification;
    }
    
    public UserPasswordResetVerificationModel getPasswordResetVerification() {
        return this.passwordResetVerification;
    }
    
    public void setPasswordResetVerification(
            UserPasswordResetVerificationModel passwordResetVerification
    ) {
        this.passwordResetVerification = passwordResetVerification;
    }
    
    public Set<PlayedModel> getPlayedTracks() {
        return this.playedTracks;
    }
    
    public void addPlayedTrack(TrackModel track, LocalDateTime playedAt) {
        final PlayedModel playedTrack =
                new PlayedModel(
                        track,
                        this,
                        playedAt
                );
        this.playedTracks.add(playedTrack);
    }
    
    public Set<NotificationRecipientModel> getNotifications() {
        return this.notifications;
    }
    
    public void addNotification(NotificationRecipientModel recipient) {
        this.notifications.add(recipient);
    }
    
    public Set<PlaylistModel> getPlaylists() {
        return this.playlists;
    }
    
    public void addPlaylist(PlaylistModel playlist) {
        this.playlists.add(playlist);
    }
    
    public void removePlaylist(PlaylistModel playlist) {
        this.playlists.remove(playlist);
    }
    
    @Override
    public boolean equals(Object user) {
        if(user == this)
            return true;
        if(!(user instanceof UserModel))
            return false;
        UserModel u = (UserModel) user;
        return u.getId().equals(this.getId());
    }
    
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
    
}
