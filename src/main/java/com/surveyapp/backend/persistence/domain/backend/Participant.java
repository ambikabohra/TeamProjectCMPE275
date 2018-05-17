package com.surveyapp.backend.persistence.domain.backend;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    private String pName;
    private String email;

    private String pswd;
    private Boolean isValid;

//    @JsonIgnore    //ignores looping in json response
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "tokenId")
//    private Token token;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "participants")
    private Set<Token> tokens = new HashSet<>();


    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Response> participantresponses= new ArrayList<Response>();

    public Participant(String pName, String email, String pswd, Boolean isValid) {
        this.pName = pName;
        this.email = email;
        this.pswd = pswd;
        this.isValid  =isValid;
      //  this.participantresponses = participantresponses;
    }



    public Participant() {
    }

    public int getpId() {
        return userId;
    }

    public void setpId(int pId) {
        this.userId = pId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public List<Response> getParticipantresponses() {
        return participantresponses;
    }

    public void setParticipantresponses(List<Response> participantresponses) {
        this.participantresponses = participantresponses;
    }

    public Set<Token> getTokens() {
        return tokens;
    }

    public void setTokens(Set<Token> tokens) {
        this.tokens = tokens;
    }
}